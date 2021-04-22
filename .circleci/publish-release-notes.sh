#! /bin/bash

# Create release notes and publish them to central
# Release notes are generated for the last published release and for the next to-be published release

# saner programming env: these switches turn some bugs into errors
# https://www.davidpashley.com/articles/writing-robust-shell-scripts/
set -o errexit -o pipefail -o noclobber -o nounset

publish_release_notes() {
  echo "Creating release notes for repo ${1} from branch ${2} to branch ${3}"

  cplace-cli release-notes --from "${2}" "${3}" --force
  echo '---- Generated changelog BEGIN chronological'
  cat CHANGELOG.md
  echo '---- Generated changelog END chronological'

  if [[ $(cat CHANGELOG.md | wc -l) -ge 5 ]]; then
    (
      head -n 2 CHANGELOG.md &
      tail -n$(($(cat CHANGELOG.md | wc -l) - 4)) CHANGELOG.md | sort
    ) >CHANGELOG.sorted.md
    sed -n '3,4p' CHANGELOG.md >>CHANGELOG.sorted.md
    mv CHANGELOG.sorted.md CHANGELOG.md
  fi

  echo '---- Generated changelog BEGIN alphabetical'
  cat CHANGELOG.md
  echo '---- Generated changelog END alphabetical'

  showdown makehtml -i CHANGELOG.md -o changelog.html
  echo "Publishing release notes to central for release ${4}"
  curl -X POST --data-urlencode "release-notes=$(cat changelog.html)" -d token=d41d8cd98f00b204e9800998ecf8427e -d release=${4} -d repo-space-name=${1} "https://central.collaboration-factory.de/cf/cplace/cfactoryCentral/handler/releaseNotes/releaseNotes"
}

check_input_params() {
  repoName=${1}
  currentBranchName=${2}

  echo "You are on branch ${currentBranchName}"

  if [ "${currentBranchName}" != "master" ]; then
    echo "Publishing release notes is NOT supported for branches different than master"
    exit 1
  fi
}

main() {
  check_input_params "${1}" "${2}"

  releaseBranches="$(git for-each-ref --format '%(refname:short)' 'refs/remotes/origin/release' | grep -E '^origin/release/[0-9]+\.[0-9]+$' | sort -V | tail -2)"
  if [ -z "$releaseBranches" ]; then
    echo "No release branches in repo $repoName"
    exit 1
  else
    echo "Latest release branches:"
    echo $releaseBranches
  fi

  latestReleaseBranch="$(echo "$releaseBranches" | tail -1)"
  preLatestReleaseBranch="$(echo "$releaseBranches" | head -1)"

  curRelMajorNr="$(echo $latestReleaseBranch | sed -E -e 's@^origin/release/([0-9]+).[0-9]+@\1@')"
  curRelMinorNr="$(echo $latestReleaseBranch | sed -E -e 's@^origin/release/[0-9]+.([0-9]+)@\1@')"
  curRelease="$curRelMajorNr.$curRelMinorNr"

  if [[ "$latestReleaseBranch" != "$preLatestReleaseBranch" ]]; then
    publish_release_notes $repoName "$preLatestReleaseBranch" "$latestReleaseBranch" $curRelease
  fi

  if [ "4.59" = "$curRelease" ]; then
    nextRelease="5.0"
  else
    nextRelease="$curRelMajorNr.$(($curRelMinorNr + 1))"
  fi

  publish_release_notes $repoName "$latestReleaseBranch" "$currentBranchName" $nextRelease
}

if [[ $# -le 1 ]] ; then
    echo 'Need to provide the repository and the branch name!'
    exit 1
fi

main "${1}" "${2}"

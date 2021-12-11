# cplace-scim2

Proof of concept for a SCIM2 plugin for cplace that was implemented as a Tech Day project.

See also https://base.cplace.io/pages/6y8orpi0m0nwf8v01h1im8sjm/Tech-Day

Current status:
- a cplace plugin that allows to synchronize users (login, name, email, preferred language, profile image) and groups
- Tested with Ping Identity. Synching users to cplace works, including  (login, name, email, preferred language, profile image). Snyching groups doesn't seem to be possible with Ping.
- assigning users to groups is still missing
- paging in the GET endpoints is missing
- password reset mechanism seems not to work properly yet
package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import cf.cplace.scim2.domain.GroupRepository;
import com.bettercloud.scim2.server.annotation.ScimResource;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ScimResource(description = "Access Group Resources", name = "Group", schema = GroupResource.class)
@CplaceRequestMapping(path = "/cf.cplace.scim2/Groups")
public class GroupController {

    private static final Logger log = LoggerFactory.getLogger(GroupController.class);

    private final GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = Preconditions.checkNotNull(groupRepository);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<GroupResource> findGroups(SearchRequest searchRequest) {
        log.debug("Finding groups that match {}...", searchRequest);
        return groupRepository.find(searchRequest);
    }

    @GetMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupResource getGroup(@PathVariable String groupId) {
        log.debug("Getting group with id='{}'...", groupId);
        return groupRepository.findById(groupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResource createUser(@RequestBody GroupResource group) {
        log.debug("Creating group with displayName='{}'...", group.getDisplayName());
        return groupRepository.create(group);
    }

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupResource updateGroup(@RequestBody GroupResource group, @PathVariable String groupId) {
        log.debug("Updating group with id='{}', displayName='{}'...", groupId, group.getDisplayName());
        return groupRepository.update(group);
    }

    @PatchMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupResource patchGroup(@RequestBody GroupResource group, @PathVariable String groupId) {
        log.debug("Patching group with id='{}', displayName='{}'...", groupId, group.getDisplayName());
        return groupRepository.patch(groupId, group);
    }
}

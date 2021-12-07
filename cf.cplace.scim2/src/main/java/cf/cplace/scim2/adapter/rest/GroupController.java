package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import cf.cplace.scim2.usecase.CreateGroupUseCase;
import cf.cplace.scim2.usecase.FindGroupsUseCase;
import cf.cplace.scim2.usecase.UpdateGroupUseCase;
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

    private final FindGroupsUseCase findGroupsUseCase;
    private final CreateGroupUseCase createGroupUseCase;
    private final UpdateGroupUseCase updateGroupUseCase;

    public GroupController(FindGroupsUseCase findGroupsUseCase, CreateGroupUseCase createGroupUseCase, UpdateGroupUseCase updateGroupUseCase) {
        this.findGroupsUseCase = Preconditions.checkNotNull(findGroupsUseCase);
        this.createGroupUseCase = Preconditions.checkNotNull(createGroupUseCase);
        this.updateGroupUseCase = Preconditions.checkNotNull(updateGroupUseCase);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<GroupResource> findGroups(SearchRequest searchRequest) {
        log.debug("Finding groups that match {}...", searchRequest);
        return findGroupsUseCase.find(searchRequest);
    }

    @GetMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupResource getGroup(@PathVariable String groupId) {
        log.debug("Getting group with id='{}'...", groupId);
        return findGroupsUseCase.findById(groupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResource createUser(@RequestBody GroupResource group) {
        log.debug("Creating group with displayName='{}'...", group.getDisplayName());
        return createGroupUseCase.createGroup(group);
    }

    @PutMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupResource updateGroup(@RequestBody GroupResource group, @PathVariable String groupId) {
        log.debug("Updating group with id='{}', displayName='{}'...", groupId, group.getDisplayName());
        return updateGroupUseCase.updateGroup(group);
    }
}

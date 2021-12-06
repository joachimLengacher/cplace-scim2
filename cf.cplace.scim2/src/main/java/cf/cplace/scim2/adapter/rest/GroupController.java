package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import cf.cplace.scim2.usecase.CreateGroupUseCase;
import cf.cplace.scim2.usecase.FindGroupsUseCase;
import com.bettercloud.scim2.server.annotation.ScimResource;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ScimResource(description = "Access Group Resources", name = "Group", schema = GroupResource.class)
@CplaceRequestMapping(path = "/cf.cplace.scim2/Groups")
public class GroupController {

    private final FindGroupsUseCase findGroupsUseCase;
    private final CreateGroupUseCase createGroupUseCase;

    public GroupController(FindGroupsUseCase findGroupsUseCase, CreateGroupUseCase createGroupUseCase) {
        this.findGroupsUseCase = Preconditions.checkNotNull(findGroupsUseCase);
        this.createGroupUseCase = Preconditions.checkNotNull(createGroupUseCase);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<GroupResource> findGroups(SearchRequest searchRequest) {
        return findGroupsUseCase.find(searchRequest);
    }

    @GetMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public GroupResource getGroup(@PathVariable String groupId) {
        return findGroupsUseCase.findById(groupId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResource createUser(@RequestBody GroupResource group) {
        return createGroupUseCase.createGroup(group);
    }

}

package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import cf.cplace.scim2.usecase.FindGroupsUseCase;
import com.bettercloud.scim2.server.annotation.ScimResource;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.GroupResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ScimResource(description = "Access Group Resources", name = "Group", schema = GroupResource.class)
@CplaceRequestMapping(path = "/cf.cplace.scim2/Groups")
public class GroupController {

    private final FindGroupsUseCase findGroupsUseCase;

    public GroupController(FindGroupsUseCase findGroupsUseCase) {
        this.findGroupsUseCase = Preconditions.checkNotNull(findGroupsUseCase);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<GroupResource> findGroups(SearchRequest searchRequest) {
        return findGroupsUseCase.find(searchRequest);
    }
}

package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import cf.cplace.scim2.usecase.CreateUserUseCase;
import com.bettercloud.scim2.server.annotation.ScimResource;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.UserResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ScimResource(description = "Access User Resources", name = "User", schema = UserResource.class)
@CplaceRequestMapping(path = "/cf.cplace.scim2/Users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = Preconditions.checkNotNull(createUserUseCase);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResource createUser(@RequestBody UserResource user) {
        return createUserUseCase.createUser(user);
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public ListResponse<UserResource> findUsers(SearchRequest searchRequest) {
//        return findCplaceUsers(searchRequest);
//    }
//
//    @GetMapping("/{userId}")
//    @ResponseStatus(HttpStatus.OK)
//    public UserResource getUser(@PathVariable String userId) {
//        return getCplaceUser(userId);
//    }
}

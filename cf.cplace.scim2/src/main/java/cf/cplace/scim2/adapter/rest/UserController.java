package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import cf.cplace.scim2.usecase.CreateUserUseCase;
import cf.cplace.scim2.usecase.DeleteUserUseCase;
import cf.cplace.scim2.usecase.FindUserUseCase;
import cf.cplace.scim2.usecase.UpdateUserUseCase;
import com.bettercloud.scim2.server.annotation.ScimResource;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.types.UserResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ScimResource(description = "Access User Resources", name = "User", schema = UserResource.class)
@CplaceRequestMapping(path = "/cf.cplace.scim2/Users")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserUseCase findUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase, FindUserUseCase findUserUseCase, UpdateUserUseCase updateUserUseCase, DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = Preconditions.checkNotNull(createUserUseCase);
        this.findUserUseCase = Preconditions.checkNotNull(findUserUseCase);
        this.updateUserUseCase = Preconditions.checkNotNull(updateUserUseCase);
        this.deleteUserUseCase = Preconditions.checkNotNull(deleteUserUseCase);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResource createUser(@RequestBody UserResource user) {
        return createUserUseCase.createUser(user);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource updateUser(@RequestBody UserResource user) {
        return updateUserUseCase.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        deleteUserUseCase.deleteById(userId);
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public ListResponse<UserResource> findUsers(SearchRequest searchRequest) {
//        return findCplaceUsers(searchRequest);
//    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource getUser(@PathVariable String userId) {
        return findUserUseCase.findById(userId);
    }
}

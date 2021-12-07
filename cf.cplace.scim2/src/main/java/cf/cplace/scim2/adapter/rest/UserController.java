package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import cf.cplace.scim2.usecase.CreateUserUseCase;
import cf.cplace.scim2.usecase.DeleteUserUseCase;
import cf.cplace.scim2.usecase.FindUserUseCase;
import cf.cplace.scim2.usecase.UpdateUserUseCase;
import com.bettercloud.scim2.server.annotation.ScimResource;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ScimResource(description = "Access User Resources", name = "User", schema = UserResource.class)
@CplaceRequestMapping(path = "/cf.cplace.scim2/Users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

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
        log.debug("Creating user with userName='{}'...", user.getUserName());
        return createUserUseCase.createUser(user);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource updateUser(@RequestBody UserResource user, @PathVariable String userId) {
        log.debug("Updating user with id={}, userName='{}'...", userId, user.getUserName());
        return updateUserUseCase.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        log.debug("Deleting user with id='{}'...", userId);
        deleteUserUseCase.deleteById(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<UserResource> findUsers(SearchRequest searchRequest) {
        log.debug("Finding users that match {}...", searchRequest);
        return findUserUseCase.find(searchRequest);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource getUser(@PathVariable String userId) {
        log.debug("Getting user with id='{}'...", userId);
        return findUserUseCase.findById(userId);
    }
}

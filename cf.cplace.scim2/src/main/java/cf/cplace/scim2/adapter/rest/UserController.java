package cf.cplace.scim2.adapter.rest;

import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import cf.cplace.scim2.domain.UserRepository;
import com.bettercloud.scim2.server.annotation.ScimResource;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.messages.SearchRequest;
import com.unboundid.scim2.common.types.UserResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@ScimResource(description = "Access User Resources", name = "User", schema = UserResource.class)
@CplaceRequestMapping(path = "/cf.cplace.scim2/Users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = Preconditions.checkNotNull(userRepository);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResource createUser(@RequestBody UserResource user) throws IOException {
        log.debug("Creating user with userName='{}'...", user.getUserName());
        return userRepository.create(user);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource updateUser(@RequestBody UserResource user, @PathVariable String userId) throws IOException {
        log.debug("Updating user with id={}, userName='{}'...", userId, user.getUserName());
        return userRepository.update(user);
    }

    @PatchMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource patchUser(@RequestBody UserResource partialUser, @PathVariable String userId) throws IOException {
        log.debug("Patching user with id={}, userName='{}'...", userId, partialUser.getUserName());
        return userRepository.patch(userId, partialUser);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        log.debug("Deleting user with id='{}'...", userId);
        userRepository.deleteById(userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListResponse<UserResource> findUsers(SearchRequest searchRequest) {
        log.debug("Finding users that match {}...", searchRequest);
        return userRepository.find(searchRequest);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource getUser(@PathVariable String userId) {
        log.debug("Getting user with id='{}'...", userId);
        return userRepository.findById(userId);
    }
}

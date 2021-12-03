package cf.cplace.scim2.usecase.impl;

import cf.cplace.scim2.domain.UserRepository;
import cf.cplace.scim2.usecase.CreateUserUseCase;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public class UserApplication implements CreateUserUseCase {

    private final UserRepository userRepository;

    public UserApplication(UserRepository userRepository) {
        this.userRepository = Preconditions.checkNotNull(userRepository);
    }

    @Nonnull
    @Override
    public UserResource createUser(@Nonnull UserResource user) {
        return userRepository.create(user);
    }
}

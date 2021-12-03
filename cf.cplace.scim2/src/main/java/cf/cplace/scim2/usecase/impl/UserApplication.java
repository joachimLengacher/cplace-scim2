package cf.cplace.scim2.usecase.impl;

import cf.cplace.scim2.domain.UserRepository;
import cf.cplace.scim2.usecase.CreateUserUseCase;
import cf.cplace.scim2.usecase.FindUserUseCase;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public class UserApplication implements CreateUserUseCase, FindUserUseCase {

    private final UserRepository userRepository;

    public UserApplication(UserRepository userRepository) {
        this.userRepository = Preconditions.checkNotNull(userRepository);
    }

    @Nonnull
    @Override
    public UserResource createUser(@Nonnull UserResource user) {
        return userRepository.create(user);
    }

    @Nonnull
    @Override
    public UserResource findById(@Nonnull String id) {
        return  userRepository.findById(id);
    }
}

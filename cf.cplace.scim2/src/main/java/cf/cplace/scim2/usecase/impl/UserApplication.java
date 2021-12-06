package cf.cplace.scim2.usecase.impl;

import cf.cplace.scim2.domain.UserRepository;
import cf.cplace.scim2.usecase.CreateUserUseCase;
import cf.cplace.scim2.usecase.DeleteUserUseCase;
import cf.cplace.scim2.usecase.FindUserUseCase;
import cf.cplace.scim2.usecase.UpdateUserUseCase;
import com.google.common.base.Preconditions;
import com.unboundid.scim2.common.types.UserResource;

import javax.annotation.Nonnull;

public class UserApplication implements CreateUserUseCase, FindUserUseCase, UpdateUserUseCase, DeleteUserUseCase {

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

    @Nonnull
    @Override
    public UserResource updateUser(@Nonnull UserResource user) {
        return userRepository.update(user);
    }

    @Override
    public void deleteById(@Nonnull String id) {
        userRepository.deleteById(id);
    }
}

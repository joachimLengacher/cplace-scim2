package cf.cplace.scim2.assembly;

import cf.cplace.platform.api.spring.annotation.Exported;
import cf.cplace.scim2.adapter.cplace.CplaceUserRepository;
import cf.cplace.scim2.adapter.rest.GlobalScimExceptionHandler;
import cf.cplace.scim2.adapter.rest.UserController;
import cf.cplace.scim2.domain.UserRepository;
import cf.cplace.scim2.usecase.impl.UserApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This plugin's central Spring configuration that is picked up by cplace at start-up.
 */
@Configuration
public class PluginSpringConfiguration {

    @Bean
    public UserRepository userRepository() {
        return new CplaceUserRepository();
    }

    @Bean
    public UserApplication userApplication(UserRepository userRepository) {
        return new UserApplication(userRepository);
    }

    @Exported
    @Bean("cf.cplace.scim2.userController")
    public UserController userController(UserApplication userApplication) {
        return new UserController(userApplication, userApplication, userApplication);
    }

    @Exported
    @Bean("cf.cplace.scim2.globalScimExceptionHandler")
    public GlobalScimExceptionHandler globalScimExceptionHandler() {
        return new GlobalScimExceptionHandler();
    }
}

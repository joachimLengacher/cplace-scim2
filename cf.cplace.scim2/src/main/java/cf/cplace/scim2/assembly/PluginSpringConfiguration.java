package cf.cplace.scim2.assembly;

import cf.cplace.platform.api.spring.annotation.Exported;
import cf.cplace.scim2.adapter.cplace.CplaceGroupRepository;
import cf.cplace.scim2.adapter.cplace.CplaceUserRepository;
import cf.cplace.scim2.adapter.rest.GlobalScimExceptionHandler;
import cf.cplace.scim2.adapter.rest.GroupController;
import cf.cplace.scim2.adapter.rest.UserController;
import cf.cplace.scim2.domain.GroupRepository;
import cf.cplace.scim2.domain.UserRepository;
import cf.cplace.scim2.usecase.impl.GroupApplication;
import cf.cplace.scim2.usecase.impl.UserApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This plugin's central Spring configuration that is picked up by cplace at start-up.
 */
@Configuration
public class PluginSpringConfiguration {

    @Bean
    public UserRepository userRepository(@Value("${scim2.service-provider-config.filter.max-results}") int maxResults) {
        return new CplaceUserRepository(maxResults);
    }

    @Bean
    public GroupRepository groupRepository(@Value("${scim2.service-provider-config.filter.max-results}") int maxResults) {
        return new CplaceGroupRepository(maxResults);
    }

    @Bean
    public UserApplication userApplication(UserRepository userRepository) {
        return new UserApplication(userRepository);
    }

    @Bean
    public GroupApplication groupApplication(GroupRepository groupRepository) {
        return new GroupApplication(groupRepository);
    }

    @Exported
    @Bean("cf.cplace.scim2.userController")
    public UserController userController(UserApplication userApplication) {
        return new UserController(userApplication, userApplication, userApplication, userApplication);
    }

    @Exported
    @Bean("cf.cplace.scim2.groupController")
    public GroupController groupController(GroupApplication groupApplication) {
        return new GroupController(groupApplication, groupApplication, groupApplication);
    }

    @Exported
    @Bean("cf.cplace.scim2.globalScimExceptionHandler")
    public GlobalScimExceptionHandler globalScimExceptionHandler() {
        return new GlobalScimExceptionHandler();
    }
}

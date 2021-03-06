package cf.cplace.scim2.assembly;

import cf.cplace.platform.api.spring.annotation.Exported;
import cf.cplace.scim2.adapter.cplace.CplaceGroupRepository;
import cf.cplace.scim2.adapter.cplace.CplaceUserRepository;
import cf.cplace.scim2.adapter.rest.GlobalScimExceptionHandler;
import cf.cplace.scim2.adapter.rest.GroupController;
import cf.cplace.scim2.adapter.rest.UserController;
import cf.cplace.scim2.domain.GroupRepository;
import cf.cplace.scim2.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * This plugin's central Spring configuration that is picked up by cplace at start-up.
 */
@Configuration
public class PluginSpringConfiguration {

    private static final Logger log = LoggerFactory.getLogger(PluginSpringConfiguration.class);

    @Bean
    public UserRepository userRepository(@Value("${scim2.service-provider-config.filter.max-results}") int maxResults) {
        return new CplaceUserRepository(maxResults);
    }

    @Bean
    public GroupRepository groupRepository(@Value("${scim2.service-provider-config.filter.max-results}") int maxResults) {
        return new CplaceGroupRepository(maxResults);
    }

    @Exported
    @Bean("cf.cplace.scim2.userController")
    public UserController userController(UserRepository userRepository) {
        return new UserController(userRepository);
    }

    @Exported
    @Bean("cf.cplace.scim2.groupController")
    public GroupController groupController(GroupRepository groupRepository) {
        return new GroupController(groupRepository);
    }

    @Exported
    @Bean("cf.cplace.scim2.globalScimExceptionHandler")
    public GlobalScimExceptionHandler globalScimExceptionHandler() {
        return new GlobalScimExceptionHandler();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logStartup() {
        log.info("SCIM2 plugin is active!");
    }
}

package cf.cplace.scim2.assembly;

import cf.cplace.platform.application.security.BasicAuthenticationProvider;
import cf.cplace.platform.application.security.CplaceWebSecurityConfigurerAdapter;
import cf.cplace.platform.internal.api.configuration.WebEndpointPathConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class allows us to "hook" some special security configuration into the cplace application context. See
 * https://docs.cplace.io/dev-docs/cplace-architecture/spring-in-plugins#hooking-into-the-application-spring-context
 * for more details on this.
 */
@Configuration
public class PlatformExtensionsSpringConfiguration {

    @Bean("cf.cplace.scim2.noCsrfSecurityConfigurerAdapter")
    public CplaceWebSecurityConfigurerAdapter noCsrfSecurityConfigurerAdapter(
            WebEndpointPathConfiguration webEndpointPathConfiguration,
            BasicAuthenticationProvider basicAuthenticationProvider
    ) {
        return new NoCsrfCplaceWebSecurityConfigurerAdapter(
                webEndpointPathConfiguration.getWebEndpointPathPattern(),
                basicAuthenticationProvider
        );
    }
}

package cf.cplace.scim2.assembly;

import cf.cplace.platform.application.rest.TenantResourcesFilter;
import cf.cplace.platform.application.security.CplaceWebSecurityConfigurerAdapter;
import com.google.common.base.Preconditions;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@link CplaceWebSecurityConfigurerAdapter} that modifies the standard cplace security configuration for the
 * needs of this SCIM plugin. In particular, it disables CSRF protection and enforces stateless session management.
 */
@Order(13)
@ParametersAreNonnullByDefault
public class NoCsrfCplaceWebSecurityConfigurerAdapter extends CplaceWebSecurityConfigurerAdapter {

    private final String webEndpointPathPattern;
    private final AuthenticationProvider authenticationProvider;

    public NoCsrfCplaceWebSecurityConfigurerAdapter(String webEndpointPathPattern, AuthenticationProvider authenticationProvider) {
        this.webEndpointPathPattern = Preconditions.checkNotNull(webEndpointPathPattern);
        this.authenticationProvider = Preconditions.checkNotNull(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**/cf.cplace.scim2/**")
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new TenantResourcesFilter(webEndpointPathPattern), SecurityContextPersistenceFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }
}

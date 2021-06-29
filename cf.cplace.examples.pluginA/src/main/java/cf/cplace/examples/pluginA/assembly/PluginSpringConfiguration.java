package cf.cplace.examples.pluginA.assembly;

import cf.cplace.examples.pluginA.api.Multiplier;
import cf.cplace.examples.pluginA.impl.DefaultMultiplier;
import cf.cplace.platform.api.configuration.CplaceConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PluginSpringConfiguration {

    @Bean
    public Multiplier multiplier(CplaceConfiguration cplaceConfiguration) {
        return new DefaultMultiplier(cplaceConfiguration.isMultiTenancy());
    }

}

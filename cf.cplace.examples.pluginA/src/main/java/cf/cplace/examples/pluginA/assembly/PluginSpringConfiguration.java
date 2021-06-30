package cf.cplace.examples.pluginA.assembly;

import cf.cplace.examples.pluginA.api.Multiplier;
import cf.cplace.examples.pluginA.impl.DefaultMultiplier;
import cf.cplace.platform.api.configuration.CplaceConfiguration;
import cf.cplace.platform.api.spring.annotation.Exported;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class PluginSpringConfiguration {

    @Exported
    @Bean("cf.cplace.examples.pluginA.multiplier")
    public Multiplier multiplier(CplaceConfiguration cplaceConfiguration) {
        return new DefaultMultiplier(cplaceConfiguration.isMultiTenancy());
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Destroy Plugin A");
    }

}

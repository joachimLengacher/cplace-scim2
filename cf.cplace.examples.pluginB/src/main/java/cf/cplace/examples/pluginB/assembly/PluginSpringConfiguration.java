package cf.cplace.examples.pluginB.assembly;

import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PreDestroy;

@ComponentScan("cf.cplace.examples.pluginB.rest")
public class PluginSpringConfiguration {

    @PreDestroy
    public void preDestroy() {
        System.out.println("Destroy Plugin B");
    }
}

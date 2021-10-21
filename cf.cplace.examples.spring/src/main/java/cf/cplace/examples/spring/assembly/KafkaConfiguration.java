package cf.cplace.examples.spring.assembly;

import cf.cplace.examples.spring.adapter.kafka.DirectorCreatedEvent;
import cf.cplace.examples.spring.adapter.kafka.KafkaMessaging;
import cf.cplace.examples.spring.domain.port.Messaging;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@Import({KafkaProducerConfig.class, KafkaConsumerConfig.class})
public class KafkaConfiguration {

    @Bean
    public NewTopic directorCreationTopic() {
        return TopicBuilder.name("directorCreation").build();
    }

    @Bean
    public Messaging messaging(KafkaTemplate<String, DirectorCreatedEvent> kafkaTemplate) {
        return new KafkaMessaging(kafkaTemplate);
    }
}

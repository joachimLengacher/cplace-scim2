package cf.cplace.examples.spring.adapter.kafka;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.port.Messaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaMessaging implements Messaging {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessaging.class);

    private final KafkaTemplate<String, DirectorCreatedEvent> kafkaTemplate;

    public KafkaMessaging(KafkaTemplate<String, DirectorCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void directorCreated(Director director) {
        kafkaTemplate.send("directorCreation", toEvent(director));
    }

    @KafkaListener(topics = "directorCreation", groupId = "directors")
    public void logDirectorCreation(DirectorCreatedEvent directorCreatedEvent) {
        log.info("Director '{}' has been created with id='{}'", directorCreatedEvent.getName(), directorCreatedEvent.getId());
    }

    private DirectorCreatedEvent toEvent(Director director) {
        return new DirectorCreatedEvent(director.getId(), director.getName());
    }
}

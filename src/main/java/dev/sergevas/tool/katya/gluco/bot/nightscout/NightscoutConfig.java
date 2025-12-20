package dev.sergevas.tool.katya.gluco.bot.nightscout;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent.NsEntryAppEventListener;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent.NsEntryAppEventPublisher;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent.NsEntryPublishedLoggingHandler;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.NsEntryEntityJpaRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.NsEntryPersistenceAdapter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest.NsEntryAssembler;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.*;
import dev.sergevas.tool.katya.gluco.bot.readings.control.SensorDataReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableJpaRepositories(basePackages = {
        "dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence"
})
@EnableAsync
public class NightscoutConfig {

    @Bean
    public NsEntryAssembler nsEntryAssembler() {
        return new NsEntryAssembler();
    }

    @Bean
    public NsEntryPublisher nsEntryPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new NsEntryAppEventPublisher(applicationEventPublisher);
    }

    @Bean
    public NsEntryPublishedUseCase nsEntryPublishedLoggingHandler() {
        return new NsEntryPublishedLoggingHandler();
    }

    @Bean
    public NsEntryAppEventListener nsEntryAppEventListener(NsEntryPublishedUseCase nsEntryPublishedUseCase) {
        return new NsEntryAppEventListener(nsEntryPublishedUseCase);
    }

    @Bean
    public NsEntryPersistenceAdapter sensorDataPersistenceAdapter(NsEntryEntityJpaRepository entityJpaRepository,
                                                                  NsEntryPublisher nsEntryNotifier) {
        return new NsEntryPersistenceAdapter(entityJpaRepository, nsEntryNotifier);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "sensor.data.source", havingValue = "nightscout")
    public FromNsEntryMapper fromNsEntryMapper() {
        return new FromNsEntryMapper();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "sensor.data.source", havingValue = "nightscout")
    public SensorDataReader sensorDataReader(FromNsEntryMapper fromNsEntryMapper,
                                             NsEntryRepository nsEntryRepository) {
        return new NsEntrySensorDataReader(fromNsEntryMapper, nsEntryRepository);
    }
}

package dev.sergevas.tool.katya.gluco.bot.nightscout;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent.FirstNsEntryPublishedLoggingHandler;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent.NsEntryAppEventPublisher;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.appevent.SecondNsEntryPublishedLoggingHandler;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.NsEntryEntityJpaRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.NsEntryPersistenceAdapter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest.NsEntryAssembler;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.FromNsEntryMapper;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryPublishedUseCase;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryPublisher;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntrySensorDataReader;
import dev.sergevas.tool.katya.gluco.bot.readings.control.SensorDataReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence"
})
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
    public NsEntryPublishedUseCase firstNsEntryPublishedLoggingHandler() {
        return new FirstNsEntryPublishedLoggingHandler();
    }

    @Bean
    public NsEntryPublishedUseCase secondNsEntryPublishedLoggingHandler() {
        return new SecondNsEntryPublishedLoggingHandler();
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

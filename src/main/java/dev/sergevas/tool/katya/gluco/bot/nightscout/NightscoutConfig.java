package dev.sergevas.tool.katya.gluco.bot.nightscout;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.NsEntryEntityJpaRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.NsEntryPersistenceAdapter;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.FromNsEntryMapper;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntrySensorDataReader;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.SensorDataReader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence"
})
public class NightscoutConfig {

    @Bean
    public NsEntryPersistenceAdapter sensorDataPersistenceAdapter(NsEntryEntityJpaRepository entityJpaRepository) {
        return new NsEntryPersistenceAdapter(entityJpaRepository);
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

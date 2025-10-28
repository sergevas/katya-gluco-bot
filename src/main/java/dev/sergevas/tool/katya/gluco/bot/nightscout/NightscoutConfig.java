package dev.sergevas.tool.katya.gluco.bot.nightscout;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.NsEntryEntityJpaRepository;
import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.SensorDataPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence"
})
public class NightscoutConfig {

    @Bean
    public SensorDataPersistenceAdapter sensorDataPersistenceAdapter(NsEntryEntityJpaRepository entityJpaRepository) {
        return new SensorDataPersistenceAdapter(entityJpaRepository);
    }
}

package dev.sergevas.tool.katya.gluco.bot.readings;

import dev.sergevas.tool.katya.gluco.bot.readings.control.LastReadingCacheManager;
import dev.sergevas.tool.katya.gluco.bot.readings.control.ReadingService;
import dev.sergevas.tool.katya.gluco.bot.readings.control.SensorDataReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReadingsConfig {

    @Bean
    public LastReadingCacheManager lastReadingCacheManager() {
        return new LastReadingCacheManager();
    }

    @Bean
    public ReadingService readingService(SensorDataReader sensorDataReader,
                                         LastReadingCacheManager lastReadingCacheManager) {
        return new ReadingService(sensorDataReader, lastReadingCacheManager);
    }


}

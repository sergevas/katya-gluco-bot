package dev.sergevas.tool.katya.gluco.bot.config;

import dev.sergevas.tool.katya.gluco.bot.telegram.control.SchedulerControls;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfig {

    @Bean
    public SchedulerControls schedulerControls(@Value("${scheduler.period.accelerated}") Long periodAccelerated,
                                               @Value("${scheduler.period.default}") Long periodDefault,
                                               @Value("${scheduler.period.alert}") Long periodAlert) {
        return new SchedulerControls(periodAccelerated, periodDefault, periodAlert);
    }
}

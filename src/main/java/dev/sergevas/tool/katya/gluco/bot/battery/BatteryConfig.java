package dev.sergevas.tool.katya.gluco.bot.battery;

import dev.sergevas.tool.katya.gluco.bot.battery.boundary.BatteryLevelChangeListener;
import dev.sergevas.tool.katya.gluco.bot.battery.control.BatteryService;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class BatteryConfig {

    @Bean
    public BatteryService batteryService(KatyaGlucoBot katyaGlucoBot) {
        return new BatteryService(katyaGlucoBot);
    }

    @Bean
    public BatteryLevelChangeListener batteryLevelChangeListener(BatteryService batteryService) {
        return new BatteryLevelChangeListener(batteryService);
    }
}

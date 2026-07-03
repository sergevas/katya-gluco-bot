package dev.sergevas.tool.katya.gluco.bot.battery.boundary;

import dev.sergevas.tool.katya.gluco.bot.battery.control.BatteryService;
import dev.sergevas.tool.katya.gluco.bot.battery.entity.Battery;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public class BatteryLevelChangeListener {

    private final BatteryService batteryService;

    public BatteryLevelChangeListener(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @EventListener
    @Async
    public void onBatteryEventPublished(Battery battery) {
        this.batteryService.processBatteryLevelChange(battery);
    }
}

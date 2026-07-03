package dev.sergevas.tool.katya.gluco.bot.battery.control;

import dev.sergevas.tool.katya.gluco.bot.battery.entity.Battery;
import dev.sergevas.tool.katya.gluco.bot.telegram.boundary.KatyaGlucoBot;
import dev.sergevas.tool.katya.gluco.bot.telegram.control.TextMessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.sergevas.tool.katya.gluco.bot.battery.entity.BatteryState.DECREASE;
import static dev.sergevas.tool.katya.gluco.bot.battery.entity.BatteryState.INCREASE;

public class BatteryService {

    private static final Logger LOG = LoggerFactory.getLogger(BatteryService.class);

    private final KatyaGlucoBot katyaGlucoBot;

    public BatteryService(KatyaGlucoBot katyaGlucoBot) {
        this.katyaGlucoBot = katyaGlucoBot;
    }

    public void processBatteryLevelChange(Battery battery) {
        LOG.info("Enter processBatteryLevelChange {}", battery);
        if (DECREASE.equals(battery.state())) {
            katyaGlucoBot.sendSensorReadingUpdateToAll(TextMessageFormatter.formatBatteryLowAlert(battery.batteryLevel()));
        } else if (INCREASE.equals(battery.state())) {
            katyaGlucoBot.sendSensorReadingUpdateToAll(TextMessageFormatter.formatBatteryFullyChargedLowAlert(battery.batteryLevel()));
        } else {
            LOG.warn("Illegal battery state {}", battery);
        }
        LOG.info("Exit processBatteryLevelChange");
    }
}

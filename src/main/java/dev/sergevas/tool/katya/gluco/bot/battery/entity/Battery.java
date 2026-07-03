package dev.sergevas.tool.katya.gluco.bot.battery.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record Battery(@NotNull BatteryState state, @NotNull @PositiveOrZero Integer batteryLevel) {
}

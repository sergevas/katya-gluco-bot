package dev.sergevas.tool.katya.gluco.bot.nightscout.entity;

import java.time.OffsetDateTime;

public record NsEntry(
        String type,
        String device,
        OffsetDateTime sgvTime,
        Long epochTime,
//        Sensor Glucose Value
        Integer sgv,
        Double delta,
        String direction,
        Integer noise,
        Integer filtered,
        Integer unfiltered,
        Integer rssi) {

    public double sgvMmolL() {
        return 0.055555555555555552472 * sgv;
    }
}

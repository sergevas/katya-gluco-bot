package dev.sergevas.tool.katya.gluco.bot.nightscout.entity;

import java.time.OffsetDateTime;
import java.util.Objects;

public record NsEntry(
        Long id,
        String type,
        String device,
        OffsetDateTime sgvTime,
        Long epochTime,
        Integer sgv, // Sensor Glucose Value
        Double delta,
        String direction,
        Integer noise,
        Integer filtered,
        Integer unfiltered,
        Integer rssi) {

    public double sgvMmolL() {
        return 0.055555555555555552472 * sgv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsEntry nsEntry = (NsEntry) o;
        return Objects.equals(type, nsEntry.type)
                && Objects.equals(sgv, nsEntry.sgv)
                && Objects.equals(delta, nsEntry.delta)
                && Objects.equals(rssi, nsEntry.rssi)
                && Objects.equals(device, nsEntry.device)
                && Objects.equals(noise, nsEntry.noise)
                && Objects.equals(epochTime, nsEntry.epochTime)
                && Objects.equals(direction, nsEntry.direction)
                && Objects.equals(filtered, nsEntry.filtered)
                && Objects.equals(unfiltered, nsEntry.unfiltered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, device, epochTime, sgv, delta, direction, noise, filtered, unfiltered, rssi);
    }
}

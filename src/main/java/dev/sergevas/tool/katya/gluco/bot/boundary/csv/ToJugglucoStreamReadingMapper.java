package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.entity.JugglucoStreamReading;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ToJugglucoStreamReadingMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss Z");

    public static List<JugglucoStreamReading> toJugglucoStreamReadingList(List<CsvStreamReading> csv) {
        return Optional.ofNullable(csv).orElse(List.of()).stream()
                .map(ToJugglucoStreamReadingMapper::toJugglucoStreamReading)
                .sorted(Comparator.comparing(JugglucoStreamReading::getUnixTime))
                .toList();
    }

    public static JugglucoStreamReading toJugglucoStreamReading(CsvStreamReading csv) {
        return new JugglucoStreamReading(
                csv.getSensorId(),
                Integer.parseInt(csv.getNr()),
                Long.parseLong(csv.getUnixTime()),
                OffsetDateTime.parse(csv.getTimestamp() + " +0" + csv.getTz() + "00", FORMATTER),
                Integer.parseInt(csv.getMin()),
                Double.parseDouble(csv.getReading()),
                Double.parseDouble(csv.getRate()),
                ChangeStatus.valueOf(csv.getChangeLabel())
        );
    }
}

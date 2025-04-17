package dev.sergevas.tool.katya.gluco.bot.boundary.csv;

import dev.sergevas.tool.katya.gluco.bot.entity.ChangeStatus;
import dev.sergevas.tool.katya.gluco.bot.entity.ICanReading;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ToJugglucoStreamReadingMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss Z");

    public static List<ICanReading> toJugglucoStreamReadingList(List<CsvStreamReading> csv) {
        return Optional.ofNullable(csv).orElse(List.of()).stream()
                .map(ToJugglucoStreamReadingMapper::toJugglucoStreamReading)
                .sorted(Comparator.comparing(ICanReading::getUnixTime))
                .toList();
    }

    public static ICanReading toJugglucoStreamReading(CsvStreamReading csv) {
        return new ICanReading(
                Long.parseLong(csv.getTime()),
                Double.parseDouble(csv.getValue_mmol()),
                ChangeStatus.fromName(csv.getDirection())
        );
    }
}

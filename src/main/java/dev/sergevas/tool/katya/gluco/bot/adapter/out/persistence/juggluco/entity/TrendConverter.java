package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Optional;

@Converter(autoApply = true)
public class TrendConverter implements AttributeConverter<Trend, String> {
    @Override
    public String convertToDatabaseColumn(Trend attribute) {
        return Optional.ofNullable(attribute).map(Enum::name).orElse(null);
    }

    @Override
    public Trend convertToEntityAttribute(String dbData) {
        return Optional.ofNullable(dbData).map(Trend::valueOf).orElse(null);
    }
}

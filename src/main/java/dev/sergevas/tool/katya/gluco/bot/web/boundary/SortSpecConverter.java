package dev.sergevas.tool.katya.gluco.bot.web.boundary;

import dev.sergevas.tool.katya.gluco.bot.web.control.SortSpec;
import org.springframework.core.convert.converter.Converter;

public class SortSpecConverter implements Converter<String, SortSpec> {

    @Override
    public SortSpec convert(String source) {
        String[] parts = source.split(",");
        var propertyName = parts[0];
        var ascending = parts.length < 2 || !parts[1].equalsIgnoreCase("desc");
        return new SortSpec(propertyName, ascending);
    }
}

package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import org.springframework.data.domain.Sort;

import java.util.Map;

public class SortFieldMapper {

    private static final Map<String, String> API_TO_ENTITY_FIELD_MAP = Map.of(
            "dateString", "sgvTime"
    );

    public static String mapApiFieldToEntityField(String apiField) {
        return API_TO_ENTITY_FIELD_MAP.getOrDefault(apiField, apiField);
    }

    public static Sort map(Sort sort) {
        return Sort.by(sort.stream()
                .map(order -> {
                    var apiProperty = order.getProperty();
                    var dbProperty = SortFieldMapper.mapApiFieldToEntityField(apiProperty);
                    return new Sort.Order(order.getDirection(), dbProperty);
                })
                .toList());
    }
}

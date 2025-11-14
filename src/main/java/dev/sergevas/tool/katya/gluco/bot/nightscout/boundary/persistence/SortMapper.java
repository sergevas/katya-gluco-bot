package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

public class SortMapper {

    public static Sort toEntitySort(List<String> sortParams) {
        if (Objects.isNull(sortParams) || sortParams.isEmpty()) {
            return Sort.by(Sort.Direction.DESC, "sgvTime");
        }
        List<Sort.Order> orders = sortParams.stream()
                .map(SortMapper::createOrder)
                .toList();
        return Sort.by(orders);
    }

    public static Sort.Order createOrder(String sortParam) {
        var parts = sortParam.split(",");
        var apiField = parts[0].trim();
        Sort.Direction direction = parts.length > 1
                ? Sort.Direction.fromString(parts[1].trim())
                : Sort.Direction.ASC;

        String entityField = switch (apiField) {
            case "dateString" -> "sgvTime";  // Mapping to NsEntryEntity field
            default -> apiField;
        };
        return new Sort.Order(direction, entityField);
    }
}

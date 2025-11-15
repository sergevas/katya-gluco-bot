package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.web.control.SortSpec;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

public class SortMapper {

    public static Sort toEntitySort(List<SortSpec> sortParams) {
        if (Objects.isNull(sortParams) || sortParams.isEmpty()) {
            return Sort.by(DESC, "sgvTime");
        }
        List<Sort.Order> orders = sortParams.stream()
                .map(SortMapper::createOrder)
                .toList();
        return Sort.by(orders);
    }

    public static Sort.Order createOrder(SortSpec sortSpec) {
        String entityField = switch (sortSpec.propertyName()) {
            case "dateString" -> "sgvTime";  // Mapping to NsEntryEntity field
            default -> sortSpec.propertyName();
        };
        return new Sort.Order(sortSpec.ascending() ? ASC : DESC, entityField);
    }
}

package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.rest;

import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.function.Function;

public class SortFieldMapper {

    private enum ApiToEntity {

        DATE_STRING("dateString", "sgvTime");

        private final String apiField;
        private final String entityField;

        ApiToEntity(String apiField, String entityField) {
            this.apiField = apiField;
            this.entityField = entityField;
        }

        public static String getApiField(String entityField) {
            return Arrays.stream(ApiToEntity.values())
                    .filter(ate -> ate.entityField.equals(entityField))
                    .findFirst()
                    .map(mapping -> mapping.apiField)
                    .orElse(entityField);
        }

        public static String getEntityField(String apiField) {
            return Arrays.stream(ApiToEntity.values())
                    .filter(ate -> ate.apiField.equals(apiField))
                    .findFirst()
                    .map(mapping -> mapping.entityField)
                    .orElse(apiField);
        }
    }

    public static Sort map(final Sort sort, final Function<String, String> fieldMapper) {
        return Sort.by(sort.stream()
                .map(order -> new Sort.Order(order.getDirection(),
                        fieldMapper.apply(ApiToEntity.getEntityField(order.getProperty()))))
                .toList());
    }

    public static Sort mapToEntity(Sort sort) {
        return map(sort, ApiToEntity::getEntityField);
    }

    public static Sort mapToApi(Sort sort) {
        return map(sort, ApiToEntity::getApiField);
    }
}

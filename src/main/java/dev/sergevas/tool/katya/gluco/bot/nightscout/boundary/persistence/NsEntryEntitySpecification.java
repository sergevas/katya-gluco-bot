package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.NsEntryEntity;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static java.util.Optional.ofNullable;

public class NsEntryEntitySpecification implements Specification<NsEntryEntity> {

    @Override
    public Predicate toPredicate(Root<NsEntryEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.conjunction();
    }

    public static Specification<NsEntryEntity> toSpecification(NsEntryFilter filter) {
        return build(
                filter.device(),
                filter.direction(),
                filter.sgvTimeExact(),
                filter.sgvTimeFrom(),
                filter.sgvTimeTo()
        );
    }

    public static Specification<NsEntryEntity> build(final String device,
                                                     final String direction,
                                                     final OffsetDateTime sgvTime,
                                                     final OffsetDateTime fromSgvTime,
                                                     final OffsetDateTime toSgvTime) {
        final var specs = new ArrayList<Specification<NsEntryEntity>>();
        ofNullable(device).ifPresent(d -> specs.add(byDevice(d)));
        ofNullable(direction).ifPresent(dir -> specs.add(byDirection(dir)));
        ofNullable(sgvTime).ifPresent(time -> specs.add(bySgvTime(time)));
        ofNullable(fromSgvTime).ifPresent(fromTime -> specs.add(bySgvTimeGreaterThanOrEqualTo(fromTime)));
        ofNullable(toSgvTime).ifPresent(toTime -> specs.add(bySgvTimeLessThanOrEqualTo(toTime)));
        return specs.stream()
                .filter(Objects::nonNull)
                .reduce(Specification::and)
                .orElse(null);
    }

    public static Specification<NsEntryEntity> byDevice(final String device) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("device"), device);
    }

    public static Specification<NsEntryEntity> byDirection(final String direction) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("direction"), direction);
    }

    public static Specification<NsEntryEntity> bySgvTime(final OffsetDateTime sgvTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sgvTime"), sgvTime);
    }

    public static Specification<NsEntryEntity> bySgvTimeGreaterThanOrEqualTo(final OffsetDateTime sgvTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("sgvTime"), sgvTime);
    }

    public static Specification<NsEntryEntity> bySgvTimeLessThanOrEqualTo(final OffsetDateTime sgvTime) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("sgvTime"), sgvTime);
    }
}

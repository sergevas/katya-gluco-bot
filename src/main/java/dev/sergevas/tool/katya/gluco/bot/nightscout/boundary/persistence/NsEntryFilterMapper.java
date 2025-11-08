package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.NsEntryEntity;
import dev.sergevas.tool.katya.gluco.bot.nightscout.control.NsEntryFilter;
import org.springframework.data.jpa.domain.Specification;

public class NsEntryFilterMapper {

    public static Specification<NsEntryEntity> toSpecification(NsEntryFilter filter) {
        return NsEntryEntitySpecification.build(
                filter.device(),
                filter.direction(),
                null, // ты можешь добавить преобразование времён, если нужно
                null
        );
    }
}

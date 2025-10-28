package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.NsEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface NsEntryEntityJpaRepository extends JpaRepository<NsEntryEntity, Long> {

    List<NsEntryEntity> findByEpochTime(Collection<Long> epochTimeValues);
}

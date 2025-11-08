package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.NsEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface NsEntryEntityJpaRepository extends JpaRepository<NsEntryEntity, Long>, JpaSpecificationExecutor<NsEntryEntity> {

    List<NsEntryEntity> findByEpochTime(Collection<Long> epochTimeValues);

    Optional<NsEntryEntity> findFirstByOrderByEpochTimeDesc();
}

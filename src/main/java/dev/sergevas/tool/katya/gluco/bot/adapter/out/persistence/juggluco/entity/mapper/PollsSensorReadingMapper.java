package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.mapper;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.PollsSensorReadingEntity;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedTargetPolicy = IGNORE, unmappedSourcePolicy = IGNORE)
public interface PollsSensorReadingMapper {

    @Mapping(target = "glucose", source = "glucoseMgDl")
    PollsSensorReadingEntity toPollsSensorReadingEntity(PollsSensorReading pollsSensorReading);

    @InheritInverseConfiguration
    PollsSensorReading toPollsSensorReading(PollsSensorReadingEntity pollsSensorReadingEntity);
}

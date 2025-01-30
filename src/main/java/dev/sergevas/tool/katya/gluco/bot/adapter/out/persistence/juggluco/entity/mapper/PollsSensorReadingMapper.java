package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.mapper;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity.PollsSensorReadingEntity;
import dev.sergevas.tool.katya.gluco.bot.domain.juggluco.PollsSensorReading;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PollsSensorReadingMapper {

    @Mapping(target = "glucose", source = "glucoseMgDl")
    @Mapping(target = "id", ignore = true)
    PollsSensorReadingEntity toPollsSensorReadingEntity(PollsSensorReading pollsSensorReading);
}

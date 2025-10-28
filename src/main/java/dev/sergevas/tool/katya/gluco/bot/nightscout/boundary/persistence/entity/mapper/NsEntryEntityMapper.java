package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.mapper;

import dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity.NsEntryEntity;
import dev.sergevas.tool.katya.gluco.bot.nightscout.entity.NsEntry;

public class NsEntryEntityMapper {

    public static NsEntryEntity toNsEntryEntity(NsEntry nsE) {
        return new NsEntryEntity(
                nsE.type(),
                nsE.device(),
                nsE.sgvTime(),
                nsE.epochTime(),
                nsE.sgv(),
                nsE.delta(),
                nsE.direction(),
                nsE.noise(),
                nsE.filtered(),
                nsE.unfiltered(),
                nsE.rssi());
    }

    public static NsEntry toNsEntry(NsEntryEntity nsEe) {
        return new NsEntry(
                nsEe.getTyp(),
                nsEe.getDevice(),
                nsEe.getSgvTime(),
                nsEe.getEpochTime(),
                nsEe.getSgv(),
                nsEe.getDelta(),
                nsEe.getDirection(),
                nsEe.getNoise(),
                nsEe.getFiltered(),
                nsEe.getUnfiltered(),
                nsEe.getRssi()
        );
    }
}

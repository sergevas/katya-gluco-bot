package dev.sergevas.tool.katya.gluco.bot.nightscout.boundary.persistence.entity;

import dev.sergevas.tool.katya.gluco.bot.persistence.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "NS_ENTRY")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "NS_ENTRY_ID_SEQ", allocationSize = 1)
@NamedQuery(name = "NsEntryEntity.findByEpochTime",
        query = "select e from NsEntryEntity e where e.epochTime in :epochTimeValues")
@NamedQuery(name = "NsEntryEntity.findAll", query = "select e from NsEntryEntity e")
public class NsEntryEntity extends BaseEntity {

    private String type;
    private OffsetDateTime sgvTime;
    private long epochTime;
    private Integer sgv;
    private String direction;
    private Integer noise;
    private Integer filtered;
    private Integer unfiltered;
    private Integer rssi;

    public NsEntryEntity() {
    }

    public NsEntryEntity(String type, OffsetDateTime sgvTime, long epochTime, Integer sgv, String direction,
                         Integer noise, Integer filtered, Integer unfiltered, Integer rssi) {
        this.type = type;
        this.sgvTime = sgvTime;
        this.epochTime = epochTime;
        this.sgv = sgv;
        this.direction = direction;
        this.noise = noise;
        this.filtered = filtered;
        this.unfiltered = unfiltered;
        this.rssi = rssi;
    }

    public String getType() {
        return type;
    }

    public NsEntryEntity setType(String type) {
        this.type = type;
        return this;
    }

    public OffsetDateTime getSgvTime() {
        return sgvTime;
    }

    public NsEntryEntity setSgvTime(OffsetDateTime timeEpoch) {
        this.sgvTime = timeEpoch;
        return this;
    }

    public long getEpochTime() {
        return epochTime;
    }

    public NsEntryEntity setEpochTime(long date) {
        this.epochTime = date;
        return this;
    }

    public Integer getSgv() {
        return sgv;
    }

    public NsEntryEntity setSgv(Integer sgv) {
        this.sgv = sgv;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public NsEntryEntity setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public Integer getNoise() {
        return noise;
    }

    public NsEntryEntity setNoise(Integer noise) {
        this.noise = noise;
        return this;
    }

    public Integer getFiltered() {
        return filtered;
    }

    public NsEntryEntity setFiltered(Integer filtered) {
        this.filtered = filtered;
        return this;
    }

    public Integer getUnfiltered() {
        return unfiltered;
    }

    public NsEntryEntity setUnfiltered(Integer unfiltered) {
        this.unfiltered = unfiltered;
        return this;
    }

    public Integer getRssi() {
        return rssi;
    }

    public NsEntryEntity setRssi(Integer rssi) {
        this.rssi = rssi;
        return this;
    }

    @Override
    public String toString() {
        return "NsEntryEntity{" +
                "id='" + super.getId() + '\'' +
                "type='" + type + '\'' +
                ", sgvTime=" + sgvTime +
                ", epochTime=" + epochTime +
                ", sgv=" + sgv +
                ", direction='" + direction + '\'' +
                ", noise=" + noise +
                ", filtered=" + filtered +
                ", unfiltered=" + unfiltered +
                ", rssi=" + rssi +
                '}';
    }
}

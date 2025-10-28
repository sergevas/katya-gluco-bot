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
@NamedQuery(name = "NsEntryEntity.findByEpochTime", query = "select e from NsEntryEntity e where e.epochTime in :epochTimeValues")
public class NsEntryEntity extends BaseEntity {

    private String typ;
    private String device;
    private OffsetDateTime sgvTime;
    private Long epochTime;
    private Integer sgv;
    private Double delta;
    private String direction;
    private Integer noise;
    private Integer filtered;
    private Integer unfiltered;
    private Integer rssi;

    public NsEntryEntity() {
    }

    public NsEntryEntity(String typ, String device, OffsetDateTime sgvTime, Long epochTime, Integer sgv, Double delta,
                         String direction, Integer noise, Integer filtered, Integer unfiltered, Integer rssi) {
        this.typ = typ;
        this.device = device;
        this.sgvTime = sgvTime;
        this.epochTime = epochTime;
        this.sgv = sgv;
        this.delta = delta;
        this.direction = direction;
        this.noise = noise;
        this.filtered = filtered;
        this.unfiltered = unfiltered;
        this.rssi = rssi;
    }

    public String getTyp() {
        return typ;
    }

    public NsEntryEntity setTyp(String type) {
        this.typ = type;
        return this;
    }

    public String getDevice() {
        return device;
    }

    public NsEntryEntity setDevice(String device) {
        this.device = device;
        return this;
    }

    public OffsetDateTime getSgvTime() {
        return sgvTime;
    }

    public NsEntryEntity setSgvTime(OffsetDateTime timeEpoch) {
        this.sgvTime = timeEpoch;
        return this;
    }

    public Long getEpochTime() {
        return epochTime;
    }

    public NsEntryEntity setEpochTime(Long date) {
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

    public Double getDelta() {
        return delta;
    }

    public NsEntryEntity setDelta(Double delta) {
        this.delta = delta;
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
                "typ='" + typ + '\'' +
                "device='" + device + '\'' +
                ", sgvTime=" + sgvTime +
                ", epochTime=" + epochTime +
                ", sgv=" + sgv +
                ", delta=" + delta +
                ", direction='" + direction + '\'' +
                ", noise=" + noise +
                ", filtered=" + filtered +
                ", unfiltered=" + unfiltered +
                ", rssi=" + rssi +
                '}';
    }
}

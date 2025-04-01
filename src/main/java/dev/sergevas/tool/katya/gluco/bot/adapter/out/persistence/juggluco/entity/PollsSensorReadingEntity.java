package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "JG_POLLS")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "JG_POLLS_ID_SEQ", allocationSize = 1)
@NamedQuery(name = "PollsSensorReadingEntity.findByTimeEpoch",
        query = "select p from PollsSensorReadingEntity p where p.timeEpoch in :timeEpochValues")
@NamedQuery(name = "PollsSensorReadingEntity.findAll", query = "select p from PollsSensorReadingEntity p")
public class PollsSensorReadingEntity extends BaseEntity {

    private long timeEpoch;
    private LocalDateTime timeLocal;
    private int minSinceStart;
    private int glucose;
    private Trend trend;
    private float rateOfChange;

    public PollsSensorReadingEntity() {
    }

    public long getTimeEpoch() {
        return timeEpoch;
    }

    public void setTimeEpoch(long timeEpoch) {
        this.timeEpoch = timeEpoch;
    }

    public LocalDateTime getTimeLocal() {
        return timeLocal;
    }

    public void setTimeLocal(LocalDateTime timeLocal) {
        this.timeLocal = timeLocal;
    }

    public int getMinSinceStart() {
        return minSinceStart;
    }

    public void setMinSinceStart(int minSinceStart) {
        this.minSinceStart = minSinceStart;
    }

    public int getGlucose() {
        return glucose;
    }

    public void setGlucose(int glucose) {
        this.glucose = glucose;
    }

    public Trend getTrend() {
        return trend;
    }

    public PollsSensorReadingEntity setTrend(Trend trend) {
        this.trend = trend;
        return this;
    }

    public float getRateOfChange() {
        return rateOfChange;
    }

    public void setRateOfChange(float rateOfChange) {
        this.rateOfChange = rateOfChange;
    }
}

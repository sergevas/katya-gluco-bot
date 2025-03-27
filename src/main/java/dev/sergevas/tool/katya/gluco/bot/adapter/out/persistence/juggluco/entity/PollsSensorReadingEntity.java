package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "JG_POLLS")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "JG_POLLS_ID_SEQ", allocationSize = 1)
public class PollsSensorReadingEntity extends BaseEntity {

    private LocalDateTime timeLocal;
    private int minSinceStart;
    private int glucose;
    private Trend trend;
    private float rateOfChange;

    public PollsSensorReadingEntity() {
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

    public PollsSensorReadingEntity setGlucose(int glucose) {
        this.glucose = glucose;
        return this;
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

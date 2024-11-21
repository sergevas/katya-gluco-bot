package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.juggluco.entity;

import dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "JG_POLLS")
@SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_JG_POLLS", allocationSize = 1)
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

    public PollsSensorReadingEntity setTimeLocal(LocalDateTime timeLocal) {
        this.timeLocal = timeLocal;
        return this;
    }

    public int getMinSinceStart() {
        return minSinceStart;
    }

    public PollsSensorReadingEntity setMinSinceStart(int minSinceStart) {
        this.minSinceStart = minSinceStart;
        return this;
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

    public PollsSensorReadingEntity setRateOfChange(float rateOfChange) {
        this.rateOfChange = rateOfChange;
        return this;
    }
}


package dev.sergevas.tool.katya.gluco.bot.adapter.out.persistence;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (this == o) {
            return true;
        }
        if (getClass() != o.getClass()
                && !o.getClass().isAssignableFrom(getClass())
                && !getClass().isAssignableFrom(o.getClass())) {
            return false;
        }
        final BaseEntity entity = (BaseEntity) o;
        return this.id.equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return this.id != null ? this.id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("id=[%d] hashcode=[%d]", this.id, this.hashCode());
    }
}

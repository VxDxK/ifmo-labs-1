package com.vdk.lab3aaaaaaaa;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.annotations.Cache;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@ManagedBean(name = "hit")
@SessionScoped

@Entity(name = "s338999_hits")
@Table(name = "s338999_hits", schema = "public")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HitsIdGenerator")
    @SequenceGenerator(name="HitsIdGenerator", sequenceName="HIT_ID", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Min(-4)
    @Max(3)
    @Column
    private double x;

    @Min(-3)
    @Max(3)
    @Column
    private double y;

    @Column
    @Min(1)
    @Max(5)
    private double r;

    @Column
    private boolean hit;
    @Column
    @JsonSerialize(using = DateSerializer.class)
    private LocalDateTime time;
    @Column
    private long processingTime;

    public Hit(double x, double y, double r, boolean hit, LocalDateTime time, long processingTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.time = time;
        this.processingTime = processingTime;
    }
}

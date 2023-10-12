package de.atruvia.myweb.mywebapp.repositories.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name ="tbl_schweine")
public class SchweinEntity {
    @Id
    @Column(nullable = false, length = 36)
    private String id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false)
    private int gewicht;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SchweinEntity schwein = (SchweinEntity) o;
        return id != null && Objects.equals(id, schwein.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

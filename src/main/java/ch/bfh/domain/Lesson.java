package ch.bfh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ch.bfh.domain.util.CustomDateTimeDeserializer;
import ch.bfh.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Lesson.
 */
@Entity
@Table(name = "T_LESSON")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Lesson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "date", nullable = false)
    private DateTime date;

    @Column(name = "vistied")
    private Boolean vistied;

    @Column(name = "rating")
    private Boolean rating;

    @ManyToOne
    private Module module;

    @ManyToOne
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Boolean getVistied() {
        return vistied;
    }

    public void setVistied(Boolean vistied) {
        this.vistied = vistied;
    }

    public Boolean getRating() {
        return rating;
    }

    public void setRating(Boolean rating) {
        this.rating = rating;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Lesson lesson = (Lesson) o;

        if (id != null ? !id.equals(lesson.id) : lesson.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", date='" + date + "'" +
                ", vistied='" + vistied + "'" +
                ", rating='" + rating + "'" +
                '}';
    }
}

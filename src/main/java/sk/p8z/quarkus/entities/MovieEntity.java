package sk.p8z.quarkus.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies", schema = "public", catalog = "docker")
public class MovieEntity {
    @Id
    @Column(nullable = false)
    private Integer id;
    @Column
    private String title;
    @Column
    private Integer year;
    @Column
    private Integer length;
    @Column
    private String genres;
}

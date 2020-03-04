package sk.p8z.quarkus.entities;

import org.hibernate.annotations.Formula;
import sk.p8z.quarkus.model.Movie;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies", schema = "public", catalog = "docker")
public class MovieEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "year")
    private Integer year;
    @Column(name = "length")
    private Integer length;
    @Column(name = "genres")
    private String genres;
    @Formula("lower(title)")
    @JsonbTransient
    private String lowercaseTitle;

    public MovieEntity() {
    }

    public MovieEntity(Movie entity) {
        genres = entity.getGenres();
        id = entity.getId();
        length = entity.getLength();
        title = entity.getTitle();
        year = entity.getYear();
    }

    public static Movie toMovie(MovieEntity entity) {
        Movie movie = new Movie();
        movie.setGenres(entity.genres);
        movie.setId(entity.id);
        movie.setLength(entity.length);
        movie.setTitle(entity.title);
        movie.setYear(entity.year);
        return movie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getLowercaseTitle() {
        return lowercaseTitle;
    }
}

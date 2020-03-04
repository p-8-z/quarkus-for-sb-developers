package sk.p8z.quarkus.entities;

import org.hibernate.annotations.Formula;
import sk.p8z.quarkus.model.Movie;

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
    @Formula("lower(title)")
    private String lowercaseTitle;

    public MovieEntity() {
    }

    public MovieEntity(Movie movie) {
        id = movie.getId();
        title = movie.getTitle();
        year = movie.getYear();
        length = movie.getLength();
        genres = movie.getGenres();
    }

    public static Movie toMovie(MovieEntity entity) {
        Movie movie = new Movie();
        movie.setYear(entity.getYear());
        movie.setTitle(entity.getTitle());
        movie.setLength(entity.getLength());
        movie.setId(entity.getId());
        movie.setGenres(entity.getGenres());
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

package sk.p8z.quarkus.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.p8z.quarkus.api.MovieApi;
import sk.p8z.quarkus.entities.MovieEntity;
import sk.p8z.quarkus.model.Movie;
import sk.p8z.quarkus.repositories.MoviesRepositary;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MoviesController implements MovieApi {
    private MoviesRepositary moviesRepositary;

    public MoviesController(MoviesRepositary moviesRepositary) {
        this.moviesRepositary = moviesRepositary;
    }

    @Override
    public ResponseEntity<List<Movie>> moviesGet(@Min(0) @Valid Integer offset, @Min(1) @Max(50) @Valid Integer limit) {
        List<MovieEntity> entities = moviesRepositary.findAll(PageRequest.of(offset, limit)).getContent();
        List<Movie> movies = entities.stream().map(MovieEntity::toMovie).collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    @Override
    public ResponseEntity<Movie> moviesMovieIdGet(Integer id) {
        MovieEntity entity = moviesRepositary.findById(id).orElseThrow(NotFoundException::new);
        Movie movie = MovieEntity.toMovie(entity);
        return ResponseEntity.ok(movie);
    }

    @Override
    public ResponseEntity<Movie> moviesMoviePost(@Valid Movie movie) {
        MovieEntity entity = new MovieEntity(movie);
        entity = moviesRepositary.save(entity);
        return ResponseEntity.ok(MovieEntity.toMovie(entity));
    }

    @Override
    public ResponseEntity<List<Movie>> moviesTitleGet(String title) {
        List<MovieEntity> entities = moviesRepositary.findMovieEntitiesByLowercaseTitleContaining(title);
        List<Movie> movies = entities.stream().map(MovieEntity::toMovie).collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }
}

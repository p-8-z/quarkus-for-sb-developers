package sk.p8z.quarkus.controllers;


import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.p8z.quarkus.api.MovieApi;
import sk.p8z.quarkus.entities.MovieEntity;
import sk.p8z.quarkus.model.Movie;
import sk.p8z.quarkus.repositories.MoviesRepository;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class MoviesController implements MovieApi {

    private MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @Override
    public ResponseEntity<List<Movie>> moviesGet(
            @Min(0) @Valid Integer offset
            , @Min(1) @Max(50) @Valid Integer limit
    ) {
        List<MovieEntity> movieEntities = moviesRepository.findAll(PageRequest.of(offset, limit)).getContent();
        List<Movie> movies = movieEntities.stream().map(MovieEntity::toMovie).collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }

    @Override
    public ResponseEntity<Movie> moviesMovieIdGet(Integer id) {
        MovieEntity entity = moviesRepository.findById(id).orElse(new MovieEntity());
        return ResponseEntity.ok(MovieEntity.toMovie(entity));
    }

    @Override
    public ResponseEntity<Movie> moviesMoviePost(@Valid Movie movie) {
        MovieEntity entity = new MovieEntity(movie);
        entity = moviesRepository.save(entity);
        return ResponseEntity.ok(MovieEntity.toMovie(entity));
    }

    @Override
    public ResponseEntity<List<Movie>> moviesTitleGet(String title) {
        List<MovieEntity> movieEntities = moviesRepository.findMoviesByLowercaseTitleContaining(title.toLowerCase());
        List<Movie> movies = movieEntities.stream().map(MovieEntity::toMovie).collect(Collectors.toList());
        return ResponseEntity.ok(movies);
    }
}

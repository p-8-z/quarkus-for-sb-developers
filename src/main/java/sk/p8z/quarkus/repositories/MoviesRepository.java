package sk.p8z.quarkus.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import sk.p8z.quarkus.entities.MovieEntity;

import java.util.List;

public interface MoviesRepository extends PagingAndSortingRepository<MovieEntity, Integer> {
    List<MovieEntity> findMoviesByLowercaseTitleContaining(String title);
}

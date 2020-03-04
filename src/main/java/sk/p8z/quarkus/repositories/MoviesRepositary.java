package sk.p8z.quarkus.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import sk.p8z.quarkus.entities.MovieEntity;

public interface MoviesRepositary extends PagingAndSortingRepository<MovieEntity, Integer> {
}

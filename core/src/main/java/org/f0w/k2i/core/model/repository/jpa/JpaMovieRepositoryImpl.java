package org.f0w.k2i.core.model.repository.jpa;

import org.f0w.k2i.core.model.entity.Movie;
import org.f0w.k2i.core.model.repository.MovieRepository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class JpaMovieRepositoryImpl extends BaseJPARepository<Movie, Long> implements MovieRepository {
    /**
     * {@inheritDoc}
     */
    @Override
    public Movie findByTitleAndYear(final String title, final int year) {
        TypedQuery<Movie> query = entityManagerProvider.get()
                .createQuery(
                        "FROM Movie m WHERE m.title = :title AND m.year = :year",
                        Movie.class
                )
                .setParameter("title", title)
                .setParameter("year", year);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

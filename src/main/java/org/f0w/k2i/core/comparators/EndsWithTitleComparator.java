package org.f0w.k2i.core.comparators;

import org.f0w.k2i.core.entities.Movie;

class EndsWithTitleComparator implements EqualityComparator<Movie> {
    @Override
    public boolean areEqual(Movie movie1, Movie movie2) {
        String title1 = movie1.getTitle();
        String title2 = movie2.getTitle();

        return title1.endsWith(title2) || title2.endsWith(title1);
    }
}
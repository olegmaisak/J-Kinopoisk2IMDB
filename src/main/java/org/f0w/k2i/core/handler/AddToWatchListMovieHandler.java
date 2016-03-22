package org.f0w.k2i.core.handler;

import org.f0w.k2i.core.exchange.MovieWatchlistAssigner;

import com.google.inject.Inject;

import java.io.IOException;

class AddToWatchListMovieHandler extends AbstractMovieHandler {
    @Inject
    private MovieWatchlistAssigner assigner;

    @Override
    public boolean execute() {
        try {
            assigner.sendRequest(importProgress.getMovie());

            movieRepository.save(importProgress.getMovie());

            importProgress.setImported(true);

            importProgressRepository.save(importProgress);

            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
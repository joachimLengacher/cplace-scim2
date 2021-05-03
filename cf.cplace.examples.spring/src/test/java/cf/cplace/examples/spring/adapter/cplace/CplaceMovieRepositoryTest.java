package cf.cplace.examples.spring.adapter.cplace;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.DirectorRepository;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import cf.cplace.platform.services.exceptions.EntityNotFoundException;
import cf.cplace.platform.test.util.StartServerRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import javax.inject.Inject;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CplaceMovieRepositoryTest {

    @Rule
    public TestRule startServer = new StartServerRule(this);

    @Inject
    private MovieRepository movieRepository;

    @Inject
    private DirectorRepository directorRepository;

    @Test
    public void testCreateAndFind() {
        given_there_is_no_movie_named("Fitzcarraldo");
        given_there_is_no_movie_named("Cerro Torre: Scream of Stone");

        String movieId = when_a_movie_is_created("Fitzcarraldo");

        then_the_movie_should_be_found_by_name("Fitzcarraldo");
        then_the_movie_should_not_be_found_by_name("Cerro Torre: Scream of Stone");

        Movie movie = then_the_movie_should_be_found_by_Id(movieId);
        then_the_movie_should_not_be_found_by_Id("inexistent");

        then_the_movie_should_be_found_in_all_movies(movie);
    }

    @Test
    public void testUpdate() {
        Movie movie = given_there_is_a_movie_named("The White Diamond");
        Director director = given_there_is_a_director_named("Werner Herzog");
        when_the_director_is_added_to_the_movie(director, movie);
        then_the_director_should_be_set_on_the_movie(director.getId(), movie.getId());
    }

    private void given_there_is_no_movie_named(String name) {
        assertThat(movieRepository.findByName(name), empty());
    }

    private Movie given_there_is_a_movie_named(String name) {
        String id = movieRepository.create(name);
        return movieRepository.findById(id);
    }

    private Director given_there_is_a_director_named(String name) {
        String id = directorRepository.create(name, null);
        return directorRepository.findById(id);
    }

    private String when_a_movie_is_created(String name) {
        return movieRepository.create(name);
    }

    private void when_the_director_is_added_to_the_movie(Director director, Movie movie) {
        movie.setDirectorId(director.getId());
        movieRepository.save(movie);
    }

    private void then_the_movie_should_be_found_by_name(String name) {
        Collection<Movie> movies = movieRepository.findByName(name);
        assertThat(movies.size(), is(1));
        assertThat(movies.iterator().next().getName(), is(name));
    }

    private void then_the_movie_should_not_be_found_by_name(String name) {
        assertThat( movieRepository.findByName(name),empty());
    }

    private Movie then_the_movie_should_be_found_by_Id(String movieId) {
        Movie movie = movieRepository.findById(movieId);
        assertThat(movie, notNullValue());
        return movie;
    }

    private void then_the_movie_should_not_be_found_by_Id(String movieId) {
        Assert.assertThrows(EntityNotFoundException.class, () -> {
            movieRepository.findById(movieId);
        });
    }

    private void then_the_movie_should_be_found_in_all_movies(Movie movie) {
        assertThat(movieRepository.findAll(), contains(movie));
    }

    private void then_the_director_should_be_set_on_the_movie(String directorId, String movieId) {
        Movie movie = movieRepository.findById(movieId);
        assertThat(movie.getDirectorId(), is(directorId));
    }
}

package cf.cplace.examples.spring.usecase.impl;

import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieApplicationTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private Movie movie;

    private MovieApplication movieApplication;

    @Before
    public void setUp() {
        movieApplication = new MovieApplication(movieRepository);
    }

    @Test
    public void testCreate() {
        when(movieRepository.create("Fitzcarraldo")).thenReturn("70ugelde04214tqjrn3cduqus");
        assertThat(movieApplication.create("Fitzcarraldo"), is("70ugelde04214tqjrn3cduqus"));
    }

    @Test
    public void testFindById() {
        Movie movie = new Movie("70ugelde04214tqjrn3cduqus", "Fitzcarraldo", null);
        when(movieRepository.findById("70ugelde04214tqjrn3cduqus")).thenReturn(movie);
        assertThat(movieApplication.findById("70ugelde04214tqjrn3cduqus"), is(movie));
    }

    @Test
    public void testFindByName() {
        when(movieRepository.findByName("Fitzcarraldo")).thenReturn(Collections.singletonList(movie));
        assertThat(movieApplication.findByName("Fitzcarraldo"), contains(movie));
    }

    @Test
    public void testFindAll() {
        when(movieRepository.findAll()).thenReturn(Collections.singletonList(movie));
        assertThat(movieApplication.findAll(), contains(movie));
    }

    @Test
    public void testAddDirectorToMovie() {
        when(movieRepository.findById("70ugelde04214tqjrn3cduqus")).thenReturn(movie);
        movieApplication.addDirectorToMovie("83ukql2hqmrzuk8u5w3n89ik5", "70ugelde04214tqjrn3cduqus");
        verify(movie).setDirectorId("83ukql2hqmrzuk8u5w3n89ik5");
        verify(movieRepository).save(movie);
    }
}

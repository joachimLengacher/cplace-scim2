package cf.cplace.examples.spring.adapter.cplace;

import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.ForbiddenException;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import cf.cplace.examples.spring.domain.port.NotFoundException;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Search;
import cf.cplace.platform.services.exceptions.EntityNotFoundException;
import cf.cplace.platform.services.exceptions.ProtectedEntityException;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ParametersAreNonnullByDefault
public class CplaceMovieRepository implements MovieRepository {

    public CplaceMovieRepository() {
    }

    @Override
    public String create(String name) {
        try {
            PageSpace space = Preconditions.checkNotNull(PageSpace.SCHEMA.getEntity(PageSpace.ROOT_SPACE_ID));
            Page page = Page.SCHEMA.createPageAndPersist(space, ImdbAppTypes.MOVIE.TYPE, p -> p._name().set(name));
            return page.getId();
        } catch (ProtectedEntityException e) {
            throw new ForbiddenException();
        }
    }

    @Override
    public void save(Movie movie) {
        try {
            updateMovie(movie);
            updateMovieDirector(movie);
        } catch (ProtectedEntityException e) {
            throw new ForbiddenException();
        } catch (EntityNotFoundException e) {
            throw  new NotFoundException(movie.getId());
        }
    }

    @Override
    public Movie findById(String id) {
        try {
            Page moviePage = Page.SCHEMA.getEntityNotNull(id);
            return toMovie(moviePage);
        } catch (ProtectedEntityException e) {
            throw new ForbiddenException();
        } catch (EntityNotFoundException e) {
            throw  new NotFoundException(id);
        }
    }

    @Override
    public Collection<Movie> findAll() {
        try {
            PageSpace space = Preconditions.checkNotNull(PageSpace.SCHEMA.getEntity(PageSpace.ROOT_SPACE_ID));
            Search search = new Search();
            search.add(Filters.space(space));
            search.add(Filters.type(ImdbAppTypes.MOVIE.TYPE));

            return StreamSupport.stream(search.findAllPages().spliterator(), false)
                    .map(this::toMovie).collect(Collectors.toList());

        } catch (ProtectedEntityException e) {
            throw new ForbiddenException();
        }
    }

    @Override
    public Collection<Movie> findByName(String name) {
        try {
            PageSpace space = Preconditions.checkNotNull(PageSpace.SCHEMA.getEntity(PageSpace.ROOT_SPACE_ID));
            Search search = new Search()
                    .add(Filters.space(space))
                    .add(Filters.type(ImdbAppTypes.MOVIE.TYPE))
                    .add(Filters.builtinString("name", name));

            return StreamSupport.stream(search.findAllPages().spliterator(), false)
                    .map(this::toMovie).collect(Collectors.toList());

        } catch (ProtectedEntityException e) {
            throw new ForbiddenException();
        }
    }

    @Nonnull
    private Movie toMovie(Page moviePage) {
        Page director = moviePage.get(ImdbAppTypes.MOVIE.MOVIE_DIRECTOR);
        return new Movie(moviePage.getId(), moviePage.getNameNotEmpty(), director != null ? director.getId() : null);
    }


    private void updateMovie(Movie movie) {
        Page movieEntity = Page.SCHEMA.getWritableCopyNotNull(movie.getId());
        movieEntity._name().set(movie.getName());
        movieEntity.persist();
    }

    private void updateMovieDirector(Movie movie) {
        Page movieEntity = Page.SCHEMA.getEntityNotNull(movie.getId());
        if (movie.getDirectorId() != null) {
            Page directorEntity = Page.SCHEMA.getWritableCopyNotNull(movie.getId());
            movieEntity.set(ImdbAppTypes.MOVIE.MOVIE_DIRECTOR, directorEntity);
        } else {
            movieEntity.set(ImdbAppTypes.MOVIE.MOVIE_DIRECTOR, null);
        }
    }
}

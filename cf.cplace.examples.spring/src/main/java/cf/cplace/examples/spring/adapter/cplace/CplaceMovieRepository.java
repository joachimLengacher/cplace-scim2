package cf.cplace.examples.spring.adapter.cplace;

import cf.cplace.examples.spring.domain.InvalidReferenceException;
import cf.cplace.examples.spring.domain.model.Movie;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Search;
import cf.cplace.platform.orm.PersistentEntity;
import cf.cplace.platform.services.exceptions.EntityNotFoundException;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * A {@link MovieRepository} implementation that adapts {@link Movie} instances to cplace entities.
 */
@ParametersAreNonnullByDefault
public class CplaceMovieRepository implements MovieRepository {

    @Override
    @Nonnull
    public String create(String name) {
        PageSpace space = PageSpace.SCHEMA.getEntityNotNull(PageSpace.ROOT_SPACE_ID);
        Page page = Page.SCHEMA.createPageAndPersist(space, ImdbAppTypes.MOVIE.TYPE, p -> p._name().set(name));
        return page.getId();
    }

    @Override
    public void save(Movie movie) {
        Page movieEntity = Page.SCHEMA.getEntityNotNull(movie.getId());
        PersistentEntity.doOnWritableCopyAndPersistIfModified(movieEntity, m-> {
            m._name().set(movie.getName());
            if (movie.getDirectorId() != null) {
                try {
                    Page directorEntity = Page.SCHEMA.getEntityNotNull(movie.getDirectorId());
                    m.set(ImdbAppTypes.MOVIE.MOVIE_DIRECTOR, directorEntity);
                } catch (EntityNotFoundException e) {
                    throw new InvalidReferenceException(String.format("Unknown director: '%s'", movie.getDirectorId()));
                }
            } else {
                m.set(ImdbAppTypes.MOVIE.MOVIE_DIRECTOR, null);
            }
        });
    }

    @Override
    @Nonnull
    public Movie findById(String id) {
        Page moviePage = Page.SCHEMA.getEntityNotNull(id);
        return toMovie(moviePage);
    }

    @Override
    @Nonnull
    public Collection<Movie> findAll() {
        PageSpace space = PageSpace.SCHEMA.getEntityNotNull(PageSpace.ROOT_SPACE_ID);
        Search search = new Search();
        search.add(Filters.space(space));
        search.add(Filters.type(ImdbAppTypes.MOVIE.TYPE));

        return StreamSupport.stream(search.findAllPages().spliterator(), false)
                .map(this::toMovie).collect(Collectors.toList());
    }

    @Override
    @Nonnull
    public Collection<Movie> findByName(String name) {
        PageSpace space = PageSpace.SCHEMA.getEntityNotNull(PageSpace.ROOT_SPACE_ID);
        Search search = new Search()
                .add(Filters.space(space))
                .add(Filters.type(ImdbAppTypes.MOVIE.TYPE))
                .add(Filters.builtinString("name", name));

        return StreamSupport.stream(search.findAllPages().spliterator(), false)
                .map(this::toMovie).collect(Collectors.toList());
    }

    @Nonnull
    private Movie toMovie(Page moviePage) {
        Page director = moviePage.get(ImdbAppTypes.MOVIE.MOVIE_DIRECTOR);
        return new Movie(moviePage.getId(), moviePage.getNameNotEmpty(), director != null ? director.getId() : null);
    }
}

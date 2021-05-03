package cf.cplace.examples.spring.adapter.cplace;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.port.DirectorRepository;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Search;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * A {@link DirectorRepository} implementation that adapts {@link Director} instances to cplace entities.
 */
@ParametersAreNonnullByDefault
public class CplaceDirectorRepository implements DirectorRepository {

    @Override
    @Nonnull
    public String create(String name, @Nullable LocalDate birthday) {
        PageSpace space = PageSpace.SCHEMA.getEntityNotNull(PageSpace.ROOT_SPACE_ID);
        Page page = Page.SCHEMA.createPageAndPersist(space, ImdbAppTypes.DIRECTOR.TYPE, p -> {
            p._name().set(name);
            if (birthday != null) {
                p.set(ImdbAppTypes.DIRECTOR.BIRTHDAY, toDate(birthday));
            }
        });
        return page.getId();
    }

    @Override
    @Nonnull
    public Director findById(String id) {
        Page directorPage = Page.SCHEMA.getEntityNotNull(id);
        return toDirector(directorPage);
    }

    @Override
    @Nonnull
    public Collection<Director> findAll() {
        PageSpace space = PageSpace.SCHEMA.getEntityNotNull(PageSpace.ROOT_SPACE_ID);
        Search search = new Search();
        search.add(Filters.space(space));
        search.add(Filters.type(ImdbAppTypes.DIRECTOR.TYPE));

        return StreamSupport.stream(search.findAllPages().spliterator(), false)
                .map(this::toDirector).collect(Collectors.toList());
    }

    @Nonnull
    private Director toDirector(Page directorPage) {
        return new Director(directorPage.getId(), directorPage.getNameNotEmpty(),  toLocalDate(directorPage.get(ImdbAppTypes.DIRECTOR.BIRTHDAY)));
    }

    @Nullable
    private Date toDate(@Nullable LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    @Nullable
    private LocalDate toLocalDate(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}

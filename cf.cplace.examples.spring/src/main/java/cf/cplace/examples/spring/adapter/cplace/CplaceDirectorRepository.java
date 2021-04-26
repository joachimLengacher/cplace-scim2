package cf.cplace.examples.spring.adapter.cplace;

import cf.cplace.examples.spring.domain.model.Director;
import cf.cplace.examples.spring.domain.port.DirectorRepository;
import cf.cplace.examples.spring.domain.port.ForbiddenException;
import cf.cplace.examples.spring.domain.port.NotFoundException;
import cf.cplace.platform.assets.file.Page;
import cf.cplace.platform.assets.file.PageSpace;
import cf.cplace.platform.assets.search.Filters;
import cf.cplace.platform.assets.search.Search;
import cf.cplace.platform.services.exceptions.EntityNotFoundException;
import cf.cplace.platform.services.exceptions.ProtectedEntityException;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CplaceDirectorRepository implements DirectorRepository {

    @Override
    public String create(String name, LocalDate birthday) {
        try {
            PageSpace space = PageSpace.SCHEMA.getEntity(PageSpace.ROOT_SPACE_ID);
            Page page = Page.SCHEMA.createPageAndPersist(space, ImdbAppTypes.DIRECTOR.TYPE, p -> {
                p._name().set(name);
                if (birthday != null) {
                    p.set(ImdbAppTypes.DIRECTOR.birthday, toDate(birthday));
                }
            });
            return page.getId();
        } catch (ProtectedEntityException e) {
            throw new ForbiddenException();
        }
    }

    @Override
    public Director findById(String id) {
        try {
            Page directorPage = Page.SCHEMA.getEntityNotNull(id);
            return toDirector(directorPage);
        } catch (ProtectedEntityException e) {
            throw new ForbiddenException();
        } catch (EntityNotFoundException e) {
            throw  new NotFoundException(id);
        }
    }

    @Override
    public Collection<Director> findAll() {
        try {
            PageSpace space = PageSpace.SCHEMA.getEntity(PageSpace.ROOT_SPACE_ID);
            Search search = new Search();
            search.add(Filters.space(space));
            search.add(Filters.type(ImdbAppTypes.DIRECTOR.TYPE));

            return StreamSupport.stream(search.findAllPages().spliterator(), false)
                    .map(this::toDirector).collect(Collectors.toList());

        } catch (ProtectedEntityException e) {
            throw new ForbiddenException();
        }
    }

    private Director toDirector(Page directorPage) {
        return new Director(directorPage.getId(), directorPage.getNameNotEmpty(),  toLocalDate(directorPage.get(ImdbAppTypes.DIRECTOR.birthday)));
    }

    private Date toDate(@Nullable LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private LocalDate toLocalDate(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}

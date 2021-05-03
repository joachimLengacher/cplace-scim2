package cf.cplace.examples.spring.domain.model;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Movie {
    private final String id;
    private final String name;
    private String directorId;

    public Movie(String id, String name, @Nullable String directorId) {
        this.id = Preconditions.checkNotNull(id);
        this.name = Preconditions.checkNotNull(name);
        this.directorId = directorId;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nullable
    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(@Nullable String directorId) {
        this.directorId = directorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!getId().equals(movie.getId())) return false;
        if (!getName().equals(movie.getName())) return false;
        return getDirectorId() != null ? getDirectorId().equals(movie.getDirectorId()) : movie.getDirectorId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getDirectorId() != null ? getDirectorId().hashCode() : 0);
        return result;
    }
}

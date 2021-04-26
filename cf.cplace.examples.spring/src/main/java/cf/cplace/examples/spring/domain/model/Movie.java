package cf.cplace.examples.spring.domain.model;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public class Movie {
    private final String id;
    private final String name;
    private String directorId;

    public Movie(String id, String name, @Nullable String directorId) {
        this.id = Preconditions.checkNotNull(id);
        this.name = Preconditions.checkNotNull(name);
        this.directorId = directorId;
    }

    public String getId() {
        return id;
    }

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
}

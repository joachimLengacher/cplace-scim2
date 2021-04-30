package cf.cplace.examples.spring.domain.model;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;

@ParametersAreNonnullByDefault
public class Director {
    private final String id;
    private String name;
    private LocalDate birthday;

    public Director(String id, String name, @Nullable LocalDate birthday) {
        this.id = Preconditions.checkNotNull(id);
        this.name =  Preconditions.checkNotNull(name);
        this.birthday =  birthday;
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = Preconditions.checkNotNull(name);
    }

    @Nullable
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Director director = (Director) o;

        if (!getId().equals(director.getId())) return false;
        if (!getName().equals(director.getName())) return false;
        return getBirthday() != null ? getBirthday().equals(director.getBirthday()) : director.getBirthday() == null;
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getBirthday() != null ? getBirthday().hashCode() : 0);
        return result;
    }
}

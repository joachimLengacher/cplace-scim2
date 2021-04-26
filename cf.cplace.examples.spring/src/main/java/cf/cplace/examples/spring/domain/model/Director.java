package cf.cplace.examples.spring.domain.model;

import com.google.common.base.Preconditions;
import edu.umd.cs.findbugs.annotations.ReturnValuesAreNonnullByDefault;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.LocalDate;

@ParametersAreNonnullByDefault
@ReturnValuesAreNonnullByDefault
public class Director {
    private final String id;
    private String name;
    public LocalDate birthday;

    public Director(String id, String name, @Nullable LocalDate birthday) {
        this.id = Preconditions.checkNotNull(id);
        this.name =  Preconditions.checkNotNull(name);
        this.birthday =  birthday;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}

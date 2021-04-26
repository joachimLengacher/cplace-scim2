package cf.cplace.examples.spring.adapter.rest;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public final class CreateDirectorRequest {

    private String name;
    private LocalDate birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonFormat(pattern="yyyy-MM-dd")
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}

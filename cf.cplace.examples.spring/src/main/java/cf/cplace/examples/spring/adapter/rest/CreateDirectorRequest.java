package cf.cplace.examples.spring.adapter.rest;

import java.time.LocalDate;

public class CreateDirectorRequest {

    private String name;
    private LocalDate birthday; // TODO: parse format

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}

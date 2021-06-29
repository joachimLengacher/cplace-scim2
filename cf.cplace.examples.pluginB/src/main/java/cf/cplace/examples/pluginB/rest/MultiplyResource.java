package cf.cplace.examples.pluginB.rest;

import cf.cplace.examples.pluginA.api.Multiplier;
import cf.cplace.platform.api.spring.annotation.Exported;
import cf.cplace.platform.api.web.annotation.CplaceRequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Exported
@RestController
@CplaceRequestMapping(path = "/cf.cplace.examples.pluginB/multiply")
public class MultiplyResource {

    private final Multiplier multiplier;

    public MultiplyResource(Multiplier multiplier) {
        this.multiplier = multiplier;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Long multiply(@RequestParam Integer a, @RequestParam Integer b) {
        return multiplier.multiply(a, b);
    }
}

package cf.cplace.examples.spring.assembly;

import cf.cplace.examples.spring.adapter.cplace.CplaceDirectorRepository;
import cf.cplace.examples.spring.adapter.cplace.CplaceMovieRepository;
import cf.cplace.examples.spring.adapter.rest.DirectorResource;
import cf.cplace.examples.spring.adapter.rest.GlobalExceptionHandler;
import cf.cplace.examples.spring.adapter.rest.MovieResource;
import cf.cplace.examples.spring.domain.port.DirectorRepository;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import cf.cplace.examples.spring.usecase.impl.DirectorApplication;
import cf.cplace.examples.spring.usecase.impl.MovieApplication;
import cf.cplace.platform.api.spring.Exported;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This plugin's central Spring configuration that is picked up by cplace at start-up.
 */
@Configuration
public class PluginSpringConfiguration {

    @Bean
    public MovieRepository movieRepository() {
        return new CplaceMovieRepository();
    }

    @Bean
    public DirectorRepository directorRepository() {
        return new CplaceDirectorRepository();
    }

    @Bean
    public MovieApplication movieApplication(MovieRepository movieRepository) {
        return new MovieApplication(movieRepository);
    }

    @Bean
    public DirectorApplication directorApplication(DirectorRepository directorRepository) {
        return new DirectorApplication(directorRepository);
    }

    @Exported
    @Bean("cf.cplace.examples.spring.movieResource")
    public MovieResource movieResource(MovieApplication movieApplication) {
        return new MovieResource(movieApplication, movieApplication, movieApplication);
    }

    @Exported
    @Bean("cf.cplace.examples.spring.directorResource")
    public DirectorResource directorResource(DirectorApplication directorApplication) {
        return new DirectorResource(directorApplication, directorApplication);
    }

    @Exported
    @Bean("cf.cplace.examples.spring.globalExceptionHandler")
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}

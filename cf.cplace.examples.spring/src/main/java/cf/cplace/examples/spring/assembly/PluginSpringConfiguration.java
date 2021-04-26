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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public MovieApplication movieApplication(MovieRepository movieRepository, DirectorRepository directorRepository) {
        return new MovieApplication(movieRepository, directorRepository);
    }

    @Bean
    public DirectorApplication directorApplication(DirectorRepository directorRepository) {
        return new DirectorApplication(directorRepository);
    }

    @Bean
    public MovieResource movieResource(MovieApplication movieApplication) {
        return new MovieResource(movieApplication, movieApplication, movieApplication);
    }

    @Bean
    public DirectorResource directorResource(DirectorApplication directorApplication) {
        return new DirectorResource(directorApplication, directorApplication);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}

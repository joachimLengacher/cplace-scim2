package cf.cplace.examples.spring.assembly;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cf.cplace.examples.spring.adapter.cplace.CplaceMovieRepository;
import cf.cplace.examples.spring.adapter.rest.GlobalExceptionHandler;
import cf.cplace.examples.spring.adapter.rest.MovieResource;
import cf.cplace.examples.spring.domain.port.MovieRepository;
import cf.cplace.examples.spring.usecase.impl.MovieApplication;

@Configuration
public class PluginSpringConfiguration {

    @Bean
    public MovieRepository movieRepository() {
        return new CplaceMovieRepository();
    }

    @Bean
    public MovieApplication movieApplication(MovieRepository movieRepository) {
        return new MovieApplication(movieRepository);
    }

    @Bean
    public MovieResource movieResource(MovieApplication movieApplication) {
        return new MovieResource(movieApplication, movieApplication);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}

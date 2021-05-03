package cf.cplace.examples.spring;

import cf.cplace.platform.archunit.ArchUnitRunner;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "cf.cplace.examples.spring")
public class LayeredArchitectureTests {

    @ArchTest
    public static final ArchRule platformUsageRules = layeredArchitecture()
            .layer("Infrastructure").definedBy("..cf.cplace.examples.spring.assembly..")
            .layer("Adapters").definedBy("..cf.cplace.examples.spring.adapter..")
            .layer("Application Logic").definedBy("..cf.cplace.examples.spring.usecase..")
            .layer("Domain").definedBy("..cf.cplace.examples.spring.domain..")
            .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
            .whereLayer("Adapters").mayOnlyBeAccessedByLayers("Infrastructure")
            .whereLayer("Application Logic").mayOnlyBeAccessedByLayers("Infrastructure", "Adapters")
            .whereLayer("Domain").mayOnlyBeAccessedByLayers("Infrastructure", "Application Logic", "Adapters");

}

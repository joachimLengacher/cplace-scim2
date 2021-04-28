package cf.cplace.examples.spring;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayeredArchitectureTests {

    @Test
    public void layer_dependencies_are_respected() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("cf.cplace.examples.spring");
        ArchRule myRule = layeredArchitecture()
                .layer("Infrastructure").definedBy("..cf.cplace.examples.spring.assembly..")
                .layer("Adapters").definedBy("..cf.cplace.examples.spring.adapter..")
                .layer("Application Logic").definedBy("..cf.cplace.examples.spring.usecase..")
                .layer("Domain").definedBy("..cf.cplace.examples.spring.domain..")
                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
                .whereLayer("Adapters").mayOnlyBeAccessedByLayers("Infrastructure")
                .whereLayer("Application Logic").mayOnlyBeAccessedByLayers("Infrastructure", "Adapters")
                .whereLayer("Domain").mayOnlyBeAccessedByLayers("Infrastructure", "Application Logic", "Adapters");
        myRule.check(importedClasses);
    }
}

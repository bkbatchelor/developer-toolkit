package com.developer.toolkit.nosql.idgenerator;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;

@AnalyzeClasses(packages = "com.developer.toolkit", importOptions = ImportOption.DoNotIncludeTests.class)
public class SecurityArchUnitTest {

    @ArchTest
    static final ArchRule repository_methods_should_have_hardened_signatures =
            methods().that().areDeclaredInClassesThat().resideInAPackage("..repository..")
                    .should().notHaveRawReturnType(Object.class)
                    .as("Repository methods should have specific return types (not Object) for type safety and security.");

    @ArchTest
    static final ArchRule controller_methods_should_not_expose_raw_id_in_path_variables =
            noMethods().that().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
                    .should(new com.tngtech.archunit.lang.ArchCondition<JavaMethod>("expose raw String ID in PathVariable") {
                        @Override
                        public void check(JavaMethod method, com.tngtech.archunit.lang.ConditionEvents events) {
                            boolean hasInsecurePathVariable = method.getParameters().stream()
                                    .anyMatch(p -> p.isAnnotatedWith(PathVariable.class) && p.getRawType().getName().equals("java.lang.String"));
                            
                            if (hasInsecurePathVariable) {
                                events.add(com.tngtech.archunit.lang.SimpleConditionEvent.violated(method, 
                                        String.format("Method %s has a String PathVariable which could be a raw DB ID", method.getFullName())));
                            }
                        }
                    })
                    .as("Controllers should avoid using raw String IDs in PathVariables to prevent IDOR; use specialized types or surrogate keys like SKU.");

    @ArchTest
    static final ArchRule dtos_should_not_expose_raw_id_field =
            classes().that().resideInAPackage("..dto..")
                    .should(new com.tngtech.archunit.lang.ArchCondition<JavaClass>("not have field named 'id'") {
                        @Override
                        public void check(JavaClass item, com.tngtech.archunit.lang.ConditionEvents events) {
                            item.getFields().stream()
                                    .filter(field -> field.getName().equalsIgnoreCase("id"))
                                    .forEach(field -> events.add(com.tngtech.archunit.lang.SimpleConditionEvent.violated(item, 
                                            String.format("Class %s has field named '%s'", item.getName(), field.getName()))));
                        }
                    })
                    .as("DTOs should not expose a field named 'id' to prevent raw database ID leakage; use 'productId' or 'sku' instead.");
}

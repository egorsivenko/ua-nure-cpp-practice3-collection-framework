package ua.nure.cpp.name.practice3.list;

import static org.junit.jupiter.api.Assertions.*;
import static ua.nure.cpp.name.practice3.list.Util.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;

class ComplianceTest {
    private static final Logger LOG = LoggerFactory.getLogger(ComplianceTest.class);
    private static final String JAVA_UTIL = "java.util";

    static String basePackage;

    @BeforeAll
    static void init() {
        basePackage = getBasePackage();
    }

    @Test
    void testComplianceTopLevelClass() {
        Map<CtType<?>, CtTypeReference<?>> entities = getAllEntities();
        Set<Map.Entry<CtType<?>, CtTypeReference<?>>> topClasses = entities.entrySet().stream().filter(e -> e.getValue() == null)
                .collect(Collectors.toSet());
        assertEquals(1, topClasses.size(),
                "Only one class can be top of the hierarchy");
    }

    @Test
    void testCompliancePackageNaming() {
        LOG.debug("basePackage: {}", basePackage);
        Matcher m = Pattern.compile("ua\\.nure\\.cpp\\.[a-z&&\\D]+?\\.practice3").matcher(basePackage);
        assertTrue(m.matches(),
                "Base package must be 'ua.nure.cpp..practice3'.\n" +
                        "Where <your_last_name> is your last name in lower case without digits.");
    }

    // java.util is prohibited
    @Test
    void testComplianceJavaUtilIsForbidden() {
        CtModel ctModel = Util.getCtModel();
        List<String> forbiddenClasses = ctModel
                .filterChildren((Filter<CtTypeReference<?>>) el ->
                        el.getQualifiedName().startsWith(JAVA_UTIL))
                .list()
                .stream()
                .map(el -> ((CtTypeReference<?>) el).getQualifiedName())
                .distinct()
                .filter(el -> el.startsWith(JAVA_UTIL) &&
                        !(el.endsWith("Objects") ||
                                el.endsWith("Iterator") ||
                                el.endsWith("NoSuchElementException"))
                ).toList();
        LOG.debug("forbiddenClasses: {}", forbiddenClasses);
        assertTrue(forbiddenClasses.isEmpty(), "'java.util' package is forbidden " +
                "except 'java.util.Objects', 'java.util.NoSuchElementException'. " +
                "Your forbidden classes: " + forbiddenClasses);
    }

    // must not aggregate an array
    @Test
    void testComplianceMustNotAggregateArrays() {
        CtModel ctModel = Util.getCtModel();
        CtTypeReference<?> impl = ctModel.filterChildren((Filter<CtTypeReference<?>>) el ->
                el.getQualifiedName().equals(getListImplementorFqn())).first();
        Collection<CtFieldReference<?>> declaredFields = impl.getDeclaredFields();
        List<CtFieldReference<?>> arrayFields = declaredFields.stream()
                .filter(f -> f.getType().getQualifiedName().endsWith("[]"))
                .toList();
        LOG.debug("arrayFields: {}", arrayFields);
        assertTrue(arrayFields.isEmpty(), "You must not aggregate arrays in your list implementor.\n" +
                "arrayFields: " + arrayFields);
    }
}

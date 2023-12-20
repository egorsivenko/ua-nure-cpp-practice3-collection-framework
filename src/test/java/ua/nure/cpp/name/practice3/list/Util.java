package ua.nure.cpp.name.practice3.list;

import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.*;
import spoon.reflect.path.CtRole;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.chain.CtQuery;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class Util {
    static final Logger LOG = LoggerFactory.getLogger(Util.class);
    static final String ENTITY_PACKAGE = "entity";
    static final String LIST_PACKAGE = "list";
    static final Object CONTAINER_CLASS_NAME = "Container";
    private static CtModel ctModel;
    private static String containerFqn;
    private static String listFqn;
    private static String implFqn;
//    private static Map<CtType<?>, CtTypeReference<?>> entities;
    private static String basePackage;
    private static Set<String> nonAbstractEntities;
    private static String topEntityFqn;

    static {
        init();
    }

    static void init() {
        ctModel = getCtModel();
        basePackage = getBasePackage();
        LOG.info("Detected basePackage: {}", basePackage);
        containerFqn = basePackage + "." + LIST_PACKAGE + "." + CONTAINER_CLASS_NAME;
        LOG.info("Detected containerFqn: {}", containerFqn);
        listFqn = getFqnByInterface(containerFqn);
        LOG.info("Detected listFqn: {}", listFqn);
        implFqn = getFqnByInterface(listFqn);
        LOG.info("Detected implFqn: {}", implFqn);
        nonAbstractEntities = getAllNonAbstractEntities();
        LOG.info("Detected nonAbstractEntitiesFqn: {}", nonAbstractEntities);
        topEntityFqn = getTopEntityFqn();
        LOG.info("Detected topEntityFqn: {}", topEntityFqn);
    }

    private Util() {
    }

    static synchronized CtModel getCtModel() {
        if (ctModel == null) {
            SpoonAPI spoon = new Launcher();
            spoon.addInputResource("src/main/java/");
            ctModel = spoon.buildModel();
        }
        return ctModel;
    }

    static String getBasePackage() {
        if (basePackage == null) {
            Optional<String> any = ctModel.getAllPackages().stream()
                    .map(CtPackage::getQualifiedName)
                    .filter(pfqn -> pfqn.endsWith(ENTITY_PACKAGE))
                    .findAny();
            assertTrue(any.isPresent());
            String s = any.get();
            basePackage = s.substring(0, s.length() - ENTITY_PACKAGE.length() - 1);
        }
        return basePackage;
    }

    static String getFqnByInterface(String fqn) {
        CtQuery ctQuery = ctModel.filterChildren(
                (Filter<CtType<?>>) element -> !element.getSuperInterfaces()
                        .stream()
                        .filter(i -> i.getQualifiedName().equals(fqn))
                        .toList()
                        .isEmpty());
        List<CtType<?>> list = ctQuery.list();
        Assertions.assertEquals(1, list.size(), "The implementor must be only one");
        return list.get(0).getQualifiedName();
    }

    static Map<CtType<?>, CtTypeReference<?>> getAllEntities() {
        Set<CtType<?>> entities = ctModel.getAllTypes().stream()
                .filter(t -> t.getPackage().getQualifiedName().endsWith("entity"))
                .collect(Collectors.toSet());
        // Entities must be placed in entity package
        assertFalse(entities.isEmpty());

        HashMap<CtType<?>, CtTypeReference<?>> hierarchy = new HashMap<>();
        entities.stream().filter(Objects::nonNull).forEach(e -> hierarchy.put(e, e.getSuperclass()));
        // Entities must be hierarchically organized
        assertFalse(hierarchy.isEmpty());
        return hierarchy;
    }

    static String getListImplementorFqn() {
        return implFqn;
    }

    static CtType<?> getContainerCtType() {
        Optional<CtType<?>> containerOpt = ctModel.getElements(
                (Filter<CtType<?>>) element -> element.getQualifiedName().equals(containerFqn)
        ).stream().findFirst();
        assertTrue(containerOpt.isPresent());
        return containerOpt.get();
    }

    static String getTopEntityFqn() {
        if (topEntityFqn == null) {
            CtType<?> containerCtType = getContainerCtType();
            Set<CtTypeReference<?>> superInterfacesCtRefs = containerCtType.getSuperInterfaces();
            LOG.debug("all superInterfaces: {}", superInterfacesCtRefs);

            Optional<CtTypeReference<?>> superInterfaceOpt = superInterfacesCtRefs.stream()
                    .filter(r -> r.getQualifiedName().equals("java.lang.Iterable"))
                    .findFirst();
            assertTrue(superInterfaceOpt.isPresent(), 
            		"Your list interface must extends 'java.lang.Iterable'");
            CtTypeReference<?> superInterfaceCtRef = superInterfaceOpt.get();
            LOG.debug("superInterfaceCtRef found: {}", superInterfaceCtRef);

            Matcher m = Pattern.compile(".+?[<](.+)[>]").matcher(superInterfaceCtRef.toString());
            assertTrue(m.find(), 
            		"Iterable must be parametrized by your top level entity class <YourDomainClass>");
            String iterableTypeParameterFqn = m.group(1);
            LOG.debug("type parameter of Iterable: {}", iterableTypeParameterFqn);
            topEntityFqn = iterableTypeParameterFqn;
        }
        return topEntityFqn;
    }

    static Set<String> getAllNonAbstractEntities() {
        if (nonAbstractEntities == null) {
            Collection<CtType<?>> elements = ctModel.getElements(
                    element -> element.getModifiers().stream()
                            .filter(m -> m.equals(ModifierKind.ABSTRACT))
                            .toList()
                            .isEmpty() &&
                            element.getQualifiedName().startsWith(basePackage + "." + ENTITY_PACKAGE)  &&
                            !element.getRoleInParent().equals(CtRole.TYPE_MEMBER)
            );
            nonAbstractEntities = elements.stream()
//                    .filter(t -> t.getRoleInParent().getSuperRole() != CtRole.NESTED_TYPE)
                    .map(CtTypeInformation::getQualifiedName)
                    .collect(Collectors.toSet());
            // Hierarchy must have more then 1 class
            assertTrue(1 < nonAbstractEntities.size(), 
            		"Hierarchy must have more then 1 class");
        }
        return nonAbstractEntities;
    }

	static Object[] initEntities() {
		List<Object> list = new ArrayList<>(Util.getAllNonAbstractEntities()
				.stream()
				.map(Util::newObject)
				.toList());
		list.add(null);
		return list.toArray();
	}

    static Class<?> getClazz(String fqn) {
        try {
            return Class.forName(fqn);
        } catch (ClassNotFoundException e) {
            fail(fqn, e);
        }
        return null;
    }

    static Object newObject(String fqn) {
        try {
            Class<?> aClass = Class.forName(fqn);
            Constructor<?> constructor = aClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            fail(fqn, e);
        }
        return null;
    }

    static Object call(Object source, String method) {
        return invoke(source, source.getClass(), method, null, null);
    }

    static Object call(Object source, String method, Object... params) {
        return invoke(source, source.getClass(), method,
                new Class<?>[]{getClazz(getTopEntityFqn())}, params);
    }

    static Object invoke(Object obj, Class<?> aClass, String methodName, Class<?>[] paramsClasses, Object[] params) {
        try {
            Method method = aClass.getDeclaredMethod(methodName, paramsClasses);
            return method.invoke(obj, params);
        } catch (ReflectiveOperationException e) {
            fail(e);
        }
        return null;
    }
}

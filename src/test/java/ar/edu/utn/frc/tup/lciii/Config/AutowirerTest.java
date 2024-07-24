package ar.edu.utn.frc.tup.lciii.Config;

import ar.edu.utn.frc.tup.lciii.Config.Anotations.Autowire;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutowirerTest {




    static class TestClass {
        @Autowire
        private Dependency dependency;

        public Dependency getDependency() {
            return dependency;
        }
    }

    static class Dependency {
        public String greet() {
            return "Hello, World!";
        }
    }
    @Test
    void testInjectDependencies() {
        TestClass testObject = new TestClass();
        Autowirer.injectDependencies(testObject);
        assertNotNull(testObject.getDependency());
        assertEquals("Hello, World!", testObject.getDependency().greet());
    }
//--------------------------------------------------------------------






    static class AlreadyInitializedTestClass {
        @Autowire
        private Dependency dependency = new Dependency();

        public Dependency getDependency() {
            return dependency;
        }
    }
    @Test
    void testAlreadyInitializedField() {
        AlreadyInitializedTestClass testObject = new AlreadyInitializedTestClass();
        Dependency existingDependency = testObject.getDependency();
        Autowirer.injectDependencies(testObject);
        assertNotNull(testObject.getDependency());
        assertSame(existingDependency, testObject.getDependency());
    }
//----------------------------------------------------------------------




    static class InvalidDependency {
        private InvalidDependency() {

        }
    }
    static class InvalidDependencyTestClass {
        @Autowire
        private InvalidDependency dependency;

        public InvalidDependency getDependency() {
            return dependency;
        }
    }
    @Test
    void testInvalidDependency() {
        InvalidDependencyTestClass testObject = new InvalidDependencyTestClass();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            Autowirer.injectDependencies(testObject);
        });
        assertTrue(exception.getMessage().contains("Unable to create instance of class"));
    }
}

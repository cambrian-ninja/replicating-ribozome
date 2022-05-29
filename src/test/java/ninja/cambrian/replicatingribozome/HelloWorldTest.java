package ninja.cambrian.replicatingribozome;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class HelloWorldTest {

    @Test
    public void testApplyTemplate() {
        HelloWorld helloWorld = new HelloWorld();
        assertEquals(helloWorld.applyTemplate("world"),"Hello, world");
    }
}

package ninja.cambrian.replicatingribozome;

import org.stringtemplate.v4.ST;

public class HelloWorld {

    public static void main(String[] args) {

        new HelloWorld().sayHello();
    }

    public void sayHello() {
        String output = applyTemplate("world");
        System.out.println(output);
    }

    public String applyTemplate(String name) {
        ST hello = new ST("Hello, <name>");
        hello.add("name", name);
        return hello.render();
    }
}

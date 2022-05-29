package ninja.cambrian.replicatingribozome;

import org.stringtemplate.v4.ST;

public class HelloWorld {

    public static void main(String[] args) {
        ST hello = new ST("Hello, <name>");
        hello.add("name", "world");
        System.out.println(hello.render());
    }
}

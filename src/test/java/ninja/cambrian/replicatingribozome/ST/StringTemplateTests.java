package ninja.cambrian.replicatingribozome.ST;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.testng.annotations.Test;

import java.net.URL;

import static org.testng.Assert.assertEquals;

public class StringTemplateTests {

    @Test
    public void runHelloWorldTest() {
        ST hello = new ST("Hello, <name>");
        hello.add("name","world");
        assertEquals(hello.render(),"Hello, world");
    }

    @Test
    public void runGroupDirTest() {
        URL directoryURL = this.getClass().getResource("test");
        STGroup group = new STGroupDir(directoryURL);
        ST st = group.getInstanceOf("/decl");
        st.add("type","int");
        st.add("name","x");
        st.add("value",0);

        assertEquals(st.render(),"int x = 0;");
    }

    @Test
    public void runGroupFileTest() {
        URL fileURL = this.getClass().getResource("test/test.stg");
        STGroup group = new STGroupFile(fileURL);
        ST st = group.getInstanceOf("decl");
        st.add("type", "int");
        st.add("name", "x");
        st.add("value", 0);
        assertEquals(st.render(),"int x = 0;");
    }

    @Test
    public void runModelTest() {
        ST st = new ST("<b>$u.id$</b>: $u.name$", '$', '$');
        st.add("u", new User(999, "parrt"));
        assertEquals(st.render(),"<b>999</b>: parrt");
    }

    @Test
    public void runDataAggregateTest() {
        ST st = new ST("<items:{it|<it.id>: <it.lastName>, <it.firstName>\n}>");
        st.addAggr("items.{ firstName ,lastName, id }", "Ter", "Parr", 99); // add() uses varargs
        st.addAggr("items.{firstName, lastName ,id}", "Tom", "Burns", 34);

        assertEquals(st.render(),"99: Parr, Ter\n34: Burns, Tom\n");

    }


    public static class User {
        public int id; // template can directly access via u.id
        private String name; // template can't access this
        public User(int id, String name) { this.id = id; this.name = name; }
        public boolean isManager() { return true; } // u.manager
        public boolean hasParkingSpot() { return true; } // u.parkingSpot
        public String getName() { return name; } // u.name
        public String toString() { return id+":"+name; } // u
    }

}



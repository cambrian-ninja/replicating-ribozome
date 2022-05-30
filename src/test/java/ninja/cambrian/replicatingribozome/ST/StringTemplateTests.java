package ninja.cambrian.replicatingribozome.ST;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupDir;
import org.stringtemplate.v4.STGroupFile;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.Objects;

import static org.testng.Assert.assertEquals;

public class StringTemplateTests {

    @Test
    public void runHelloWorldTest() {
        ST hello = new ST("Hello, <name>");
        hello.add("name", "world");
        assertEquals(hello.render(), "Hello, world");
    }

    @Test
    public void runGroupDirTest() {
        URL directoryURL = this.getClass().getResource("test");
        STGroup group = new STGroupDir(Objects.requireNonNull(directoryURL));
        ST st = group.getInstanceOf("/decl");
        st.add("type", "int");
        st.add("name", "x");
        st.add("value", 0);

        assertEquals(st.render(), "int x = 0;");
    }

    @Test
    public void runGroupFileTest() {
        URL fileURL = this.getClass().getResource("test/test.stg");
        STGroup group = new STGroupFile(Objects.requireNonNull(fileURL));
        ST st = group.getInstanceOf("decl");
        st.add("type", "int");
        st.add("name", "x");
        st.add("value", 0);
        assertEquals(st.render(), "int x = 0;");
    }

    @Test
    public void runModelTest() {
        ST st = new ST("<b>$u.id$</b>: $u.name$", '$', '$');
        User u = new User(999, "userName");
        st.add("u", u);
        assertEquals(u.getName(),"userName");
        assertEquals(st.render(), "<b>999</b>: userName");
    }

    @Test
    public void runDataAggregateTest() {
        ST st = new ST("<items:{it|<it.id>: <it.lastName>, <it.firstName>\n}>");
        st.addAggr("items.{ firstName ,lastName, id }", "Ter", "Parr", 99); // add() uses varargs
        st.addAggr("items.{firstName, lastName ,id}", "Tom", "Burns", 34);

        assertEquals(st.render(), "99: Parr, Ter\n34: Burns, Tom\n");

    }

    @Test
    public void runRecordTest() {
        ST st = new ST("<b>$u.id$</b>: $u.name$", '$', '$');
        UserRec u = new UserRec(998L,"userName");
        st.add("u",u);
        assertEquals(u.name,"userName");
        assertEquals(st.render(), "<b>998</b>: userName");
    }


    public static class User {
        public int id; // template can directly access via u.id
        private final String name; // template can't access this

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        } // u.name

        public String toString() {
            return id + ":" + name;
        } // u
    }

    public record UserRec(Long id, String name) {

        //legacy getters - getFieldName() are required since ST doesn't know about records yet.
        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}



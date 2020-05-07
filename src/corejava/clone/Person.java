package corejava.clone;

import java.io.*;

public class Person implements Cloneable, Serializable {

    private String name;
    private int age;
    private PersonId personId;

    public Person(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.personId = new PersonId(id);
    }

    public void setId(int id) {
        personId.setId(id);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person clone() throws CloneNotSupportedException {

        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try(ObjectOutputStream out = new ObjectOutputStream(bout)) {
                out.writeObject(this);
            }

            try(InputStream bin = new ByteArrayInputStream(bout.toByteArray())){
                ObjectInputStream in = new ObjectInputStream(bin);
                return (Person)in.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            CloneNotSupportedException e2 = new CloneNotSupportedException();
            e2.initCause(e);
            throw e2;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", personId=" + personId +
                '}';
    }
}

class PersonId implements Cloneable,Serializable{
    private int id;

    public PersonId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PersonId{" +
                "id=" + id +
                '}';
    }

    public PersonId clone() throws CloneNotSupportedException {
        return (PersonId)super.clone();
    }
}
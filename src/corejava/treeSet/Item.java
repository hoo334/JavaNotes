package corejava.treeSet;

import java.util.Objects;

public class Item implements Comparable<Item>{
private String description;
private int partNumber;

    public Item(String description, int partNumber) {
        this.description = description;
        this.partNumber = partNumber;
    }

    public String getDescription() {
        return description;
    }

    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null)return false;
        if(getClass() != o.getClass())return false;
        Item otherItem = (Item) o;
        return Objects.equals(description,otherItem.description) && partNumber == otherItem.partNumber;
    }

    public int hashCode(){
    return Objects.hash(partNumber,description);
    }

    @Override
    public int compareTo(Item o) {
        int diff = Integer.compare(partNumber,o.partNumber);
        return diff != 0?diff:description.compareTo(o.description);
    }

    @Override
    public String toString() {
        return "Item{" +
                "description='" + description + '\'' +
                ", partNumber=" + partNumber +
                '}';
    }
}

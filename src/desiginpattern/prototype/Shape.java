package desiginpattern.prototype;

public abstract class Shape implements Cloneable {
    private String id;
    protected String type;

   public abstract void draw();

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object clone(){
        Object cloned = null;
        try {
            cloned = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloned;
    }

}

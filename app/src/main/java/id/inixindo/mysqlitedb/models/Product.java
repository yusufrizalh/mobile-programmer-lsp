package id.inixindo.mysqlitedb.models;

public class Product {
    public Product() {
    }

    public String toString() {
        return name + " " + price + " " + description;
    }

    private long id;
    private String name;
    private String price;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

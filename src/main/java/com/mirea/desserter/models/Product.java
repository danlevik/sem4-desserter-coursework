package com.mirea.desserter.models;


import javax.persistence.*;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type_id")
    private int typeId;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "description")
    private String description;

    @Column(name = "cover_link")
    private String coverLink;


    public Product() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}

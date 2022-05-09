package com.mirea.desserter.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
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

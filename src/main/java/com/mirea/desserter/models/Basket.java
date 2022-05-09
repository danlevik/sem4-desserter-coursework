package com.mirea.desserter.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_count")
    private int productCount;

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", productCount=" + productCount +
                '}';
    }
}

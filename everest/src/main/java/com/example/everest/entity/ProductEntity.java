package com.example.everest.entity;

import jakarta.persistence.*;

@Entity(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "old_price")
    private double oldPrice;
    @Column(name = "new_price")
    private double newPrice;
    @Column(name = "description")
    private String description;
    @Column(name = "information")
    private String information;
    @Column(name = "sku")
    private long sku;

    @ManyToOne
    @JoinColumn(name = "id_image")
    private ProductImageEntity productImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public long getSku() {
        return sku;
    }

    public void setSku(long sku) {
        this.sku = sku;
    }

    public ProductImageEntity getProductImage() {
        return productImage;
    }

    public void setProductImage(ProductImageEntity productImage) {
        this.productImage = productImage;
    }
}

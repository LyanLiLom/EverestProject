package com.example.everest.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank(message = "Tên sản phẩm không được để trống.")
    private String productName;
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá cũ phải lớn hơn 0")
    private double oldPrice;
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá mới phải lớn hơn 0")
    private double newPrice;
    @NotBlank(message = "Mô tả sản phẩm không được để trống.")
    private String description;
    private String information;
    @Min(value = 1, message = "SKU phải là một số nguyên dương.")
    private int sku;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public int getSku() {
        return sku;
    }

    public void setSku(int sku) {
        this.sku = sku;
    }
}

package com.example.everest.payload.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {
    private int id;
    @NotNull(message = "MultipathFile không được để trống.")
    private MultipartFile multipartFile;

    @NotBlank(message = "Tên sản phẩm không được để trống.")
    private String productName;

    @DecimalMin(value = "0.0", inclusive = false, message = "Giá cũ phải lớn hơn 0")
    private double oldPrice;

    @DecimalMin(value = "0.0", inclusive = false, message = "Giá mới phải lớn hơn 0")
    private double newPrice;

    @NotNull(message = "Mô tả không được để trống")
    private String description;

    @NotNull(message = "Thông tin không được để trống")
    private String information;

    @Min(value = 1, message = "SKU phải là một số nguyên dương.")
    private long sku;

    public ProductRequest() {
    }

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

    public long getSku() {
        return sku;
    }

    public void setSku(long sku) {
        this.sku = sku;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}

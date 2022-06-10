package com.myapplication.condingexam.model;

public class Products {
    private String originalPrice, percentOff, imageUrl, name, description, salePrice;

    public Products(String originalPrice, String percentOff, String imageUrl, String name, String description, String salePrice) {
        this.originalPrice = originalPrice;
        this.percentOff = percentOff;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.salePrice = salePrice;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getPercentOff() {
        return percentOff;
    }

    public void setPercentOff(String percentOff) {
        this.percentOff = percentOff;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }
}

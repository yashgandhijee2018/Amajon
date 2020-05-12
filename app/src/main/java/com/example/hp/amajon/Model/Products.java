package com.example.hp.amajon.Model;

public class Products {
    String ProductID,category,date,description,image,pname,price,time;
    public Products()
    {
        //default constructor
    }

    public Products(String productID, String category, String date, String description, String image, String pname, String price, String time) {
        this.ProductID = productID;
        this.category = category;
        this.date = date;
        this.description = description;
        this.image = image;
        this.pname = pname;
        this.price = price;
        this.time = time;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

package com.infinty8.cliffexcart.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_cart")
public class ProductModelData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String desc;
    private int price;
    private String image;
    private float totalPrice;
    private int qty;

    public ProductModelData(String name, String desc, int price, String image,int qty) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.qty=qty;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductModelData)) return false;

        ProductModelData note = (ProductModelData) o;

        if (id != note.id) return false;
        return name != null ? name.equals(note.name) : note.name == null;
    }


    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ",image='" + image + '\'' +
                ",price='" + price + '\'' +
                '}';
    }

}

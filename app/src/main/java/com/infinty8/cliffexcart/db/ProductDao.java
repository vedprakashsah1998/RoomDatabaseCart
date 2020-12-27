package com.infinty8.cliffexcart.db;

import com.infinty8.cliffexcart.model.ProductModelData;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM product_cart ")
    List<ProductModelData> getAll();

    @Insert
    void insertProduct(ProductModelData pokemon);

    @Query("DELETE FROM product_cart WHERE name = :productname")
    void deleteProduct(String productname);

    @Query("DELETE FROM product_cart")
    void deleteAll();



    @Query("SELECT * FROM product_cart WHERE name = :productname")
    ProductModelData getProductByName(String productname);

    @Query("UPDATE product_cart SET qty =:qty WHERE id =:id")
    void updateProductQuantity(Integer qty,Integer id);

}

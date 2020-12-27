package com.infinty8.cliffexcart.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.infinty8.cliffexcart.repository.ProductRepo


class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val dbProductRepo: ProductRepo =
            ProductRepo(application)

    suspend fun getAllCart(): List<ProductModelData>? {
        return dbProductRepo.getAllProduct()
    }

    suspend fun getProductByName(name: String): ProductModelData? {
        return dbProductRepo.getProductByName(name)
    }

    fun deleteProduct(product: String?) {
        dbProductRepo.deleteProduct(product)
    }

    fun updateProductQuantity(qty: Int?, id: Int?){
        dbProductRepo.updateProductQuantity(qty,id)
    }



    fun insert(mobileGetInpUnits: ProductModelData) {
        dbProductRepo.insert(mobileGetInpUnits)
    }

    fun deleteAll() {
        dbProductRepo.deleteAll()
    }
}

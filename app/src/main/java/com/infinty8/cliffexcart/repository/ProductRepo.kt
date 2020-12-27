package com.infinty8.cliffexcart.repository

import android.app.Application
import com.infinty8.cliffexcart.db.DbProduct
import com.infinty8.cliffexcart.db.ProductDao
import com.infinty8.cliffexcart.model.ProductModelData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductRepo internal constructor(application: Application) {
    private val dbProductDao: ProductDao


    init {
        val db = DbProduct.getDatabase(application)
        dbProductDao = db.productDao()
    }


    internal fun insert(mobileGetInpUnits: ProductModelData) {
        CoroutineScope(IO).launch {
            dbProductDao.insertProduct(mobileGetInpUnits)
        }
    }



    fun deleteAll() {
        CoroutineScope(IO).launch {
            dbProductDao.deleteAll()
        }
    }


    suspend fun getAllProduct(): List<ProductModelData>? = withContext(IO) {
        return@withContext dbProductDao.all
    }
    suspend fun getProductByName(name: String): ProductModelData? = withContext(IO) {
        return@withContext dbProductDao.getProductByName(name)
    }


    fun updateProductQuantity(qty: Int?, id: Int?){
        dbProductDao.updateProductQuantity(qty,id)
    }

    fun deleteProduct(product: String?) {
        dbProductDao.deleteProduct(product)
    }



}

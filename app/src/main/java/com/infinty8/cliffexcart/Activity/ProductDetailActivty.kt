package com.infinty8.cliffexcart.Activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.infinty8.cliffexcart.databinding.ActivityProductDetailActivtyBinding
import com.infinty8.cliffexcart.model.ProductModelData
import com.infinty8.cliffexcart.model.ProductViewModel
import kotlinx.android.synthetic.main.cart_adapter.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailActivty : AppCompatActivity() {
    var binding: ActivityProductDetailActivtyBinding? = null
    var viewModel: ProductViewModel? = null
    private var name: String? = null
    private var desc: String? = null
    private var image: String? = null
    private var price: Int? = null
    private var qty: Int? = null

    // DbProduct database;
    var modelData: ProductModelData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailActivtyBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        val intent = intent
        name = intent.getStringExtra("name")
        price = intent.extras?.getInt("price")
        desc = intent.extras?.getString("desc")
        image = intent.extras?.getString("image")
        qty=intent.extras?.getInt("qty")
        binding!!.productNameDetail.text = name
        Log.d("priceDetail", price.toString()+"Hello")
        binding!!.priceDetail.text = "Rs " + price.toString()
        binding!!.productDescDetail.text = desc
        Glide.with(this@ProductDetailActivty).load(image).into(binding!!.productDetailImg)
        binding!!.titleTextData.text = name
        binding!!.back.setOnClickListener { v: View? -> onBackPressed() }
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> binding!!.titleTextData.setTextColor(Color.parseColor("#FFFFFF"))
            Configuration.UI_MODE_NIGHT_NO -> binding!!.titleTextData.setTextColor(Color.parseColor("#000000"))
        }


        CoroutineScope(IO).launch {
            name?.let {

                if (viewModel!!.getProductByName(it) == null) {
                    withContext(Main) {
                        binding!!.addtoCart.text = "Add to Cart"

                    }

                } else {
                    withContext(Main) {
                        binding!!.addtoCart.text = "Already in Cart"

                    }

                }
            }

        }


        binding!!.addtoCart.setOnClickListener {
            modelData = price?.let { it1 -> qty?.let { it2 -> ProductModelData(name, desc, it1, image, it2) } }
            //  InsertTask(this@ProductDetailActivty, modelData!!).execute()
            binding!!.addtoCart.text = "Already in Cart"

            CoroutineScope(IO).launch {
                viewModel?.insert(modelData!!)
            }

        }
    }

    private fun setResult(note: ProductModelData, flag: Int) {
        setResult(flag, Intent().putExtra("product", note.toString()))
        finish()
    }

    /*private class InsertTask internal constructor(context: ProductDetailActivty, productModelData: ProductModelData) : AsyncTask<Void?, Void?, Boolean>() {
        private val activityReference: WeakReference<ProductDetailActivty>
        private val productModelData: ProductModelData

        // doInBackground methods runs on a worker thread
        protected override fun doInBackground(vararg objs: Void): Boolean {
            activityReference.get().database.productDao().insertProduct(productModelData)
            return true
        }

        // onPostExecute runs on main thread
        override fun onPostExecute(bool: Boolean) {
            if (bool) {
                activityReference.get()!!.setResult(productModelData, 1)
            }
        }

        // only retain a weak reference to the activity
        init {
            activityReference = WeakReference(context)
            this.productModelData = productModelData
        }
    }*/
}
package com.infinty8.cliffexcart.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.infinty8.cliffexcart.Adapter.CartAdapter.CartHolder
import com.infinty8.cliffexcart.databinding.CartAdapterBinding
import com.infinty8.cliffexcart.fragment.CartFragment
import com.infinty8.cliffexcart.model.ProductModelData
import com.infinty8.cliffexcart.model.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class CartAdapter(private val context: Context, list: ArrayList<ProductModelData>, adapterInterface: AdapterInterface) : RecyclerView.Adapter<CartHolder>() {
    private var list: ArrayList<ProductModelData> = ArrayList()

    private var count = 0
    private val viewModel: ProductViewModel
    private val adapterInterface: AdapterInterface
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CartAdapterBinding.inflate(inflater, parent, false)
        adapterInterface.buttonPressed(getTotal())
        return CartHolder(binding)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {


        val price = list[position].price


        holder.binding.productNameCart.text = list[position].name
        holder.binding.productPriceCart.text = price.toString()
        holder.binding.qty.text = list[position].qty.toString()

        Glide.with(context).load(list[position].image).into(holder.binding.productImgCart)




        holder.binding.plus.setOnClickListener { v: View? ->

            list[position].qty++
            holder.binding.qty.text = list[position].qty.toString()

            CoroutineScope(IO).launch {
                viewModel.updateProductQuantity(list[position].qty, list[position].id)
            }

            var total = 0

            Log.d("DateCalc89", "quantity: ${list[position].qty}")
            Log.d("DateCalc89", "price: $price")
            adapterInterface.buttonPressed(getTotal())
        }
        holder.binding.minus.setOnClickListener { v: View? ->
            list[position].qty--
            CoroutineScope(IO).launch {
                viewModel.updateProductQuantity(list[position].qty, list[position].id)
            }
            holder.binding.qty.text = list[position].qty.toString()
            if (list[position].qty == 0) {
                CoroutineScope(IO).launch {
                    viewModel.deleteProduct(holder.binding.productNameCart.text.toString())
                }
                list.removeAt(position)
                notifyItemRemoved(position)

            }


            adapterInterface.buttonPressed(getTotal())

        }



    }

    fun getTotal(): Int {
        var total = 0
        for (item in list) {
            total += (item.price * item.qty)
        }
        Log.d("total", total.toString())
        return total
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class CartHolder(var binding: CartAdapterBinding) : RecyclerView.ViewHolder(binding.root)


    init {
        this.list = list
        this.adapterInterface = adapterInterface
        viewModel = ViewModelProvider((context as FragmentActivity)).get(ProductViewModel::class.java)
    }
}

interface AdapterInterface {
    fun buttonPressed(total: Int)
}
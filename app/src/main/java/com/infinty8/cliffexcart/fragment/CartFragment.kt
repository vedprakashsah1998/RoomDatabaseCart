package com.infinty8.cliffexcart.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.infinty8.cliffexcart.Adapter.AdapterInterface
import com.infinty8.cliffexcart.Adapter.CartAdapter
import com.infinty8.cliffexcart.databinding.FragmentCartBinding
import com.infinty8.cliffexcart.db.DbProduct
import com.infinty8.cliffexcart.db.DbProduct.Companion.getDatabase
import com.infinty8.cliffexcart.model.ProductModelData
import com.infinty8.cliffexcart.model.ProductViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartFragment : Fragment(),AdapterInterface {
    var binding: FragmentCartBinding? = null
    var adapter: CartAdapter? = null
    private var noteDatabase: DbProduct? = null
    private var modelData: List<ProductModelData>? = null
    var viewModel: ProductViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false)
        noteDatabase = getDatabase(context!!)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        // new RetrieveTask(this).execute();
        binding!!.cartAdapter.layoutManager = LinearLayoutManager(context)


        CoroutineScope(IO).launch {
            var list = viewModel!!.getAllCart()
            withContext(Main) {
                list?.let {list->
                    adapter = CartAdapter(requireContext(), list as ArrayList<ProductModelData>,this@CartFragment)
                    binding!!.cartAdapter.adapter = adapter
                    adapter?.notifyDataSetChanged()
                }

            }
        }

        setView()


        return binding!!.root
    }

    fun setView() {

    }

    override fun buttonPressed(total:Int) {
        Log.d("updateAdater8", "setView: ")
        val subTotal = total
        val GST = total * 5 / 100
        val total = GST + subTotal

        binding!!.subTotal.text = "Sub Total =  " + subTotal.toString()
        binding!!.GST.text = "GST 5% =  " + GST.toString()
        binding!!.Total.text = "Total =  " + total.toString()
    }


}
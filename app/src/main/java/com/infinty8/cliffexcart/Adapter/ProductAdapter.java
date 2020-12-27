package com.infinty8.cliffexcart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.infinty8.cliffexcart.Activity.ProductDetailActivty;
import com.infinty8.cliffexcart.model.ProductModel;
import com.infinty8.cliffexcart.databinding.ProductAdapterBinding;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder>
{

    private final Context context;
    private final List<ProductModel> list;

    public ProductAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ProductAdapterBinding binding=ProductAdapterBinding.inflate(inflater,parent,false);

        return new ProductHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        holder.binding.productName.setText(list.get(position).getName());
        holder.binding.productPrice.setText(String.valueOf(list.get(position).getPrice()));

        Glide.with(context).load(list.get(position).getImage()).into(holder.binding.productImg);
        holder.binding.cardView.setOnClickListener(v -> {
            Log.d("priceDetail",list.get(position).getPrice()+"Hello");

            Intent intent=new Intent(context, ProductDetailActivty.class);
            intent.putExtra("name",list.get(position).getName());
            intent.putExtra("price",list.get(position).getPrice());
            intent.putExtra("desc",list.get(position).getDesc());
            intent.putExtra("image",list.get(position).getImage());
            intent.putExtra("qty",1);

            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {
        ProductAdapterBinding binding;
        public ProductHolder(@NonNull ProductAdapterBinding itemView) {
            super(itemView.getRoot());
            this.binding=itemView;
        }
    }
}

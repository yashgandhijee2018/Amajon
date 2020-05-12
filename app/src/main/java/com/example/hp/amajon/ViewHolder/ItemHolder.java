package com.example.hp.amajon.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.amajon.Interface.TempClass;
import com.example.hp.amajon.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView name,price,description;
    public ImageView img;
    public TempClass listner;
    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        img=(ImageView)itemView.findViewById(R.id.product_image);
        name=(TextView)itemView.findViewById(R.id.product_name);
        price=(TextView)itemView.findViewById(R.id.product_price);
        description=(TextView)itemView.findViewById(R.id.product_description);
    }

    public void setItemClickListner(TempClass listner)
    {
        this.listner=listner;
    }
    @Override
    public void onClick(View v) {
        listner.onClick(v,getAdapterPosition(),false);
    }
}

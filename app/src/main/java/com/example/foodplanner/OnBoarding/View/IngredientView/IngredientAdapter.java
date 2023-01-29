package com.example.foodplanner.OnBoarding.View.IngredientView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.foodplanner.OnBoarding.Models.Ingredient.Ingredient;
import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.Holder>  {
    private List<Ingredient> list = new ArrayList<>();
    //private OnMealClick listOnClickItem;
    private Context context;

    public IngredientAdapter(List<Ingredient> list2){//}, OnMealClick listOnClickItem) {
        this.list = list2;
        //this.listOnClickItem = listOnClickItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_meal, parent, false);
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_ingrd_test, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Ingredient ingredient = list.get(position);
        holder.ingrad_id_tv.setText(ingredient.getIdIngredient().toString());
        holder.ingrad_name_tv.setText(ingredient.getStrIngredient());
        Glide.with(context)
                .load(ingredient.getStIngredientThumb())
                .into(holder.ingrad_photo);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView ingrad_photo;

        public TextView  ingrad_name_tv,  ingrad_id_tv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ingrad_photo = itemView.findViewById(R.id.ingraduant_image);
            ingrad_name_tv=itemView.findViewById(R.id.ingredient_name_tv);
            ingrad_id_tv = itemView.findViewById(R.id.ingraduant_id);


        }

        @Override
        public void onClick(View view) {
            //listOnClickItem.onClickIndex(getAdapterPosition());
            Log.i("clicked","ingrediant adapter");

        }

    }
}


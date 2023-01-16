package com.example.foodplanner.OnBoarding.Views.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.Holder>  {
    private ArrayList<Meal> list = new ArrayList<>();
    private OnMealClick listOnClickItem;

    public MealAdapter(ArrayList<Meal> list, OnMealClick listOnClickItem) {
        this.list = list;
        this.listOnClickItem = listOnClickItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_container, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {


        Meal meal = list.get(position);
        holder.meal_id_tv.setText(meal.getIdMeal().toString());
        holder.meal_name_tv.setText(meal.getStrMeal());
        Glide.with(holder.meal_photo.getContext()).load(meal.getStrMealThumb()).into(holder.meal_photo);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {



        public ImageView meal_photo;

        public TextView  meal_name_tv,  meal_id_tv;

        public Holder(@NonNull View itemView) {
            super(itemView);

            meal_photo = itemView.findViewById(R.id.meal_image);

           meal_name_tv=itemView.findViewById(R.id.meal_name);
           meal_id_tv = itemView.findViewById(R.id.meal_ingraduants);


        }

        @Override
        public void onClick(View view) {
            listOnClickItem.onClickIndex(getAdapterPosition());

        }

    }
}


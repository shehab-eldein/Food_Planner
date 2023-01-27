package com.example.foodplanner.OnBoarding.View.MealFilterdView;

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
import java.util.List;


public class MealAdapterSearch extends RecyclerView.Adapter<MealAdapterSearch.Holder>  {
    private List<Meal> list = new ArrayList<>();


    public MealAdapterSearch(List<Meal> list){
        this.list = list;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        Meal meal = list.get(position);
        holder.meal_name_tv.setText(meal.getStrMeal());
        Glide.with(holder.meal_photo.getContext()).load(meal.getStrMealThumb()).into(holder.meal_photo);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{



        public ImageView meal_photo;

        public TextView meal_name_tv;

        public Holder(@NonNull View itemView) {
            super(itemView);

            meal_photo = itemView.findViewById(R.id.meal_image);

            meal_name_tv=itemView.findViewById(R.id.meal_name);
        }
    }


}


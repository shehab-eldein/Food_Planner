package com.example.foodplanner.OnBoarding.View.MealListView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.OnBoarding.Favorite.Favorite_Fragment;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.R;

import java.util.List;


public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.Holder> {
    private List<MealList> meal_list;
    private List<Meal> fav_meals;
    Boolean isFav = false;
    private OnMeallistClickListener onMeallistClickListener;
    private OnClickMealListenerFav onClickMealListenerFav;
    public MealListAdapter(List<MealList> meal_list,OnMeallistClickListener onMeallistClickListener){
        this.meal_list=meal_list;

        this.onMeallistClickListener=onMeallistClickListener;

    }

    public MealListAdapter(List<Meal> fav_meals,
                           Favorite_Fragment favorite_fragment,
                           OnClickMealListenerFav onClickMealListenerFav) {
        this.fav_meals = fav_meals;
        isFav = true;
        this.onClickMealListenerFav=onClickMealListenerFav;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_list_row, parent, false);

        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        if (isFav) {
            Meal favMeal = fav_meals.get(position);
            holder.meal_name_tv.setText(favMeal.getStrMeal());
            Glide.with(holder.meal_photo.getContext()).load(favMeal.getStrMealThumb()).into(holder.meal_photo);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickMealListenerFav.onClickMealFav(favMeal,position);
                }
            });

        }else {
            MealList meal = meal_list.get(position);
            holder.meal_name_tv.setText(meal.getStrMeal());
            Glide.with(holder.meal_photo.getContext()).load(meal.getStrMealThumb()).into(holder.meal_photo);
            ///////////////////////////////////
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onMeallistClickListener.onClickMealList(meal,position);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        if (isFav) {
            return fav_meals.size();
        }
        return meal_list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView meal_photo;
        public TextView  meal_name_tv,meal_day;


        ////////////////
        public Button delete;


        public Holder(@NonNull View itemView) {
            super(itemView);

            meal_photo = itemView.findViewById(R.id.meal_image);
            meal_name_tv=itemView.findViewById(R.id.meal_name);

//////////////////////////////////////
            delete=itemView.findViewById(R.id.delete_btn);
        }


        @Override
        public void onClick(View v) {


        }
    }
}



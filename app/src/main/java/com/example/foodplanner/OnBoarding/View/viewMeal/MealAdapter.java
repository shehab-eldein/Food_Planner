package com.example.foodplanner.OnBoarding.View.viewMeal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Constraints;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.foodplanner.OnBoarding.Home.Home_FragmentDirections;
import com.example.foodplanner.OnBoarding.Models.CategoryModel.Category;
import com.example.foodplanner.OnBoarding.Models.mealModel.Meal;
import com.example.foodplanner.R;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.Holder>  {
    private ArrayList<Meal> mealArrayList = new ArrayList<>();
    private ArrayList<Category> categoryArrayList = new ArrayList<>();
    private OnMealClick listOnClickItem;
    public boolean isRandom;


    public MealAdapter(ArrayList<Meal> list, OnMealClick listOnClickItem, boolean isRandom) {
        this.mealArrayList = list;
        this.listOnClickItem = listOnClickItem;
        this.isRandom = isRandom;
    }
    public MealAdapter(ArrayList<Category> list,boolean isRandom) {
        this.isRandom = isRandom;
        this.categoryArrayList = list;

    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

       if (isRandom) {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row, parent, false);
        }
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_row, parent, false);

        return new Holder(view,isRandom);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (isRandom) {
            handelRandomDesign(holder,position);
        } else {
           handelCategoryDesign(holder,position);
        }

    }

    void handelRandomDesign (Holder holder,int position) {
        Meal meal = mealArrayList.get(position);
        holder.meal_name_tv.setText(meal.getStrMeal());
        Glide.with(holder.meal_photo.getContext()).load(meal.getStrMealThumb()).into(holder.meal_photo);
    }
    void handelCategoryDesign(Holder holder,int position) {
        Category category = categoryArrayList.get(position);
        Glide.with(holder.meal_photo.getContext()).load(category.getStrCategoryThumb()).into(holder.meal_photo);
        holder.meal_name_tv.setVisibility(View.INVISIBLE);

        holder.meal_photo.setLayoutParams(new ViewGroup.LayoutParams(Constraints.LayoutParams.WRAP_CONTENT,Constraints.LayoutParams.WRAP_CONTENT));
        holder.meal_photo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.container.setLayoutParams(new ViewGroup.LayoutParams(300,250));
    }

    @Override
    public int getItemCount() {
       if (isRandom) {
            return mealArrayList.size();
        }
        return categoryArrayList.size();

    }



    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView meal_photo;
        public TextView  meal_name_tv, favLabel;
        public LinearLayout container;

        public Holder(@NonNull View itemView, Boolean isRandom) {
            super(itemView);
                meal_photo = itemView.findViewById(R.id.meal_image);
                meal_name_tv=itemView.findViewById(R.id.meal_name);
                favLabel = itemView.findViewById(R.id.fav_icon);
                container = itemView.findViewById(R.id.view_container2);
                itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            listOnClickItem.onClickIndex(getAdapterPosition());

            Home_FragmentDirections.ActionHomeFragmentToDetailFragment action = Home_FragmentDirections.actionHomeFragmentToDetailFragment();
            action.setID(mealArrayList.get(this.getAdapterPosition()).getIdMeal());
            Navigation.findNavController(view).navigate(action);

        }

    }
}


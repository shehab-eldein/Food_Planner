package com.example.foodplanner.OnBoarding.View.viewMealList;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodplanner.OnBoarding.Models.MealListModel.MealList;
import com.example.foodplanner.R;

import java.util.List;


public class MealListAdapter extends RecyclerView.Adapter<MealListAdapter.Holder> {
    private List<MealList> meal_list;


    public MealListAdapter(List<MealList> meal_list){//}, OnFavClickListener listOnClickItem) {
        this.meal_list=meal_list;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_list_row, parent, false);

        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        MealList meal = meal_list.get(position);

       /* switch(meal.getDay()) {
            case 1:
                holder.meal_day.setText("Saturday");
                break;

            case 2:
                holder.meal_day.setText("Sunday");
                break;

            case 3:
                holder.meal_day.setText("Monday");
                break;

            case 4:
                holder.meal_day.setText("Tuesday");
                break;

            case 5:
                holder.meal_day.setText("Wednesday");
                break;

            case 6:
                holder.meal_day.setText("Thursday");
                break;

            case 7:
                holder.meal_day.setText("Friday");
                break;
        }
*/
        holder.meal_name_tv.setText(meal.getStrMeal());
       
        holder.meal_day.setText(meal.getDay());
        Glide.with(holder.meal_photo.getContext()).load(meal.getStrMealThumb()).into(holder.meal_photo);


    }

    @Override
    public int getItemCount() {
        return meal_list.size();
    }

    public class Holder extends RecyclerView.ViewHolder  {
        public ImageView meal_photo;

        public TextView  meal_name_tv,meal_day;

        public Holder(@NonNull View itemView) {
            super(itemView);

            meal_photo = itemView.findViewById(R.id.meal_image);
            meal_name_tv=itemView.findViewById(R.id.meal_name);
            meal_day=itemView.findViewById(R.id.meal_day);

        }



    }
}



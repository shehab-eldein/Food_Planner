package com.example.foodplanner.OnBoarding.View.viewMealList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodplanner.R;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.Holder>{
    private List<String> days;
    private  OnDayClickListener onDayClickListener;



    public DayAdapter(List<String>data   ,  OnDayClickListener  onDayClickListener) {
        this.days=data;
        this.onDayClickListener=onDayClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_row, parent, false);

        return new Holder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        String day=days.get(position);
        holder.day_name.setText(day);
        holder.itemView.setTag(day);

    }

    @Override
    public int getItemCount() {
        return  days.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements  View.OnClickListener {

        public TextView day_name;

        public Holder(@NonNull View itemView) {
            super(itemView);

            day_name = itemView.findViewById(R.id.day_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDayClickListener.onClickDay(view.getTag().toString());

        }
    }

}

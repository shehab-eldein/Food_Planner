package com.example.foodplanner.OnBoarding.View.viewSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodplanner.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends   RecyclerView.Adapter<SearchAdapter.Holder>  {
    private List<String> list = new ArrayList<>();
   // private List<String> list = new ArrayList<>();
    //private OnMealClick listOnClickItem;
    private Context context;

    public SearchAdapter(List<String> list2){
          //  , OnMealClick listOnClickItem) {
        this.list = list2;
       // this.listOnClickItem = listOnClickItem;
    }



    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_meal, parent, false);
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        String row = list.get(position);

        holder.search_item_tv.setText(row);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }




public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {



    public TextView search_item_tv;

    public Holder(@NonNull View itemView) {
        super(itemView);

        search_item_tv = itemView.findViewById(R.id.search_reslut);



    }

    @Override
    public void onClick(View view) {

    }
}

}
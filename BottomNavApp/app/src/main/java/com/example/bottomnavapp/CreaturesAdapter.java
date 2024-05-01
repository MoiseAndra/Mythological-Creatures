package com.example.bottomnavapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CreaturesAdapter extends RecyclerView.Adapter<CreaturesAdapter.MyViewHolder> implements Filterable {
    private final RecycleViewInterface recycleViewInterface;

    Context context;
    ArrayList<CreaturesModel> creaturesModels;
    ArrayList<CreaturesModel> creaturesModelsFiltered = new ArrayList<>();

    public CreaturesAdapter(Context context, ArrayList<CreaturesModel> creaturesModels, RecycleViewInterface recycleViewInterface ){
        this.context = context;
        this.creaturesModels = creaturesModels;
        this.creaturesModelsFiltered = creaturesModels;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public CreaturesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycle_view_row, parent, false);
        return new CreaturesAdapter.MyViewHolder(view, recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull CreaturesAdapter.MyViewHolder holder, int position) {
        CreaturesModel cretModel = creaturesModels.get(position);

        holder.textView.setText(creaturesModels.get(position).getCretName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleViewInterface.onItemClick(cretModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return creaturesModels.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0)
                {
                    filterResults.values = creaturesModelsFiltered;
                    filterResults.count = creaturesModelsFiltered.size();
                }
                else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<CreaturesModel> cretMod = new ArrayList<>();
                    for (CreaturesModel creaturesModel: creaturesModelsFiltered){
                        if (creaturesModel.getCretName().toLowerCase().contains(searchStr)){
                            cretMod.add(creaturesModel);
                        }
                    }

                    filterResults.values = cretMod;
                    filterResults.count = cretMod.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                creaturesModels = (ArrayList<CreaturesModel>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;

        public MyViewHolder(@NonNull View itemView, RecycleViewInterface recycleViewInterface) {
            super(itemView);

            textView = itemView.findViewById(R.id.nameRVCreature);

        }
    }
}

package com.lucasortizny.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.ViewHolder> {
    /*public void notifyItemRangeRemoved(int i) {
        for (int count = items.size(); count >= i; count--){

        }
    }*/

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    public itemsAdapter(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoview = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab item at the position
        String item = items.get(position);
        //Binding
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        //This is doable because the List structure has this method that exists.
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textviewitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textviewitem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String item) {
            textviewitem.setText(item);
            textviewitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });


        }
    }


}

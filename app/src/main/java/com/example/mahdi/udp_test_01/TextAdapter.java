package com.example.mahdi.udp_test_01;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;



public class TextAdapter extends RecyclerView.Adapter<TextAdapter.TextHolder>{
    List<String> dataList = new ArrayList<>();
    @Override
    public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.item_text,parent,false);
        return new TextHolder(item);
    }

    @Override
    public void onBindViewHolder(TextHolder holder, int position) {
        holder.textView.setText(dataList.get(position));
    }
    public void addText(String text){
        dataList.add(text);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class TextHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public TextHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }
}

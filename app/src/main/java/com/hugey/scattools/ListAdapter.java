package com.hugey.scattools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 12/19/16.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CategoryViewHolder>{


    private Context context;
    private LayoutInflater inflater;
    private List<Category> categories;
    
    public ListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }
    
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(inflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        holder.init("" + position + " ", categories.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addItem(Category category) {
        categories.add(category);
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNumber;
        private TextView mTvCategory;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            mTvNumber = (TextView) itemView.findViewById(R.id.list_item_tv_number);
            mTvCategory = (TextView) itemView.findViewById(R.id.list_item_tv_category);
        }

        public void init(String number, String category) {
            mTvNumber.setText(number);
            mTvCategory.setText(category);
        }
    }
}

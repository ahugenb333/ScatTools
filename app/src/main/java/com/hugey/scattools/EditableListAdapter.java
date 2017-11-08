package com.hugey.scattools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by austin on 12/19/16.
 */

public class EditableListAdapter extends RecyclerView.Adapter<EditableListAdapter.EditableCategoryViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Category> categories;

    public EditableListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.categories = new ArrayList<>();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public EditableCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditableCategoryViewHolder(inflater.inflate(R.layout.list_item_editable, parent, false));
    }

    @Override
    public void onBindViewHolder(EditableCategoryViewHolder holder, int position) {
        holder.init("" + position + " ", categories.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addItem(Category category) {
        categories.add(category);
    }


    public class EditableCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNumber;
        private TextView mTvCategory;
        private EditText mEtEditable;

        private EditableCategoryViewHolder(View itemView) {
            super(itemView);
            mTvNumber = (TextView) itemView.findViewById(R.id.list_item_tv_number);
            mTvCategory = (TextView) itemView.findViewById(R.id.list_item_tv_category);
            mEtEditable = (EditText) itemView.findViewById(R.id.list_item_editable_et);
        }

        public void init(String number, String category) {
            mTvNumber.setText(number);
            mTvCategory.setText(category);
        }
    }
}

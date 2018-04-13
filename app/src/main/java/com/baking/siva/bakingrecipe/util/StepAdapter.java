package com.baking.siva.bakingrecipe.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.siva.bakingrecipe.R;

import java.util.ArrayList;

/**
 * Created by sivakumarpadala on 13/04/18.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private final ArrayList<String> recipeList;
    Context mContext;
    final private ListItemClickListener mOnClickListener;
    //private final OnClickListenerer mOnClickListener = new MyOnClickListener();

    public StepAdapter(Context context, ArrayList<String> recipeList,ListItemClickListener listener) {
        mContext = context;
        this.recipeList = recipeList;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            //mTextView = v;
            mTextView = v.findViewById(R.id.recipe_text_view);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = (View) LayoutInflater.from(context)
                .inflate(R.layout.recipe_adapter_view, parent, false);

        /*view = inflater.inflate(R.layout.recipe_adapter_view,null);
        TextView txtView = view.findViewById(R.id.recipe_text_view);*/
        //v.setOnClickListener(mOnClickListener);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

}

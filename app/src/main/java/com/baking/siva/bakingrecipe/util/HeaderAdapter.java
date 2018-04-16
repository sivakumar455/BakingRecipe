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
 * Created by sivakumarpadala on 17/04/18.
 */

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ViewHolder> {
    private final ArrayList<String> recipeList;
    Context mContext;
    final private HeaderAdapter.ListItemClickListener mOnClickListener;

    public HeaderAdapter(Context context, ArrayList<String> recipeList,HeaderAdapter.ListItemClickListener listener) {
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
            mTextView = v.findViewById(R.id.recipe_text_view_hdr);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    @Override
    public HeaderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = (View) LayoutInflater.from(context)
                .inflate(R.layout.recipe_adaper_list, parent, false);

        /*view = inflater.inflate(R.layout.recipe_adapter_view,null);
        TextView txtView = view.findViewById(R.id.recipe_text_view);*/
        //v.setOnClickListener(mOnClickListener);

        HeaderAdapter.ViewHolder vh = new HeaderAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(HeaderAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

}

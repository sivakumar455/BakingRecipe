package com.baking.siva.bakingrecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sivakumarpadala on 01/04/18.
 */

public class RecipeAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<String> recipeList;

    public RecipeAdapter(Context context, ArrayList<String> recipeList){

        this.recipeList = recipeList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.recipe_adapter_view,null);
        TextView txtView = view.findViewById(R.id.recipe_text_view);
        txtView.setText(recipeList.get(i));
        return view;
    }
}

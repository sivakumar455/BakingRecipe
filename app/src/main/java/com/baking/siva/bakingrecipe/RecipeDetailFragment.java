package com.baking.siva.bakingrecipe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sivakumarpadala on 31/03/18.
 */

public class RecipeDetailFragment extends Fragment {
    HashMap<String, HashMap<String, String>> hashIng;
    HashMap<String, String> hashStep;
    public RecipeDetailFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getArguments();
        if(b.getSerializable("hashIngredients") != null) {
            hashIng = (HashMap<String, HashMap<String, String>>) b.getSerializable("hashIngredients");
            Log.v("hashIngredients", String.valueOf(Arrays.asList(hashIng)));

        }else if (b.getSerializable("hashSteps") != null){
            hashStep = (HashMap<String, String>) b.getSerializable("hashSteps");
            Log.v("hashIngredients", String.valueOf(Arrays.asList(hashStep)));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = null;
        if (hashStep != null) {
            rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
            TextView textView = rootView.findViewById(R.id.recipe_detail_text_view);
            textView.setText(hashStep.get("description"));
            TextView videoView = rootView.findViewById(R.id.recipe_step_video_url);
            videoView.setText(hashStep.get("videoURL"));

        }else if(hashIng != null){
            rootView = inflater.inflate(R.layout.fragment_ingredients_list, container, false);

            //LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
            LinearLayout linearLayout = rootView.findViewById(R.id.fragment_ingredients);
            for (int idx=0; idx<Integer.parseInt(hashIng.get("Length").get("ingredientLength")); idx++) {
                TextView textView = new TextView(getActivity().getApplicationContext());
                textView.setText(hashIng.get("ingredients"+idx).get("ingredient")+ "\t"+
                        hashIng.get("ingredients"+idx).get("quantity")+"\t"+
                        hashIng.get("ingredients"+idx).get("measure"));
                linearLayout.addView(textView);
            }
            Toast.makeText(getActivity().getApplicationContext(),"HashTag",Toast.LENGTH_SHORT).show();

        }
        return rootView;
    }
}

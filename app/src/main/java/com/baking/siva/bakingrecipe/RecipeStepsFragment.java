package com.baking.siva.bakingrecipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sivakumarpadala on 31/03/18.
 */

public class RecipeStepsFragment extends Fragment {
    HashMap<String, HashMap<String, String>> recipeDet;
    OnStepClickListener mCallback;

    public interface OnStepClickListener{
        public void OnStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    public RecipeStepsFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = this.getArguments();
        if(b.getSerializable("hashmap") != null) {
            recipeDet = (HashMap<String, HashMap<String, String>>) b.getSerializable("hashmap");
            Log.v("MAP", String.valueOf(Arrays.asList(recipeDet)));
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps,container,false);
        ListView listView = rootView.findViewById(R.id.recipe_steps_list_view);

        ArrayList<String> recStep=new ArrayList<String>(); // = new ArrayList<String>(Arrays.asList(recSteps));
        //String recSteps[] = {"ABC","123"};
        recStep.add("Ingredients");
        Log.v("MAP", String.valueOf(Arrays.asList(recipeDet)));
        for (int idx=0 ; idx < Integer.parseInt(recipeDet.get("Length").get("stepLength")); idx++){
            Log.v("MAP","steps"+idx);
            recStep.add(recipeDet.get("steps"+idx).get("shortDescription"));
        }


        RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity().getApplicationContext(), recStep);
        listView.setAdapter(recipeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.OnStepSelected(position);
            }
        });

        return rootView;
    }
}

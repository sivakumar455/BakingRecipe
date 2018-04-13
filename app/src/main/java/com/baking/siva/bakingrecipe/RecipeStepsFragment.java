package com.baking.siva.bakingrecipe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baking.siva.bakingrecipe.util.StepAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */

public class RecipeStepsFragment extends Fragment implements StepAdapter.ListItemClickListener {
    private HashMap<String, HashMap<String, String>> recipeDet;
    private OnStepClickListener mCallback;
    private RecyclerView recyclerView;
    private StepAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager gridLayoutManager;

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Toast.makeText(getContext(),"Hello ",Toast.LENGTH_SHORT).show();
        mCallback.OnStepSelected(clickedItemIndex);
    }

    public interface OnStepClickListener{
        void OnStepSelected(int position);
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
            Log.v("MAP", String.valueOf(Collections.singletonList(recipeDet)));
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_steps,container,false);
        recyclerView = rootView.findViewById(R.id.recipe_steps_list_view);

        ArrayList<String> recStep= new ArrayList<>(); // = new ArrayList<String>(Arrays.asList(recSteps));
        //String recSteps[] = {"ABC","123"};
        recStep.add("Ingredients");
        Log.v("MAP", String.valueOf(Collections.singletonList(recipeDet)));
        for (int idx=0 ; idx < Integer.parseInt(recipeDet.get("Length").get("stepLength")); idx++){
            Log.v("MAP","steps"+idx);
            recStep.add(recipeDet.get("steps"+idx).get("shortDescription"));
        }

        //gridLayoutManager = new GridLayoutManager(getContext(),3);

        mLayoutManager = new LinearLayoutManager(getContext());



        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new StepAdapter(getContext(),recStep,this);
        recyclerView.setAdapter(mAdapter);

        /*RecipeAdapter recipeAdapter = new RecipeAdapter(getActivity().getApplicationContext(), recStep);
        listView.setAdapter(recipeAdapter);*/


        /*recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.OnStepSelected(position);
            }
        });*/

        return rootView;
    }
}

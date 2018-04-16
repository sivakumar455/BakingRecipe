package com.baking.siva.bakingrecipe.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */

public class RecipeList {
    private final String jsonRecipeObj;

    public RecipeList(String recipeObj ){
        jsonRecipeObj = recipeObj;
    }

    public ArrayList<String> getRecipeList(){
        String name;
        ArrayList<String> recipeList = new ArrayList<>();
        try {
            JSONArray objectResults = new JSONArray(jsonRecipeObj);
            for (int idx=0; idx < objectResults.length();idx++) {
                JSONObject objName = objectResults.getJSONObject(idx);
                name = objName.getString("name");
                recipeList.add(name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return recipeList;
    }

    public HashMap<String,String> getRecipeHeader(int Id){
        HashMap<String,String> recipeHeader = new HashMap<>();
        try {
            JSONArray objectResults = new JSONArray(jsonRecipeObj);
            JSONObject objName = objectResults.getJSONObject(Id);
            String name = objName.getString("name");
            String id = objName.getString("id");
            String image = objName.getString("image");
            String servings = objName.getString("servings");
            recipeHeader.put("name",name);
            recipeHeader.put("id",id);
            recipeHeader.put("image",image);
            recipeHeader.put("servings",servings);
        }catch (Exception e){
            e.printStackTrace();
        }
        return recipeHeader;
    }

    public HashMap<String,HashMap<String,String>> getRecipeDetails(int position){
        HashMap<String ,HashMap<String,String>> map = new HashMap<>();
        HashMap<String,String> innMap;
        try {
            JSONArray objectResults = new JSONArray(jsonRecipeObj);
            JSONObject objName = objectResults.getJSONObject(position);
            JSONArray objIngrds = objName.getJSONArray("ingredients");
            for (int idx=0; idx < objIngrds.length();idx++) {
                innMap = new HashMap<>();
                JSONObject objIng = objIngrds.getJSONObject(idx);
                String quantity = objIng.getString("quantity");
                String measure = objIng.getString("measure");
                String ingredient = objIng.getString("ingredient");
                innMap.put("quantity",quantity);
                innMap.put("measure",measure);
                innMap.put("ingredient",ingredient);
                map.put("ingredients"+idx,innMap);
            }

            JSONArray objSteps = objName.getJSONArray("steps");
            for (int idx=0; idx < objSteps.length();idx++) {
                innMap = new HashMap<>();
                JSONObject objStep = objSteps.getJSONObject(idx);
                String shortDescription = objStep.getString("shortDescription");
                String description = objStep.getString("description");
                String videoURL = objStep.getString("videoURL");
                String thumbnailURL = objStep.getString("thumbnailURL");
                String id = objStep.getString("id");

                innMap.put("shortDescription",shortDescription);
                innMap.put("description",description);
                innMap.put("videoURL",videoURL);
                innMap.put("thumbnailURL",thumbnailURL);
                innMap.put("id",id);

                map.put("steps"+idx,innMap);
            }
            innMap = new HashMap<>();
            innMap.put("ingredientLength",String.valueOf(objIngrds.length()));
            innMap.put("stepLength",String.valueOf(objSteps.length()));
            map.put("Length",innMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }
}

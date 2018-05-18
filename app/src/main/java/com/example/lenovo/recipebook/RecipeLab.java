package com.example.lenovo.recipebook;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class RecipeLab {

    private ArrayList<Recipe> mRecipes;


    private Context mAppContext;

    private RecipeLab(Context context) {
        mAppContext = context.getApplicationContext();
        mRecipes = new ArrayList<>();
       /* for (int i = 0; i <= 2; i++) {
            Recipe recipe = new Recipe();
            recipe.setRecipeName("Recipe:" + i);
            recipe.setRecipeDescription("Desc:" + i);
            mRecipes.add(recipe);

        }
*/

    }

    private static RecipeLab sRecipeLab = null;

    public static RecipeLab getInstance(Context context) {
        if (sRecipeLab == null) {
            sRecipeLab = new RecipeLab(context);
        }
        return sRecipeLab;
    }

    public ArrayList<Recipe> getRecipes() {
        return mRecipes;

    }

    public void addRecipe(Recipe recipe) {
        mRecipes.add(recipe);

    }

    public void deleteRecipe(UUID recipeId){
        for (Recipe recipe : mRecipes) {
            if (recipe.getRecipeID().equals(recipeId)) {

                mRecipes.remove(recipe);
            }
        }


    }
}

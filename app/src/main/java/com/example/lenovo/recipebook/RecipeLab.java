package com.example.lenovo.recipebook;

import android.content.Context;

import java.util.ArrayList;

public class RecipeLab {

    private ArrayList<RecipeModel> mRecipes;


    private Context mAppContext;

    private RecipeLab(Context context) {
        mAppContext = context.getApplicationContext();
       // mRecipes = new ArrayList<>();
       /* for (int i = 0; i <= 2; i++) {
            RecipeModel recipe = new RecipeModel();
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

    public ArrayList<RecipeModel> getRecipes()
    {
        return mRecipes;

    }

    public void addRecipe(RecipeModel recipeModel){
        mRecipes.add(recipeModel);

    }
}

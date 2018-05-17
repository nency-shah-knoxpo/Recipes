package com.example.lenovo.recipebook;

import java.util.UUID;

public class RecipeModel {

    UUID mRecipeID;
    String mRecipeName;
    String mRecipeDescription;

    public RecipeModel() {
        mRecipeID = UUID.randomUUID();
    }


    public RecipeModel(String name,String Description) {
        mRecipeID = UUID.randomUUID();
        mRecipeName = name;
        mRecipeDescription = Description;
    }


    public UUID getRecipeID() {
        return mRecipeID;
    }


    public String getRecipeName() {
        return mRecipeName;
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
    }

    public String getRecipeDescription() {
        return mRecipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        mRecipeDescription = recipeDescription;
    }
}

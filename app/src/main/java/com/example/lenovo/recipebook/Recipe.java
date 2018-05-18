package com.example.lenovo.recipebook;

import java.util.UUID;

public class Recipe {

    private UUID mRecipeID;
    private String mRecipeName;
    private String mRecipeDescription;

    public Recipe() {
        mRecipeID = UUID.randomUUID();
    }


    public Recipe(String name, String Description) {
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

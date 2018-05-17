package com.example.lenovo.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

public class AddRecipeActivity extends SingleFragmentActivity implements AddRecipeFragment.CallBack {

    private static final String TAG = AddRecipeActivity.class.getSimpleName();
    private static final String ADDORCANCEL = TAG + ".ADDORCANCEL";

    String name;
    @Override
    protected Fragment createFragment() {
        name = getIntent().getSerializableExtra(RecipeListFragment.REC_NAME).toString();

        return  AddRecipeFragment.newInstance(name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Recipe");
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onRecipeAdd(boolean b) {
        Intent i = new Intent();
        i.putExtra(ADDORCANCEL,b);
        setResult(RESULT_OK,i);
        finish();
    }

    @Override
    public void onRecipeDiscarded(boolean b) {

    }
}

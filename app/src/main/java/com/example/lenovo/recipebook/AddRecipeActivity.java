package com.example.lenovo.recipebook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;

public class AddRecipeActivity extends SingleFragmentActivity implements AddRecipeFragment.CallBack {

    private static final String TAG = AddRecipeActivity.class.getSimpleName();
    public static final String EXTRA_IS_RECIPE_ADDED = TAG + ".EXTRA_IS_RECIPE_ADDED";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Fragment createFragment() {
        String name = getIntent().getSerializableExtra(RecipeListFragment.EXTRA_RECIPE_NAME).toString();
        return AddRecipeFragment.newInstance(name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onRecipeAdd(boolean b) {
        Intent i = new Intent();
        i.putExtra(EXTRA_IS_RECIPE_ADDED, b);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onRecipeDiscarded(boolean b) {
        Intent i = new Intent();
        i.putExtra(EXTRA_IS_RECIPE_ADDED, b);
        setResult(RESULT_CANCELED, i);
        finish();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Continue??");
        builder.setMessage(R.string.confirmation_message);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setResult(RESULT_CANCELED);
                AddRecipeActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                return;

            }
        });
        builder.show();

    }


}

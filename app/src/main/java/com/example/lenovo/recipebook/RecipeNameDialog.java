package com.example.lenovo.recipebook;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RecipeNameDialog extends DialogFragment {
    private static final String TAG = RecipeNameDialog.class.getSimpleName();
    public static final String RECIPE_NAME = TAG + ".RECIPE_NAME";

    private EditText mRecipeNameET;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.recipenamedialog, null);
        mRecipeNameET = (EditText) v.findViewById(R.id.et_dialog_recipe_name);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(v)

                .setTitle(R.string.label_name_recipe)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = mRecipeNameET.getText().toString();
                        sendResult(Activity.RESULT_OK, name);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), R.string.toast_recipe_discarded, Toast.LENGTH_SHORT).show();
                        sendResult(Activity.RESULT_CANCELED, null);
                    }
                });
        return builder.create();
    }

    private void sendResult(int resultcode, String name) {
        if (getTargetFragment() == null || name == null || resultcode == Activity.RESULT_CANCELED) {
            return;
        } else {
            Intent i = new Intent();
            i.putExtra(RECIPE_NAME, name);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultcode, i);
        }
    }
}

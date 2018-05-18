package com.example.lenovo.recipebook;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddRecipeFragment extends Fragment {

    private static final String TAG = AddRecipeFragment.class.getSimpleName();
    private static final String ARGS_RECIPE_NAME = TAG + ".ARGS_RECIPE_NAME";

    private EditText mRecipeNameET, mRecipeDescET;
    private Button mRecipeSaveBtn;

    private CallBack mCallBack;

    public interface CallBack {

        void onRecipeAdd(boolean b);

        void onRecipeDiscarded(boolean b);

    }

    @Override
    public void onAttach(Context context) {
        mCallBack = (CallBack) getActivity();
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        mCallBack = null;
        super.onDetach();
    }


    public static AddRecipeFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS_RECIPE_NAME, name);
        AddRecipeFragment fragment = new AddRecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_recipe_fragment, container, false);

        String name = getArguments().getSerializable(ARGS_RECIPE_NAME).toString();
        mRecipeDescET = (EditText) v.findViewById(R.id.et_recipe_description);
        mRecipeNameET = (EditText) v.findViewById(R.id.et_recipe_name);
        mRecipeSaveBtn = (Button) v.findViewById(R.id.btn_save_recipe);

        mRecipeSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mRecipeNameET.getText().toString();
                String desc = mRecipeDescET.getText().toString();
                Recipe recipe = new Recipe(name, desc);
                RecipeLab recipeLab = RecipeLab.getInstance(getActivity());
                recipeLab.addRecipe(recipe);
                mCallBack.onRecipeAdd(true);
            }
        });
        mRecipeNameET.setText(name);


        return v;
    }



}

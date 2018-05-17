package com.example.lenovo.recipebook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddRecipeFragment extends Fragment {

    private static final String TAG = AddRecipeFragment.class.getSimpleName();
    private static final String REC_NAME = TAG + ".REC_NAME";

    EditText mRecipename,mRecipeDesc;
    Button mSavebtn;
    public static AddRecipeFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putSerializable(REC_NAME,name);
        AddRecipeFragment fragment = new AddRecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_recipe_fragment, container, false);

        String name = getArguments().getSerializable(REC_NAME).toString();
        mRecipeDesc=(EditText)v.findViewById(R.id.txtRecipeDesc);
        mRecipename = (EditText)v.findViewById(R.id.txtRecipeName);
        mSavebtn=(Button)v.findViewById(R.id.btnSaveRecipe);

        mSavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = mRecipename.getText().toString();
                String desc = mRecipeDesc.getText().toString();
                RecipeModel recipeModel = new RecipeModel(name,desc);
                RecipeLab recipeLab = RecipeLab.getInstance(getActivity());
                recipeLab.addRecipe(recipeModel);
                mCallBack.onRecipeAdd(true);
            }
        });
        mRecipename.setText(name);



        return v;
    }
    private CallBack mCallBack;
    public interface CallBack{

        void onRecipeAdd(boolean b);
        void onRecipeDiscarded(boolean b);

    }
}

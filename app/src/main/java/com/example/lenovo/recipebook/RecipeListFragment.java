package com.example.lenovo.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class RecipeListFragment extends Fragment {
    private static final String TAG = RecipeListFragment.class.getSimpleName();
    private static final String DIALOG_RECIPE_NAME = TAG + ".DIALOG_RECIPE_NAME";
    public static final String EXTRA_RECIPE_NAME = TAG + ".EXTRA_RECIPE_NAME";

    private static final int REQUEST_RECIPE_NAME = 0;
    private static final int REQUEST_ADD_RECIPE = 1;


    private RecipeAdapter mAdapter;
    private RecyclerView mRecipeRV;
    private TextView mNullListTV;
    private Button mAddRecipeBtn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_list_fragment, container, false);
        mRecipeRV = (RecyclerView) v.findViewById(R.id.rv_recipe);
        mRecipeRV.addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        mRecipeRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNullListTV = (TextView) v.findViewById(R.id.tv_null_list);
        mAddRecipeBtn = (Button) v.findViewById(R.id.btn_add_recipe);
        mAddRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRecipeNameDialog();
            }
        });


        updateUI();
        return v;
    }

    public void updateUI() {


        RecipeLab recipeLab = RecipeLab.getInstance(getActivity());
        List<Recipe> recipes = recipeLab.getRecipes();

        mNullListTV.setVisibility(recipes.size() <= 0 ? View.VISIBLE : View.GONE);
        mAddRecipeBtn.setVisibility(recipes.size() <= 0 ? View.VISIBLE : View.GONE);
        mRecipeRV.setVisibility(recipes.size() <= 0 ? View.GONE : View.VISIBLE);
        if (mAdapter == null) {
            mAdapter = new RecipeAdapter(recipes);
            mRecipeRV.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {

        private Recipe mRecipe;
        private TextView mRecipeNameTV, mRecipeDate;
        ImageButton mDeleteIB;

        public RecipeHolder(View itemView) {
            super(itemView);

            mRecipeNameTV = (TextView) itemView.findViewById(R.id.et_recipe_name);
            mRecipeDate = (TextView) itemView.findViewById(R.id.tv_lastmodifydate);
            mDeleteIB = (ImageButton) itemView.findViewById(R.id.ib_delete);
            mDeleteIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RecipeLab recipeLab = RecipeLab.getInstance(getActivity());
                    recipeLab.deleteRecipe(mRecipe.getRecipeID());
                    updateUI();
                }
            });

        }

        public void bindRecipe(Recipe recipe) {
            mRecipe = recipe;
            mRecipeNameTV.setText(mRecipe.getRecipeName());
            mRecipeDate.setText(java.text.DateFormat.getDateInstance().format(new Date()));
        }
    }

    public class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {

        private List<Recipe> mRecipes;

        public RecipeAdapter(List<Recipe> recipes) {
            mRecipes = recipes;
        }


        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.recipelist, parent, false);
            return new RecipeHolder(view);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {

            Recipe recipe_model = mRecipes.get(position);
            holder.bindRecipe(recipe_model);
        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_RECIPE_NAME && resultCode == Activity.RESULT_OK) {
            String name = data.getStringExtra(RecipeNameDialog.RECIPE_NAME).toString();
            Intent i = new Intent(getActivity(), AddRecipeActivity.class);
            i.putExtra(EXTRA_RECIPE_NAME, name);
            startActivityForResult(i, REQUEST_ADD_RECIPE);
        } else if (requestCode == REQUEST_ADD_RECIPE && resultCode == Activity.RESULT_OK) {
            Boolean b = data.getBooleanExtra(AddRecipeActivity.EXTRA_IS_RECIPE_ADDED, true);
            if (b) {
                Toast.makeText(getActivity(), R.string.toast_recipe_added, Toast.LENGTH_SHORT).show();
                updateUI();
            }

        } else {
            Toast.makeText(getActivity(), R.string.toast_recipe_discarded, Toast.LENGTH_SHORT).show();
            updateUI();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_recipe, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_add_recipe: {
                openRecipeNameDialog();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openRecipeNameDialog() {

        FragmentManager manager = getFragmentManager();
        RecipeNameDialog dialog = new RecipeNameDialog();
        dialog.setTargetFragment(RecipeListFragment.this, REQUEST_RECIPE_NAME);
        dialog.show(manager, DIALOG_RECIPE_NAME);

    }
}

package com.example.lenovo.recipebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import java.util.List;

public class RecipeListFragment extends Fragment {
    private static final String TAG = RecipeListFragment.class.getSimpleName();
    private static final String DIALOG_RECIPE_NAME = TAG + ".DIALOG_RECIPE_NAME";
    private static final int REQUEST_RECIPE_NAME = 0;
    public static final String REC_NAME = TAG + ".DIALOG_RECIPE_NAME";
    private static final int REQUEST_ADD_RECIPE = 1;



    private RecipeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView mNullList;
    private Button mBtnAddRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_list_fragment, container, false);
mRecyclerView = (RecyclerView)v.findViewById(R.id.recipe_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNullList = (TextView)v.findViewById(R.id.txtNullList);
        mBtnAddRecipe = (Button)v.findViewById(R.id.btnAddRecipe);
        mBtnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                RecipeNameDialog dialog = new RecipeNameDialog();
                dialog.setTargetFragment(RecipeListFragment.this,REQUEST_RECIPE_NAME);
                dialog.show(manager,DIALOG_RECIPE_NAME);
            }
        });
updateUI();
        return v;
    }

    public void updateUI() {


        RecipeLab recipe_lab = RecipeLab.getInstance(getActivity());
        List<RecipeModel> recipes = recipe_lab.getRecipes();
        if(recipes != null) {
            mNullList.setVisibility(View.GONE);
            mBtnAddRecipe.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = new RecipeAdapter(recipes);
                mRecyclerView.setAdapter(mAdapter);

            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
        else
        {

        }
    }

    public class RecipeHolder extends RecyclerView.ViewHolder{

        RecipeModel mRecipeModel;
        TextView mRecipeNameTV,mRecipeDate;
         ImageButton mimgbtndelete;

        public RecipeHolder(View itemView) {
            super(itemView);

mRecipeNameTV = (TextView)itemView.findViewById(R.id.txtRecipeName);
            mRecipeDate = (TextView)itemView.findViewById(R.id.txtLastModifyDate);
mimgbtndelete = (ImageButton)itemView.findViewById(R.id.deleteimgbtn);

        }

        public void bindClass(RecipeModel recipe) {
            mRecipeModel = recipe;
            mRecipeNameTV.setText(mRecipeModel.getRecipeName());
            mRecipeDate.setText("Date");
        }
    }

    public class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {

        private List<RecipeModel> mRecipes;

        public RecipeAdapter(List<RecipeModel> recipes) {
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

            RecipeModel recipe_model = mRecipes.get(position);
            holder.bindClass(recipe_model);
        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

      if(requestCode == REQUEST_RECIPE_NAME && resultCode == Activity.RESULT_OK){
          String name = data.getStringExtra(RecipeNameDialog.RECIPE_NAME).toString();
          Intent i = new Intent(getActivity(),AddRecipeActivity.class);
          i.putExtra(REC_NAME,name);
          startActivityForResult(i,REQUEST_ADD_RECIPE);
       }

      else if(requestCode == REQUEST_ADD_RECIPE && resultCode == Activity.RESULT_OK){

          Toast.makeText(getActivity(),"Recipe Added",Toast.LENGTH_SHORT).show();
      }
        else{
          Toast.makeText(getActivity(),"wrong",Toast.LENGTH_SHORT).show();
      }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_recipe,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_add_recipe: {
                FragmentManager manager = getFragmentManager();
                RecipeNameDialog dialog = new RecipeNameDialog();
                dialog.setTargetFragment(RecipeListFragment.this,REQUEST_RECIPE_NAME);

                dialog.show(manager,DIALOG_RECIPE_NAME);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }    }
}

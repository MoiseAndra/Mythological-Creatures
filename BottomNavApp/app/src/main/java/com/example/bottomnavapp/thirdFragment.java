package com.example.bottomnavapp;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class thirdFragment extends Fragment implements RecycleViewInterface{


    ArrayList<CreaturesModel> creaturesModels = new ArrayList<>();
    CreaturesAdapter adapter;

    public thirdFragment() {
    }


    public static thirdFragment newInstance(String param1, String param2) {
        thirdFragment fragment = new thirdFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpCreatureModels();

        adapter = new CreaturesAdapter(getContext(), creaturesModels, this::onItemClick);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.mRecycleView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        SearchView searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchStr = newText;
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }

    @Override
    public void onItemClick(CreaturesModel creaturesModel) {
        Fragment fragment = null;
        System.out.println("Butonul apasat este: " + creaturesModel.getCretName());
        String cretName = creaturesModel.getCretName();
        fragment = new LibFragment(cretName);
        replaceFragment(fragment);
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.ThirdFrag, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setUpCreatureModels()
    {

        InputStream is = requireContext().getResources().openRawResource(R.raw.labels);
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                creaturesModels.add(new CreaturesModel(line));

            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

}
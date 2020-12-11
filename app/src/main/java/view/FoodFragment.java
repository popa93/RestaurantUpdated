package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;

import java.util.ArrayList;

import model.Pizza;
import model.RecyclerAdapter;
import viewmodel.FoodViewModel;


public class FoodFragment extends Fragment {

    FoodViewModel foodViewModel;
    private ArrayList<Pizza> pizzaList = new ArrayList<>();


    public FoodFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_food, container, false);

        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        observeViewModel((RecyclerView) view);
        foodViewModel.refresh();

    }


    private void observeViewModel(RecyclerView recyclerView) {

        foodViewModel.pizzaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<Pizza>>() {
            @Override
            public void onChanged(ArrayList<Pizza> pizzas) {
                pizzaList = pizzas;
                setupRecyclerView(recyclerView);// loading bar?

            }
        });


    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(toString(), pizzaList);
        recyclerView.setAdapter(recyclerAdapter);
    }


    @Override
    public String toString() {
        return "Food";
    }
}
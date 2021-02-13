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

import Util.Constants;
import model.Drink;
import model.RecyclerAdapter;
import viewmodel.DrinkViewModel;


public class DrinkFragment extends Fragment {

    private ArrayList<Drink> drinkList = new ArrayList<>();
    DrinkViewModel drinkViewModel;

    public DrinkFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_drink, container, false);

        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        drinkViewModel = ViewModelProviders.of(this).get(DrinkViewModel.class);
        observeViewModel((RecyclerView) view);
        drinkViewModel.refresh();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(toString(), drinkList, "");
        recyclerView.setAdapter(recyclerAdapter);

    }

    private void observeViewModel(RecyclerView recyclerView) {

        drinkViewModel.drinksMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<Drink>>() {
            @Override
            public void onChanged(ArrayList<Drink> drinks) {
                drinkList = drinks;
                setupRecyclerView(recyclerView);

            }
        });

    }

    @Override
    public String toString() {
        return Constants.DRINK;
    }

}

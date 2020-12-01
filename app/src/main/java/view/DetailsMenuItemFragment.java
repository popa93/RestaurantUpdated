package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.restaurantupdated.R;

import model.Drink;
import model.Pizza;


public class DetailsMenuItemFragment extends Fragment {


    public DetailsMenuItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_details_menu_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Pizza pizza = (Pizza) getArguments().getSerializable("cheie");
        Drink drink = (Drink) getArguments().getSerializable("cheieDrink");


        if (pizza != null)
            Toast.makeText(view.getContext(), pizza.getName(), Toast.LENGTH_LONG).show();
        if (drink != null)
            Toast.makeText(view.getContext(), drink.getName(), Toast.LENGTH_LONG).show();
    }
}
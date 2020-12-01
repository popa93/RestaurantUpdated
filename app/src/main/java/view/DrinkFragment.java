package view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;

import java.util.ArrayList;

import model.Drink;
import model.IMenuClickListener;
import model.Pizza;
import model.RecyclerAdapter;
import viewmodel.DrinkViewModel;


public class DrinkFragment extends Fragment {

    private ArrayList<Drink> drinkList = new ArrayList<>();
    DrinkViewModel drinkViewModel;

    public DrinkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("drink", "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("drink", "onCreateView");
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_drink, container, false);

        // setupRecyclerView(recyclerView);
        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("drink", "onViewCreated");
        drinkViewModel = ViewModelProviders.of(this).get(DrinkViewModel.class);
        observeViewModel((RecyclerView) view);
        drinkViewModel.refresh();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // RecyclerAdapter recyclerAdapter=new RecyclerAdapter(pizzaImages,pizzaNames,toString(),pizzaPrices);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(toString(), drinkList, "");
        recyclerView.setAdapter(recyclerAdapter);


        /*StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference dataReference=storageReference.child("funghi.jpg");
        dataReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("link",uri.toString());
            }
        });*/


        recyclerAdapter.setClickListener(new IMenuClickListener() {
            @Override
            public void onItemClick(Pizza item) {
                // jumpToDetailItem(item);

                //Log.e("click","click pe buton");


            }

            @Override
            public void onItemClick(Drink item) {
                jumpToDetailItem(item);

            }


            @Override
            public void addButtonClick(int position) {
                // OrderActivity.orderList.add(pizzaList.get(position));
                //Toast.makeText(getActivity(), Pizza.pizzas[position].getName()+" added to cart", Toast.LENGTH_SHORT).show();
                Log.e("pizza", drinkList.get(position).getName());
            }

        });


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

    private void jumpToDetailItem(Drink item) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("cheieDrink", item);
        // NavDirections action=MenuFragmentDirections.actionMenuFragmentToDetailsMenuItemFragment4();
        Navigation.findNavController(getView()).navigate(R.id.action_menuFragment_to_detailsMenuItemFragment4, bundle);

    }

    @Override
    public String toString() {
        return "Drink";
    }

}

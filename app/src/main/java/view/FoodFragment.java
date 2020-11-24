package view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.restaurantupdated.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import model.IMenuClickListener;
import model.Pizza;
import model.RecyclerAdapter;
import viewmodel.FoodViewModel;
import viewmodel.LoginViewModel;


public class FoodFragment extends Fragment {

    FoodViewModel foodViewModel;
    private ArrayList<Pizza> pizzaList=new ArrayList<>();


    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("food","sunt in onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("food","sunt in onCreateView");
        RecyclerView recyclerView=(RecyclerView) inflater.inflate(R.layout.fragment_food, container, false);


        return recyclerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("food","sunt in onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        observeViewModel((RecyclerView) view);
        foodViewModel.refresh();

    }


    private void observeViewModel(RecyclerView recyclerView){

        foodViewModel.pizzaMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ArrayList<Pizza>>() {
            @Override
            public void onChanged(ArrayList<Pizza> pizzas) {
                Log.e("lista",pizzas.get(1).getName());
                pizzaList=pizzas;
                setupRecyclerView(recyclerView);//pun un loading bar?

            }
        });




    }

    private void setupRecyclerView(RecyclerView recyclerView){



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // RecyclerAdapter recyclerAdapter=new RecyclerAdapter(pizzaImages,pizzaNames,toString(),pizzaPrices);
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(toString(),pizzaList);
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
                jumpToDetailItem(item);
                //Log.e("click","click pe buton");



            }


            @Override
            public void addButtonClick(int position) {
               // OrderActivity.orderList.add(pizzaList.get(position));
                //Toast.makeText(getActivity(), Pizza.pizzas[position].getName()+" added to cart", Toast.LENGTH_SHORT).show();
                Log.e("pizza",pizzaList.get(position).getName());
            }

        });


    }

    private void jumpToDetailItem(Pizza item){

        Bundle bundle=new Bundle();
        bundle.putSerializable("cheie",item);
       // NavDirections action=MenuFragmentDirections.actionMenuFragmentToDetailsMenuItemFragment4();
        Navigation.findNavController(getView()).navigate(R.id.action_menuFragment_to_detailsMenuItemFragment4,bundle);

    }

    @Override
    public String toString() {
        return "Food";
    }
}
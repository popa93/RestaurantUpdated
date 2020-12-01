package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.restaurantupdated.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Drink;
import model.Pizza;


public class DetailsMenuItemFragment extends Fragment {


    private Pizza pizza;
    private Drink drink;


    @BindView(R.id.nameOfItem)
    TextView nameOfItem;

    @BindView(R.id.quantityOfItem)
    TextView quantityOfItem;

    @BindView(R.id.priceOfItem)
    TextView priceOfItem;

    @BindView(R.id.ingredientsOfItem)
    TextView ingredientsOfItem;

    @BindView(R.id.imageOfItem)
    ImageView imageOfItem;

    @BindView(R.id.FBadProduct)
    FloatingActionButton floatingActionButton;

    public DetailsMenuItemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Details");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_details_menu_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);



        pizza = (Pizza) getArguments().getSerializable("cheie");
        drink = (Drink) getArguments().getSerializable("cheieDrink");


        if (pizza != null)
            Toast.makeText(view.getContext(), pizza.getName(), Toast.LENGTH_LONG).show();
        if (drink != null)
            Toast.makeText(view.getContext(), drink.getName(), Toast.LENGTH_LONG).show();

        initViews(pizza,drink);
        floatingButtonAction();

    }



    private void floatingButtonAction(){
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pizza!=null) {

                    Toast.makeText(getActivity(),pizza.getName()+" was added to cart",Toast.LENGTH_SHORT).show();
                }
                if(drink!=null){

                    Toast.makeText(getActivity(),drink.getName()+" was added to cart",Toast.LENGTH_SHORT).show();

                    // OrderActivity.orderList.add(drink);
                }
            }
        });
    }



    private void initViews(Pizza pizza,Drink drink){

        if (pizza != null){

            nameOfItem.setText(pizza.getName());
            quantityOfItem.setText("123");
            priceOfItem.setText(pizza.getPrice().toString());
            ingredientsOfItem.setText(pizza.getIngredients());
            Glide.with(imageOfItem.getContext()).load(pizza.imageLink).error(R.mipmap.ic_launcher).into(imageOfItem);
        }

        if (drink != null){
            nameOfItem.setText(drink.getName());
            quantityOfItem.setText("123");
            priceOfItem.setText(drink.getPrice().toString());
            ingredientsOfItem.setText(drink.getIngredients());
            Glide.with(imageOfItem.getContext()).load(drink.imageLink).error(R.mipmap.ic_launcher).into(imageOfItem);
        }



    }

}
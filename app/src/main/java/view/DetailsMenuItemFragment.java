package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.FragmentDetailsMenuItemBinding;

import Util.Constants;
import model.Drink;
import model.Pizza;


public class
DetailsMenuItemFragment extends Fragment {

    private Pizza pizza;
    private Drink drink;
    private FragmentDetailsMenuItemBinding bindingView;

    public DetailsMenuItemFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(Constants.ACTION_BAR_DETAILS_TITLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentDetailsMenuItemBinding bindingView = DataBindingUtil.inflate(inflater, R.layout.fragment_details_menu_item, container, false);
        View view = bindingView.getRoot();
        this.bindingView = bindingView;
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Object o = getArguments().getSerializable(Constants.DETAILS_ITEM_KEY);

        if (o instanceof Pizza) {
            pizza = (Pizza) o;
        } else if (o instanceof Drink) {
            drink = (Drink) o;
        }

        if (pizza != null)
            Toast.makeText(view.getContext(), pizza.getName(), Toast.LENGTH_LONG).show();
        if (drink != null)
            Toast.makeText(view.getContext(), drink.getName(), Toast.LENGTH_LONG).show();

        initViews(pizza, drink);
        floatingButtonAction();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bindingView = null;
    }

    private void floatingButtonAction() {
        bindingView.FBadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pizza != null) {

                    OrderFragment.orderList.add(pizza);
                }
                if (drink != null) {

                    OrderFragment.orderList.add(drink);
                }
            }
        });
    }


    private void initViews(Pizza pizza, Drink drink) {

        if (pizza != null) {

            bindingView.setItem(pizza);
        }

        if (drink != null) {

            bindingView.quantityLabel.setText(Constants.QUANTITY);
            bindingView.setItem(drink);
        }


    }


}

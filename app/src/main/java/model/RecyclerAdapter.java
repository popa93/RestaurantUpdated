package model;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.RecyclerCardviewItemBinding;
import com.example.restaurantupdated.databinding.RecyclerOrderItemBinding;

import java.util.ArrayList;

import Util.Constants;
import ViewHolders.MenuViewHolder;
import ViewHolders.OrderViewHolder;
import view.OrderFragment;

public class RecyclerAdapter extends RecyclerView.Adapter {


    private String fragName;
    private IOrderClickListener iOrderClickListener;
    private ICheckedOrderListener checkedOrderListener;
    private IExtraInfoOrderClick extraInfoOrderClick;


    ArrayList<Pizza> pizzaList;
    ArrayList<Drink> drinkList;
    ArrayList<Object> orderList;
    ArrayList<Order> kitchenList;


    public RecyclerAdapter(String fragName, ArrayList<Pizza> pizzaList) {
        this.fragName = fragName;
        this.pizzaList = pizzaList;
    }

    public RecyclerAdapter(String fragName, ArrayList<Drink> drinkList, String troll) {
        this.fragName = fragName;
        this.drinkList = drinkList;

    }

    public RecyclerAdapter(String fragName, ArrayList<Object> orderList, String troll, String troll2) {
        this.fragName = fragName;
        this.orderList = orderList;

    }

    public RecyclerAdapter(String fragName, ArrayList<Order> kitchenList, boolean troll) {
        this.fragName = fragName;
        this.kitchenList = kitchenList;

    }


    public void setOrderClickListener(IOrderClickListener iOrderClickListener) {
        this.iOrderClickListener = iOrderClickListener;

    }

    public void setCheckedOrderListener(ICheckedOrderListener checkedOrderListener) {
        this.checkedOrderListener = checkedOrderListener;
    }

    public void setExtraInfoOrderClick(IExtraInfoOrderClick extraInfoOrderClick) {
        this.extraInfoOrderClick = extraInfoOrderClick;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (fragName.equals(Constants.FOOD) || fragName.equals(Constants.DRINK)) {

            RecyclerCardviewItemBinding view = DataBindingUtil.inflate(inflater, R.layout.recycler_cardview_item, parent, false);
            return new MenuViewHolder(view, fragName);

        } else if (fragName.equals(Constants.KITCHEN)) {

            RecyclerOrderItemBinding view = DataBindingUtil.inflate(inflater, R.layout.recycler_order_item, parent, false);
            return new OrderViewHolder(view, checkedOrderListener, extraInfoOrderClick);

        } else {

            RecyclerOrderItemBinding view = DataBindingUtil.inflate(inflater, R.layout.recycler_order_item, parent, false);
            return new OrderViewHolder(view, iOrderClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {      //adds the data to the card views


        if (fragName.equals(Constants.FOOD)) {
            ((MenuViewHolder) holder).bind(pizzaList.get(position));

        } else if (fragName.equals(Constants.DRINK)) {
            ((MenuViewHolder) holder).bind(drinkList.get(position));

        } else if (fragName.equals(Constants.ORDER)) {

            ((OrderViewHolder) holder).bind(orderList.get(position));
        } else if (fragName.equals(Constants.KITCHEN)) {
            ((OrderViewHolder) holder).bind(kitchenList.get(position));
        }

    }

    @Override
    public int getItemCount() {

        if (fragName.equals(Constants.FOOD)) {
            return pizzaList.size();
        } else if (fragName.equals(Constants.DRINK)) {
            return drinkList.size();
        } else if (fragName.equals(Constants.KITCHEN)) {
            return kitchenList.size();
        } else {
            return OrderFragment.orderList.size();
        }

    }


}

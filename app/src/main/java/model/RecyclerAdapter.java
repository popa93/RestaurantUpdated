package model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;

import java.util.ArrayList;

import ViewHolders.MenuViewHolder;
import ViewHolders.OrderViewHolder;
import view.OrderFragment;

public class RecyclerAdapter extends RecyclerView.Adapter {


    private String fragName;
    private IMenuClickListener clickListener;
    private IOrderClickListener iOrderClickListener;


    ArrayList<Pizza> pizzaList;
    ArrayList<Drink> drinkList;
    ArrayList<Object> orderList;


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

    public void setClickListener(IMenuClickListener clickListener) {

        this.clickListener = clickListener;

    }

    public void setOrderClickListener(IOrderClickListener iOrderClickListener) {
        this.iOrderClickListener = iOrderClickListener;

    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (fragName.equals("Food") || fragName.equals("Drink")) {
            CardView myCardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cardview_item, parent, false);
            return new MenuViewHolder(myCardView, fragName, clickListener);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_item, parent, false);
            return new OrderViewHolder(view, iOrderClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {      //adds the data to the card views


        if (fragName.equals("Food")) {
            ((MenuViewHolder) holder).bind(pizzaList.get(position));

        } else if (fragName.equals("Drink")) {
            ((MenuViewHolder) holder).bind(drinkList.get(position));

        } else if (fragName.equals("Order")) {

            ((OrderViewHolder) holder).bind(orderList.get(position));
        }

    }

    @Override
    public int getItemCount() {

        if (fragName.equals("Food")) {
            return pizzaList.size();
        } else if (fragName.equals("Drink")) {
            return drinkList.size();
        } else {
            return OrderFragment.orderList.size();
        }

    }


}

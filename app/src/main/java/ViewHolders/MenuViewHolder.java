package ViewHolders;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.RecyclerCardviewItemBinding;

import java.io.Serializable;

import model.Drink;
import model.IItemClickListener;
import model.Pizza;
import view.OrderFragment;

public
class MenuViewHolder extends RecyclerView.ViewHolder implements IItemClickListener {

    String fragName;
    RecyclerCardviewItemBinding view;
    Pizza pizza;
    Drink drink;
    Object currentItem;


    public MenuViewHolder(@NonNull RecyclerCardviewItemBinding itemView, String fragName) {
        super(itemView.getRoot());
        view = itemView;
        this.fragName = fragName;

    }

    public void bind(Object item) {

        currentItem = item;

        if (item instanceof Pizza) {

            pizza = (Pizza) item;
            view.setItem(pizza);
            view.executePendingBindings();

        } else {

            drink = (Drink) item;
            view.setItem(drink);
            view.executePendingBindings();
        }

        view.setListener(this);

    }

    @Override
    public void onItemClicked(View v) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("da", (Serializable) currentItem);
        Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_detailsMenuItemFragment4, bundle);

    }

    @Override
    public void onAddToCartClick(View v) {
        OrderFragment.orderList.add(currentItem);
    }


}

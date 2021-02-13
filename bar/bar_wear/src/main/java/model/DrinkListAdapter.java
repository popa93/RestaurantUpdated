package model;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bar_wear.R;
import com.example.bar_wear.databinding.DrinkOrderListItemBinding;

import java.util.ArrayList;

public
class DrinkListAdapter extends RecyclerView.Adapter<DrinkListAdapter.DrinkViewHolder> {

    ArrayList<Order> drinkOrderList = new ArrayList<>();

    public DrinkListAdapter(ArrayList<Order> drinkOrderList) {
        this.drinkOrderList = drinkOrderList;
    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        DrinkOrderListItemBinding view = DataBindingUtil.inflate(inflater, R.layout.drink_order_list_item, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_order_list_item, parent, false);
        return new DrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        holder.bind(drinkOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        return drinkOrderList.size();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder {

        DrinkOrderListItemBinding binding;

        public DrinkViewHolder(@NonNull DrinkOrderListItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Order item) {
            binding.setDrinkItem(item);
        }
    }
}

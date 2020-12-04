package ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;

import model.Drink;
import model.IOrderClickListener;
import model.Pizza;

public
class OrderViewHolder extends RecyclerView.ViewHolder {

    View view;
    ImageView deleteItem;
    IOrderClickListener iOrderClickListener;


    public OrderViewHolder(@NonNull View itemView, IOrderClickListener iOrderClickListener) {
        super(itemView);
        this.iOrderClickListener = iOrderClickListener;
        this.view = itemView;
        deleteItem = view.findViewById(R.id.imageView);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                iOrderClickListener.onDeleteClick(position);
            }
        });
    }

    public void bind(Object item) {

        if (item instanceof Pizza) {

            Pizza pizza = (Pizza) item;

            TextView textView = view.findViewById(R.id.textView);
            textView.setText(pizza.getName());

        } else {
            Drink drink = (Drink) item;

            TextView textView = view.findViewById(R.id.textView);
            textView.setText(drink.getName());

        }


    }


}

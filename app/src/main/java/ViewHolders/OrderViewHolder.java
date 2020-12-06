package ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Drink;
import model.IOrderClickListener;
import model.Pizza;

public
class OrderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView)
    ImageView deleteItem;

    @BindView(R.id.textView)
    TextView nameOfItem;

    IOrderClickListener iOrderClickListener;


    public OrderViewHolder(@NonNull View itemView, IOrderClickListener iOrderClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.iOrderClickListener = iOrderClickListener;
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

            nameOfItem.setText(pizza.getName());

        } else {
            Drink drink = (Drink) item;

            nameOfItem.setText(drink.getName());

        }


    }


}

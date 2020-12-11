package ViewHolders;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.RecyclerOrderItemBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Drink;
import model.IOrderClickListener;
import model.Pizza;

public
class OrderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView)
    ImageView deleteItem;

    IOrderClickListener iOrderClickListener;
    RecyclerOrderItemBinding itemBinding;


    public OrderViewHolder(RecyclerOrderItemBinding itemView, IOrderClickListener iOrderClickListener) {
        super(itemView.getRoot());
        ButterKnife.bind(this, itemView.getRoot());
        itemBinding = itemView;
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
            itemBinding.setOrderItem(pizza);

        } else {

            Drink drink = (Drink) item;
            itemBinding.setOrderItem(drink);

        }


    }


}

package ViewHolders;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.RecyclerOrderItemBinding;

import Util.Constants;
import model.Drink;
import model.ICheckedOrderListener;
import model.IExtraInfoOrderClick;
import model.IOrderClickListener;
import model.Order;
import model.Pizza;

public
class OrderViewHolder extends RecyclerView.ViewHolder {

    IOrderClickListener iOrderClickListener;
    IExtraInfoOrderClick extraInfoOrderClick;
    RecyclerOrderItemBinding itemBinding;

    public OrderViewHolder(RecyclerOrderItemBinding itemView, ICheckedOrderListener checkedOrderListener, IExtraInfoOrderClick extraInfoOrderClick) {
        super(itemView.getRoot());
        itemBinding = itemView;
        this.extraInfoOrderClick = extraInfoOrderClick;
        itemBinding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                itemBinding.imageView.setImageResource(R.drawable.ic_checked);
                //itemBinding.imageView.setOnClickListener(null);
                checkedOrderListener.onClickCheckOrder(getAdapterPosition());
            }
        });

        itemBinding.extraInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extraInfoOrderClick.onClickViewExtraInfo(getAdapterPosition());
            }
        });

    }


    public OrderViewHolder(RecyclerOrderItemBinding itemView, IOrderClickListener iOrderClickListener) {
        super(itemView.getRoot());
        itemBinding = itemView;
        this.iOrderClickListener = iOrderClickListener;
        itemBinding.imageView.setOnClickListener(new View.OnClickListener() {
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

        } else if (item instanceof Order) {
            itemBinding.textView.setVisibility(View.INVISIBLE);
            itemBinding.textView2.setVisibility(View.VISIBLE);
            // String output=(String)item;
            itemBinding.setKitchenOrder((Order) item);
            itemBinding.imageView.setImageResource(R.drawable.ic_check_order);
            if (((Order) item).getOrderStatus().equals(Constants.TRUE)) {
                itemBinding.imageView.setImageResource(R.drawable.ic_checked);
            }
            if (!(((Order) item).getRemarks().isEmpty())) {
                itemBinding.extraInfo.setVisibility(View.VISIBLE);
                itemBinding.extraInfo.setImageResource(R.drawable.extra_order_info);
            } else {
                itemBinding.extraInfo.setVisibility(View.INVISIBLE);
            }
        } else {
            Drink drink = (Drink) item;
            itemBinding.setOrderItem(drink);

        }

    }

}
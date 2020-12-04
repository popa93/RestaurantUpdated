package ViewHolders;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantupdated.R;

import model.Drink;
import model.IMenuClickListener;
import model.Pizza;

public
class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private IMenuClickListener clickListener;
    CardView cardView;
    Button button;
    String fragName;


    public MenuViewHolder(@NonNull CardView itemView, String fragName, IMenuClickListener iMenuClickListener) {
        super(itemView);
        cardView = itemView;
        button = itemView.findViewById(R.id.toCartButton);
        this.fragName = fragName;
        this.clickListener = iMenuClickListener;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                clickListener.addButtonClick(position);
            }
        });
    }

    public void bind(Object item) {

        if (item instanceof Pizza) {

            Pizza pizza = (Pizza) item;

            ImageView imageView = cardView.findViewById(R.id.info_image);
            Glide.with(imageView.getContext()).load(pizza.getImageLink()).error(R.mipmap.ic_launcher).into(imageView);

            cardView.setTag(pizza);

            TextView textView = cardView.findViewById(R.id.productName);
            textView.setText(pizza.getName());

            TextView textView1 = cardView.findViewById(R.id.productPrice);
            textView1.setText(pizza.getPrice().toString());


            cardView.setOnClickListener(this);

        } else {
            Drink drink = (Drink) item;


            ImageView imageView = cardView.findViewById(R.id.info_image);
            Glide.with(imageView.getContext()).load(drink.getImageLink()).error(R.mipmap.ic_launcher).into(imageView);

            cardView.setTag(drink);

            TextView textView = cardView.findViewById(R.id.productName);
            textView.setText(drink.getName());

            TextView textView1 = cardView.findViewById(R.id.productPrice);
            textView1.setText(drink.getPrice().toString());

            cardView.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View view) {

        if (fragName.equals("Food"))
            clickListener.onItemClick((Pizza) view.getTag());
        else
            clickListener.onItemClick((Drink) view.getTag());
        Log.e("tag", "sunt in clasa MyViewHolder onClick");

    }


}

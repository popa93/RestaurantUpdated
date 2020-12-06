package ViewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantupdated.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Drink;
import model.IMenuClickListener;
import model.Pizza;

public
class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    @BindView(R.id.toCartButton)
    Button button;

    @BindView(R.id.info_image)
    ImageView infoImage;

    @BindView(R.id.productName)
    TextView productName;

    @BindView(R.id.productPrice)
    TextView productPrice;

    private IMenuClickListener clickListener;
    private CardView cardView;
    private String fragName;


    public MenuViewHolder(@NonNull CardView itemView, String fragName, IMenuClickListener iMenuClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        cardView = itemView;
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

            Glide.with(infoImage.getContext()).load(pizza.getImageLink()).error(R.mipmap.ic_launcher).into(infoImage);
            cardView.setTag(pizza);
            productName.setText(pizza.getName());
            productPrice.setText(pizza.getPrice().toString());

            cardView.setOnClickListener(this);

        } else {
            Drink drink = (Drink) item;

            Glide.with(infoImage.getContext()).load(drink.getImageLink()).error(R.mipmap.ic_launcher).into(infoImage);
            cardView.setTag(drink);
            productName.setText(drink.getName());
            productPrice.setText(drink.getPrice().toString());

            cardView.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View view) {

        if (fragName.equals("Food"))
            clickListener.onItemClick((Pizza) view.getTag());
        else
            clickListener.onItemClick((Drink) view.getTag());

    }


}

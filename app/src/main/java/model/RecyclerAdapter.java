package model;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restaurantupdated.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private int[] imageIds;
    private String[] captions;
    private String fragName;
    private String[] prices;
    private IMenuClickListener clickListener;


    ArrayList<Pizza> pizzaList;
    // ArrayList<Drink> drinkList;

    // private List<Drink> drinkList;

   /* public RecyclerAdapter(int[] imageID,String[] captions,String fragName,String[] prices) {
        this.imageIds=imageID;
        this.captions=captions;
        this.fragName=fragName;
        this.prices=prices;
    }*/

    public RecyclerAdapter(String fragName, ArrayList<Pizza> pizzaList) {
        this.fragName = fragName;
        this.pizzaList = pizzaList;
    }

   /* public RecyclerAdapter(String fragName, ArrayList<Drink> drinkList,String troll) {
        this.fragName = fragName;
        this.drinkList = drinkList;
    }*/

    public void setClickListener(IMenuClickListener clickListener) {

        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   //instantiates viewHolder that contains cardViews
        CardView myCardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cardview_item, parent, false);
        Log.e("tag", "sunt in onCreateViewHolder");
        return new MyViewHolder(myCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {      //adds the data to the card views

        Log.e("tag", "sunt in bind");

        if (fragName.equals("Food")) {
            CardView cardView = holder.cardView;

            ImageView imageView = cardView.findViewById(R.id.info_image);
            Glide.with(imageView.getContext()).load(pizzaList.get(position).getImageLink()).error(R.mipmap.ic_launcher).into(imageView);

            holder.cardView.setTag(pizzaList.get(position));

            TextView textView = cardView.findViewById(R.id.productName);
            textView.setText(pizzaList.get(position).getName());

            TextView textView1 = cardView.findViewById(R.id.productPrice);
            textView1.setText(pizzaList.get(position).getPrice().toString());

            holder.cardView.setOnClickListener(holder);

        } else {
           /* CardView cardView = holder.cardView;
            ImageView imageView = cardView.findViewById(R.id.info_image);
            Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), drinkList.get(position).getImageResourceId());
            imageView.setImageDrawable(drawable);
            imageView.setContentDescription(drinkList.get(position).getName());

            holder.cardView.setTag(Drink.drinks[position]);

            TextView textView = cardView.findViewById(R.id.productName);
            textView.setText(drinkList.get(position).getName());

            TextView textView1 = cardView.findViewById(R.id.producPrice);
            textView1.setText(drinkList.get(position).getPrice());
*/
            holder.cardView.setOnClickListener(holder);
        }
    }

    @Override
    public int getItemCount() { //total number of items from the list. va trb sa calculez cate iteme sunt in total la food si drink si apoi
        //sa trimit aici numarul lor
        if (fragName.equals("Food")) {
            return pizzaList.size();
        }/*else if (fragName.equals("Drink")){
            return Drink.drinks.length;   //aici ar veni echivalentul pentru drinks
        }*/
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;
        private Button button;

        public MyViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
            button = itemView.findViewById(R.id.toCartButton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    clickListener.addButtonClick(position);
                }
            });
        }

        @Override
        public void onClick(View view) {

            if (fragName.equals("Food"))
                clickListener.onItemClick((Pizza) view.getTag());
            /*else
                clickListener.onItemClick((Drink)view.getTag());*/
            Log.e("tag", "sunt in clasa MyViewHolder onClick");

        }


    }


}

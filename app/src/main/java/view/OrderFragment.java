package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.Drink;
import model.IOrderClickListener;
import model.Pizza;
import model.RecyclerAdapter;


public class OrderFragment extends Fragment {

    public static ArrayList<Object> orderList = new ArrayList<>();

    @BindView(R.id.totalPrice)
    TextView totalPrice;

    public OrderFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        RecyclerView recyclerViewOrder = view.findViewById(R.id.orderRecycler);

        recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final RecyclerAdapter recyclerOrderAdapter = new RecyclerAdapter(toString(), orderList, null, null);
        recyclerViewOrder.setAdapter(recyclerOrderAdapter);
        totalPrice.setText(setTotalPrice() + " lei");
        recyclerOrderAdapter.setOrderClickListener(new IOrderClickListener() {
            @Override
            public void onDeleteClick(int position) {
                orderList.remove(position);
                recyclerOrderAdapter.notifyDataSetChanged();
                totalPrice.setText(setTotalPrice() + " lei");
            }
        });

        return view;
    }

    private String setTotalPrice() {

        long totalMoney = 0;
        for (Object item : orderList) {
            if (item instanceof Pizza) {
                totalMoney += ((Pizza) item).getPrice();
            } else {

                totalMoney += ((Drink) item).getPrice();
            }


        }

        return String.valueOf(totalMoney);
    }


    @NonNull
    @Override
    public String toString() {
        return "Order";
    }
}
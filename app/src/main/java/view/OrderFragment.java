package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;

import java.util.ArrayList;

import Util.Constants;
import Util.NotificationsHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.Drink;
import model.IOrderClickListener;
import model.Pizza;
import model.RecyclerAdapter;
import viewmodel.OrderViewModel;


public class OrderFragment extends Fragment {

    OrderViewModel orderViewModel;
    NotificationsHelper notificationsHelper;
    public static ArrayList<Object> orderList = new ArrayList<>();

    @BindView(R.id.totalPrice)
    TextView totalPrice;

    @BindView(R.id.place_order)
    Button placeOrderButton;

    @BindView(R.id.orderRecycler)
    RecyclerView recyclerViewOrder;

    public OrderFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(Constants.ACTION_BAR_ORDER_TITLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);

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
        observeViewModel();
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderViewModel.placeOrder();
            }
        });

        return view;
    }

    String waitTime() {

        int totalItems = orderList.size();
        if (orderList.size() == 0)
            return Constants.ZERO;

        if (totalItems <= 2)
            return Constants.FOUR;
        else if (totalItems > 2 && totalItems <= 4)
            return Constants.EIGHT;
        else if (totalItems > 4 && totalItems <= 8)
            return Constants.THIRTEEN;
        else
            return Constants.EIGHTEEN;

    }

    private void observeViewModel() {

        orderViewModel.orderLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(Constants.OK2)) {
                    notificationsHelper = new NotificationsHelper(getContext(), waitTime());
                    notificationsHelper.createNotification();
                } else {
                    Toast.makeText(getActivity(), Constants.ERROR_MSG, Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        return Constants.ORDER;
    }
}
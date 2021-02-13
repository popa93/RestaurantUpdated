package view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.restaurantupdated.databinding.FragmentOrderBinding;

import java.util.ArrayList;

import Util.Constants;
import Util.NotificationsHelper;
import Util.SharedPreferencesHelper;
import model.Drink;
import model.IOrderClickListener;
import model.Pizza;
import model.RecyclerAdapter;
import viewmodel.OrderViewModel;


public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;

    OrderViewModel orderViewModel;
    NotificationsHelper notificationsHelper;
    public static ArrayList<Object> orderList = new ArrayList<>();

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

        binding = FragmentOrderBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.orderRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final RecyclerAdapter recyclerOrderAdapter = new RecyclerAdapter(toString(), orderList, null, null);
        binding.orderRecycler.setAdapter(recyclerOrderAdapter);
        binding.totalPrice.setText(setTotalPrice() + Constants.LEI);
        recyclerOrderAdapter.setOrderClickListener(new IOrderClickListener() {
            @Override
            public void onDeleteClick(int position) {
                orderList.remove(position);
                recyclerOrderAdapter.notifyDataSetChanged();
                binding.totalPrice.setText(setTotalPrice() + Constants.LEI);
            }
        });

        binding.placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrderAndNotification();
            }
        });

        observeLiveData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) { //completeaza cazurile aici
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void placeOrderAndNotification() {  //completeaza ca sa nu trb sa apas iar,sa i ua automat dupa ce dau accept

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
        SharedPreferencesHelper.getInstance(getContext()).setCurrentDeviceEmui(telephonyManager.getDeviceId());

        orderViewModel.placeOrder(telephonyManager.getDeviceId(), binding.clientRemarks.getText().toString());
    }

    private void observeLiveData() {
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
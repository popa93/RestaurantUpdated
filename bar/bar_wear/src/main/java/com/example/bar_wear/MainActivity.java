package com.example.bar_wear;

import android.os.Build;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;

import com.example.bar_wear.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Util.Constants;
import Util.SharedPreferencesHelper;
import model.DrinkListAdapter;
import model.Order;

//do not manually delete node from db. it will break shared preference counters logic(except at the begining of the day)

public class MainActivity extends WearableActivity { //must also show the table no on the screen(drink order list).take into account the emui(see kitchen)

    ActivityMainBinding binding;

    ArrayList<Order> myList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference(Constants.ORDERS).child(Constants.DEVICE_EMUI).child(getMainDate()); //set smartwatch emulator`s date manually(emulat. error)
    private boolean call = false;
    private int countCalls = 0;
    boolean test = false;
    DrinkListAdapter drinkListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SharedPreferencesHelper.getInstance(this).getCurrentDate().equals(getMainDate())) { //set current date
            SharedPreferencesHelper.getInstance(this).setCurrentDate(getMainDate());
            SharedPreferencesHelper.getInstance(this).setStatusModifCounter(0);                 //set to 0 nr of status updates
        }

        // Enables Always-on
        setAmbientEnabled();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.recyclerLauncherView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        drinkListAdapter = new DrinkListAdapter(myList);
        binding.recyclerLauncherView.setAdapter(drinkListAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                myList.remove(viewHolder.getAdapterPosition());
                drinkListAdapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(binding.recyclerLauncherView);

        binding.recyclerLauncherView.setEdgeItemsCenteringEnabled(true);
        binding.recyclerLauncherView.setLayoutManager(new WearableLinearLayoutManager(this));
        setContentView(view);


        dbRef.addValueEventListener(new ValueEventListener() { //every emui has a listener and all orders are added to same list(myList)

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Order r;
                SharedPreferencesHelper.getInstance(getApplicationContext()).setGlobalStatusCoutner(0);    //calculates if any new status update was made since last order
                for (DataSnapshot or : snapshot.getChildren()) {
                    r = or.getValue(Order.class);
                    if (!r.getOrderStatus().equals(Constants.FALSE)) {
                        int number = SharedPreferencesHelper.getInstance(getApplicationContext()).getGlobalStatusCounter();
                        ++number;
                        SharedPreferencesHelper.getInstance(getApplicationContext()).setGlobalStatusCoutner(number);
                    }

                    if (SharedPreferencesHelper.getInstance(getApplicationContext()).getGlobalStatusCounter() > SharedPreferencesHelper.getInstance(getApplicationContext()).getStatusModifCounter()) { //if new status update, set call to true and do not add again somethin to list(only update done not order placed)
                        int number = SharedPreferencesHelper.getInstance(getApplicationContext()).getGlobalStatusCounter();
                        SharedPreferencesHelper.getInstance(getApplicationContext()).setStatusModifCounter(number);
                        call = true;
                    }

                }


                if (!call) {
                    if (countCalls < snapshot.getChildrenCount()) {
                        countCalls = Math.toIntExact(snapshot.getChildrenCount());
                    }

                    if (!test) {
                        if (countCalls == snapshot.getChildrenCount()) {
                            test = true;
                        }
                        return;
                    } else {

                        DataSnapshot order = null;

                        for (DataSnapshot or : snapshot.getChildren()) {
                            order = or;
                        }


                        Order o = order.getValue(Order.class);

                        if (!o.getDrink().isEmpty())
                            myList.add(o);
                        drinkListAdapter.notifyDataSetChanged();
                    }

                } else {
                    call = false;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onStop() { //resets for next time calculations regarding status order updates
        super.onStop();
        SharedPreferencesHelper.getInstance(this).setStatusModifCounter(SharedPreferencesHelper.getInstance(getApplicationContext()).getGlobalStatusCounter());
        SharedPreferencesHelper.getInstance(getApplicationContext()).setGlobalStatusCoutner(0);


    }


    private String getMainDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DMY);
        return dateFormat.format(new Date());
    }
}
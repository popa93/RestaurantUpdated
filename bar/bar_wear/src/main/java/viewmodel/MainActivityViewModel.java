package viewmodel;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Order;

public
class MainActivityViewModel extends AndroidViewModel {


    private final static String TAG = MainActivityViewModel.class.getSimpleName();

    //public MutableLiveData<Order> clientOrderList = new MutableLiveData<>();
    private int countCalls;
    String specificEmui;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference("Orders").child("868757046395816").child(getMainDate());
    DatabaseReference specificChildReference;
    boolean writeCall = false;

    ArrayList<DataSnapshot> myList = new ArrayList<>();

    public MainActivityViewModel(@NonNull Application application) {

        super(application);


    }

    public void deleteOrderFromList(int position) {
        myList.remove(position);
    }

    public void fetchClientOrderList() {


        dbRef.addValueEventListener(new ValueEventListener() { //every emui has a listener and all orders are added to same list(myList)

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //if (!writeCall) {
                    //if (countCalls < snapshot.getChildrenCount()) {
                   //     countCalls = Math.toIntExact(snapshot.getChildrenCount());
                   // }

                   // if (!SharedPreferencesHelper.getInstance(getApplication()).getChildCall()) {
                   //     if (countCalls == snapshot.getChildrenCount()) {
                    //        SharedPreferencesHelper.getInstance(getApplication()).setChildCallTrue();
                    //    }
                    //    return;
                   // } else {

                        DataSnapshot order = null;

                        for (DataSnapshot or : snapshot.getChildren()) {
                            order = or;
                        }

                        /*if (order.getChildrenCount() > 5)  //verify if it`s a new order insert or update order
                            return;*/

                        Order o = order.getValue(Order.class);

                        myList.add(order);

                        //Log.d(TAG, "Job`s done!");
                       // clientOrderList.postValue(o);

                    }

                //} else {
               //     writeCall = false;
               // }
            //}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    private String getMainDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMyyyy");
        return dateFormat.format(new Date());
    }
}

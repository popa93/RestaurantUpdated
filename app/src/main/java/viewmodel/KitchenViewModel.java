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

import Util.Constants;
import Util.SharedPreferencesHelper;
import model.Order;

public class KitchenViewModel extends AndroidViewModel {

    private final static String TAG = KitchenViewModel.class.getSimpleName();

    public MutableLiveData<Order> clientOrderList = new MutableLiveData<>();
    private int countCalls;
    String specificEmui;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference(Constants.ORDERS).child(Constants.DEVICE_EMUI).child(getMainDate());
    DatabaseReference specificChildReference;
    boolean writeCall = false;

    ArrayList<DataSnapshot> myList = new ArrayList<>();

    public KitchenViewModel(@NonNull Application application) {

        super(application);
        SharedPreferencesHelper.getInstance(getApplication()).setChildCallFalse();
        countCalls = 0;

    }

    public void deleteOrderFromList(int position) {
        myList.remove(position);
    }

    public void fetchClientOrderList() {

        dbRef.addValueEventListener(new ValueEventListener() { //every emui has a listener and all orders are added to same list(myList)

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!writeCall) {   // this if is for the first time  when OnDataChange is called(when no data was written to db). Is to avoid extra useless list entries
                    if (countCalls < snapshot.getChildrenCount()) {
                        countCalls = Math.toIntExact(snapshot.getChildrenCount());
                    }

                    if (!SharedPreferencesHelper.getInstance(getApplication()).getChildCall()) {
                        if (countCalls == snapshot.getChildrenCount()) {
                            SharedPreferencesHelper.getInstance(getApplication()).setChildCallTrue();
                        }
                        return;
                    } else {

                        DataSnapshot order = null;

                        for (DataSnapshot or : snapshot.getChildren()) {
                            order = or;
                        }

                        /*if (order.getChildrenCount() > 5)  //verify if it`s a new order insert or update order
                            return;*/

                        Order o = order.getValue(Order.class);
                        if (!o.getFood().isEmpty()) {
                            myList.add(order); //last order is the most recent
                            clientOrderList.postValue(o);
                        }
                    }

                } else {
                    writeCall = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void writeOrderStatusToDB(ArrayList<Order> kitchenList, int positionClicked) { //rezolva cazul in care ai mai mult comenzi simultan in lista si dai accept la una random.simultan de la acelasi device sau device diferite
        //dbRefOrderChild.child("orderStatus").setValue("true");  //poti adauga comanda cu emui, mancarea si timpul. si pt acceptare iterez prin lista dupa astea 3
        writeCall = true;
        Order or = kitchenList.get(positionClicked);
        Order orderr;
        String nodeName = null;
        for (DataSnapshot data : myList) {
            orderr = data.getValue(Order.class);

            nodeName = data.getKey();
            if (orderr.getDate().equals(or.getDate())) {
                specificEmui = data.getRef().getParent().getParent().getKey();
                Log.d(TAG, data.getRef().getParent().getParent().getKey());   //navigates up in json db hierarchy and gets the emui node that correpsonds to clicked(checked) order
                break;
            }
        }

        specificChildReference = database.getReference(Constants.ORDERS).child(specificEmui).child(getMainDate()).child(nodeName);
        specificChildReference.child(Constants.ORDER_STATUS).setValue(Constants.TRUE);
    }

    private String getMainDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DMY);
        return dateFormat.format(new Date());
    }
}

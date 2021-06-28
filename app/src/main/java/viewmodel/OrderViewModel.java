package viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import Util.Constants;
import Util.NotificationsHelper;
import Util.SharedPreferencesHelper;
import model.Drink;
import model.Order;
import model.Pizza;
import view.OrderFragment;

public
class OrderViewModel extends AndroidViewModel {

    private final static String TAG = OrderViewModel.class.getSimpleName();
    public MutableLiveData<String> orderLiveData = new MutableLiveData<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef;
    String mainDate;
    String orderHour;


    public OrderViewModel(@NonNull Application application) {
        super(application);
    }


    public void placeOrder(String emui, String remarks) {
        HashMap<String, Object> order;

        orderHour = getOrderHour();
        mainDate = getMainDate();

        DatabaseReference databaseReference = database.getReference(Constants.ORDERS);
        dbRef = database.getReference(Constants.ORDERS).child(Constants.DEVICE_EMUI).child(getMainDate()); //delete

        try {
            order = new HashMap<>(); //it seems that if i create Order object into setvalue will also write status field even if i do not use that constructor
            order.put(Constants.DATE, orderHour); //solution with map is w workaround to that
            order.put(Constants.FOOD_NODE, foodString(emui));
            order.put(Constants.DRINK_NODE, drinkString(emui));
            order.put(Constants.TOTAL, getTotalPrice());
            order.put(Constants.REMARKS, remarks);
           // order.put("orderStatus", "false");

            //databaseReference.child(emui).child(mainDate).child(String.valueOf(System.currentTimeMillis())).setValue(new Order(foodString(), drinkString(), getTotalPrice(), orderHour));
            databaseReference.child(emui).child(mainDate).child(String.valueOf(System.currentTimeMillis())).setValue(order);

        } catch (Exception e) {

            //Log.e(TAG, "Could not write to database, exception: " + e.getMessage());
            orderLiveData.postValue(Constants.ERROR_MSG);
        }

        orderLiveData.postValue(Constants.OK2);
    }

    public void setOrderClickListener(){
        DatabaseReference databaseReference = database.getReference(Constants.ORDERS).child(Constants.DEVICE_EMUI).child(getMainDate());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Order finalOrder=null;
                DataSnapshot sn=null;
                for (DataSnapshot chilld : snapshot.getChildren()) {

                      finalOrder = chilld.getValue(Order.class);
                      sn=chilld;

                }
                if(finalOrder.getOrderStatus()!=null&&!finalOrder.getOrderStatus().equals("false")&&!finalOrder.getOrderStatus().equals("neutral")){
                    Log.e("status","status nu e null");

                   NotificationsHelper notificationsHelper = new NotificationsHelper(getApplication(), "2");
                    notificationsHelper.createNotification(); // here maybe create notif if chef accepts order
                    //pune aici iar pe false
                    database.getReference(Constants.ORDERS).child(Constants.DEVICE_EMUI).child(getMainDate()).child(sn.getKey()).child("orderStatus").setValue("neutral");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private String getTotalPrice() {

        float total = 0;

        for (Object item : OrderFragment.orderList) {

            if (item instanceof Pizza)
                total += (((Pizza) item).getPrice());
            else if (item instanceof Drink)
                total += (((Drink) item).getPrice());
        }

        return total + Constants.LEI;
    }

    private String foodString(String emui) {// pe baza emui  afisez de la ce masa e comanda
        StringBuilder stringBuilderFood = new StringBuilder();

        for (Object item : OrderFragment.orderList) {

            if (item instanceof Pizza) {
                stringBuilderFood.append(((Pizza) item).getName());
                stringBuilderFood.append(",");
            }

        }

        if(emui.equals(Constants.DEVICE_EMUI)) {
            stringBuilderFood.append(" Table 1");
        }

        return stringBuilderFood.toString();
    }

    private String drinkString(String emui) {

        StringBuilder stringBuilderDrink = new StringBuilder();

        for (Object item : OrderFragment.orderList) {


            if (item instanceof Drink) {
                stringBuilderDrink.append(((Drink) item).getName());
                stringBuilderDrink.append(",");

            }
        }

        if(emui.equals(Constants.DEVICE_EMUI)) {
            stringBuilderDrink.append(" Table 1");
        }

        return stringBuilderDrink.toString();
    }

    private String getMainDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DMY);
        return dateFormat.format(new Date());
    }

    private String getOrderHour() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_H);
        return dateFormat.format(new Date());
    }

}

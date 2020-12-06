package viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Drink;
import model.Order;
import model.Pizza;
import view.OrderFragment;

public
class OrderViewModel extends ViewModel {

    public MutableLiveData<String> orderLiveData = new MutableLiveData<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String dbEmail;


    public void placeOrder() {

        String date = getDate();
        countToAt();
        DatabaseReference databaseReference = database.getReference("Orders");

        try {
            databaseReference.child(dbEmail).child(String.valueOf(System.currentTimeMillis())).setValue(new Order(foodString(), drinkString(), getTotalPrice(), date));
        } catch (Exception e) {

            orderLiveData.setValue("EXCEPTION");
            return;
        }

        orderLiveData.setValue("OK");


    }

    public String getTotalPrice() {

        float total = 0;

        for (Object item : OrderFragment.orderList) {

            if (item instanceof Pizza)
                total += (((Pizza) item).getPrice());
            else if (item instanceof Drink)
                total += (((Drink) item).getPrice());
        }

        return total + "lei";
    }

    private String foodString() {
        StringBuilder stringBuilderFood = new StringBuilder();


        for (Object item : OrderFragment.orderList) {

            if (item instanceof Pizza) {
                stringBuilderFood.append(((Pizza) item).getName());
                stringBuilderFood.append(" ");
            }

        }

        return stringBuilderFood.toString();


    }

    private String drinkString() {

        StringBuilder stringBuilderDrink = new StringBuilder();

        for (Object item : OrderFragment.orderList) {


            if (item instanceof Drink) {
                stringBuilderDrink.append(((Drink) item).getName());
                stringBuilderDrink.append(" ");

            }
        }

        return stringBuilderDrink.toString();


    }

    private void countToAt() { //to @ sign
        int contor = 0;

        String email = currentUser.getEmail();

        for (int i = 0; i < email.length(); ++i) {
            ++contor;
            if (email.charAt(i) == '@') {
                --contor;
                break;
            }

        }
        dbEmail = email.substring(0, contor);


    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M//yyyy hh:mm:ss");

        return dateFormat.format(new Date());
    }


}

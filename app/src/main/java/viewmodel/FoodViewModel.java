package viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.Pizza;

public
class FoodViewModel extends ViewModel {

    private ArrayList<Pizza> pizzas=new ArrayList<>();
    public MutableLiveData<ArrayList<Pizza>> pizzaMutableLiveData=new MutableLiveData<>();
    private FirebaseDatabase myDatabase;





    public void getPizzas(){
        Log.e("apel","apel dld");
        myDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=myDatabase.getReference("Menu");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               for(DataSnapshot child:snapshot.getChildren()){

                pizzas.add(child.getValue(Pizza.class));
                  // Log.e("lista",pizzas.get(0).getImageLink());
               }
               pizzaMutableLiveData.postValue(pizzas);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}

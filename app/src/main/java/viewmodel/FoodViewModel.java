package viewmodel;

import android.app.Application;
import android.os.AsyncTask;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Util.SharedPreferencesHelper;
import model.ItemDao;
import model.ItemsDatabase;
import model.Pizza;

public
class FoodViewModel extends AndroidViewModel {

    private ArrayList<Pizza> pizzas = new ArrayList<>();
    Calendar calendar = Calendar.getInstance();
    private int day;
    private int dateResult;
    private long refreshTime = 5* 1000 * 1000 * 1000L; // 5 sec(30 min) interval 
    private SharedPreferencesHelper prefHelper = SharedPreferencesHelper.getInstance(getApplication());
    public MutableLiveData<ArrayList<Pizza>> pizzaMutableLiveData = new MutableLiveData<>();
    private FirebaseDatabase myDatabase;
    private AsyncTask<ArrayList<Pizza>, Void, ArrayList<Pizza>> insertTask;
    private AsyncTask<Void, Void, List<Pizza>> retrieveTask;

    public FoodViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {

        dateResult=checkDate();
        long updateTime = prefHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        if (updateTime != 0 && currentTime - updateTime < refreshTime && dateResult==0) {

            fetchFromDatabase();
        } else {

            getPizzas();
        }
    }


    public void getPizzas() {
        Log.e("apel", "apel dld");
        myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = myDatabase.getReference("Menu");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()) {

                    pizzas.add(child.getValue(Pizza.class));

                }
                // pizzaMutableLiveData.postValue(pizzas);
                insertTask = new InsertFoodTask();
                insertTask.execute(pizzas);
                Toast.makeText(getApplication(), "item retrieved from backend", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setDate(){

        day= calendar.get(Calendar.DATE);
    }

    private int checkDate(){

        return calendar.get(Calendar.DATE)-(prefHelper.getLastBackendDownloadDate());

    }

    private void fetchFromDatabase() {

        retrieveTask = new RetrieveItemsTask();
        retrieveTask.execute();
    }

    private void itemsRetrieved(ArrayList<Pizza> foodList) {

        pizzaMutableLiveData.setValue(foodList);
    }

    private class InsertFoodTask extends AsyncTask<ArrayList<Pizza>, Void, ArrayList<Pizza>> {


        @Override
        protected ArrayList<Pizza> doInBackground(ArrayList<Pizza>... arrayLists) {
            ArrayList<Pizza> list = arrayLists[0];
            ItemDao itemDao = ItemsDatabase.getInstance(getApplication()).itemDao();
            itemDao.deleteAllFood();

            ArrayList<Pizza> newList = new ArrayList<>(list);
            List<Long> result = itemDao.insertAll(newList.toArray(new Pizza[0]));

            int i = 0;
            while (i < list.size()) {

                list.get(i).uuid = result.get(i).intValue();
                ++i;
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Pizza> pizzas) { // will be executed on foreground thread

            itemsRetrieved(pizzas);
            prefHelper.saveUpdateTime(System.nanoTime());
            setDate();
            prefHelper.setLastBackendDownloadDate(day);

        }
    }

    private class RetrieveItemsTask extends AsyncTask<Void, Void, List<Pizza>> {
        @Override
        protected List<Pizza> doInBackground(Void... voids) {


            return ItemsDatabase.getInstance(getApplication()).itemDao().getAllItems();
        }

        @Override
        protected void onPostExecute(List<Pizza> pizzas) {
            ArrayList<Pizza> retrived = new ArrayList<>(pizzas);
            itemsRetrieved(retrived);
            Toast.makeText(getApplication(), "Items retrieved from database", Toast.LENGTH_SHORT).show();
        }
    }

}



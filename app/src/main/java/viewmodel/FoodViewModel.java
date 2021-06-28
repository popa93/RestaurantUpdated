package viewmodel;

import android.app.Application;
import android.os.AsyncTask;
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

import Util.Constants;
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
    private SharedPreferencesHelper prefHelper = SharedPreferencesHelper.getInstance(getApplication());
    public MutableLiveData<ArrayList<Pizza>> pizzaMutableLiveData = new MutableLiveData<>();
    private FirebaseDatabase myDatabase;
    private AsyncTask<ArrayList<Pizza>, Void, ArrayList<Pizza>> insertTask;
    private AsyncTask<Void, Void, List<Pizza>> retrieveTask;

    public FoodViewModel(@NonNull Application application) {
        super(application);
    }

    private void getPizzas() {
        myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = myDatabase.getReference(Constants.MENU);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (pizzas.size() >= 6)
                    pizzas.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    pizzas.add(child.getValue(Pizza.class));
                }
                insertTask = new InsertFoodTask();
                insertTask.execute(pizzas);
                Toast.makeText(getApplication(), "item retrieved from backend food", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void refresh() {
        dateResult = checkDate();
        long updateTime = prefHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        if (updateTime != 0 && currentTime - updateTime < Constants.REFRESH_TIME && dateResult == 0) {
            fetchFromDatabase();
        } else {
            getPizzas();
        }
    }

    private void setDate() {

        day = calendar.get(Calendar.DATE);
    }

    private int checkDate() {

        return calendar.get(Calendar.DATE) - (prefHelper.getLastBackendDownloadDate());

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


            List<Long> result = itemDao.insertAll(list);    //modified parameter in itemDao from ... to ArrayList

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

            Toast.makeText(getApplication(), "Items retrieved from database food2", Toast.LENGTH_SHORT).show();
        }
    }

}



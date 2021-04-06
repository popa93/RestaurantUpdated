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
import model.Drink;
import model.ItemDao;
import model.ItemsDatabase;

public
class DrinkViewModel extends AndroidViewModel {

    public MutableLiveData<ArrayList<Drink>> drinksMutableLiveData = new MutableLiveData<>();

    private FirebaseDatabase myDatabase;
    private ArrayList<Drink> drinks = new ArrayList<>();
    private AsyncTask<ArrayList<Drink>, Void, ArrayList<Drink>> insertTask;
    private AsyncTask<Void, Void, List<Drink>> retrieveTask;
    private Calendar calendar = Calendar.getInstance();
    private int day;
    private int dateResult;

    private SharedPreferencesHelper prefHelper = SharedPreferencesHelper.getInstance(getApplication());


    public DrinkViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        refreshCacheTime();
        dateResult = checkDate();
        long updateTime = prefHelper.getUpdateTime();
        long currentTime = System.nanoTime();
        if (updateTime != 0 && currentTime - updateTime < Constants.REFRESH_TIME && dateResult == 0) {

            fetchFromDatabase();
        } else {

            getDrinks();
        }
    }


    public void getDrinks() {
        myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = myDatabase.getReference(Constants.DRINKS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (drinks.size() >= 6)
                    drinks.clear();
                for (DataSnapshot child : snapshot.getChildren()) {

                    drinks.add(child.getValue(Drink.class));

                }
                drinksMutableLiveData.postValue(drinks);
                insertTask = new InsertDrinkTask();
                insertTask.execute(drinks);
                Toast.makeText(getApplication(), "item retrieved from backend drink", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void refreshCacheTime() { //it is enough this method to be called in this class because it gets new refresh data from db and sets Constant.REFRESH_TIME and foodViewModel  will have the new refresh time also

        myDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = myDatabase.getReference("CacheTime");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Constants.REFRESH_TIME = (Long) snapshot.getValue(); //sets new cache time

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setDate() {   //no need here because the date will be set by food calls

        day = calendar.get(Calendar.DATE);
    }

    private int checkDate() {

        return calendar.get(Calendar.DATE) - (prefHelper.getLastBackendDownloadDate());

    }

    private void fetchFromDatabase() {

        retrieveTask = new RetrieveDrinksTask();
        retrieveTask.execute();
    }


    private void itemsRetrieved(ArrayList<Drink> drinkList) {

        drinksMutableLiveData.setValue(drinkList);
    }

    private class InsertDrinkTask extends AsyncTask<ArrayList<Drink>, Void, ArrayList<Drink>> {


        @Override
        protected ArrayList<Drink> doInBackground(ArrayList<Drink>... arrayLists) {
            ArrayList<Drink> list = arrayLists[0];
            ItemDao itemDao = ItemsDatabase.getInstance(getApplication()).itemDao();
            itemDao.deleteAllDrinks();


            List<Long> result = itemDao.insertAllDrinks(list);

            int i = 0;
            while (i < list.size()) {

                list.get(i).uuid = result.get(i).intValue();
                ++i;
            }
            return list;
        }


        @Override
        protected void onPostExecute(ArrayList<Drink> drinks) { // will be executed on foreground thread

            itemsRetrieved(drinks);
            prefHelper.saveUpdateTime(System.nanoTime());
            setDate();
            prefHelper.setLastBackendDownloadDate(day);

        }
    }

    private class RetrieveDrinksTask extends AsyncTask<Void, Void, List<Drink>> {
        @Override
        protected List<Drink> doInBackground(Void... voids) {


            return ItemsDatabase.getInstance(getApplication()).itemDao().getAllDrinksItems();
        }

        @Override
        protected void onPostExecute(List<Drink> drinks) {
            ArrayList<Drink> retrieved = new ArrayList<>(drinks);
            itemsRetrieved(retrieved);
            Toast.makeText(getApplication(), "Drinks retrieved from database drink2", Toast.LENGTH_SHORT).show();
        }
    }


}

package viewmodel;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import Util.Constants;
import Util.SharedPreferencesHelper;
import model.Order;
import model.Pizza;

public
class AdminActionsViewModel extends AndroidViewModel {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public MutableLiveData<String> storageUri = new MutableLiveData<>();

    public AdminActionsViewModel(@NonNull Application application) {
        super(application);

    }


    public void onClickSetTime(int hours, int minutes, int seconds) {
        DatabaseReference databaseReference = database.getReference(Constants.CACHE_TIME);

        long hoursToMinutes = hours * 60;
        long totalMinutes = hoursToMinutes + minutes;
        double secondsToMinutes = (double) seconds / 60;

        double totalTimeInMinutes = totalMinutes + secondsToMinutes;


        databaseReference.setValue(totalTimeInMinutes * 60 * (long) Math.pow(1000, 3));
        Toast.makeText(getApplication().getApplicationContext(), "Time was set", Toast.LENGTH_SHORT).show();

    }

    public void uploadAndGetImageUrl(Uri imageUri, String pictureName) {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        StorageReference ref = storageReference.child("images/" + pictureName);
        UploadTask uploadTask = ref.putFile(imageUri);

        Task<Uri> urlTask = uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                final Uri downloadUri = task.getResult();
                storageUri.postValue(task.getResult().toString());

                Log.e("asda", downloadUri.toString());

            }
        });

    }

    /*public void addNewFoodItem(String link,String ingredients, String name, String price, String quantity,String itemNameInDb) {

        DatabaseReference databaseReference = database.getReference(Constants.MENU).child(itemNameInDb);

        HashMap<String, Object> newFoodItem = new HashMap();
        newFoodItem.put("imageLink", link);
        newFoodItem.put("ingredients", ingredients);
        newFoodItem.put("name", name);
        newFoodItem.put("price", Long.valueOf(price));
        newFoodItem.put("quantity", Integer.valueOf(quantity));
        databaseReference.setValue(newFoodItem);
    }*/

    public boolean addNewFoodItem(String link,String ingredients, String name, String price, String quantity,String itemNameInDb) {

        DatabaseReference databaseReference = database.getReference(Constants.MENU).child(itemNameInDb);
        if (!(price.matches("[0-9]") && price.length() >= 1)||!(quantity.matches("[0-9]") && quantity.length() >= 1)||price.isEmpty()||quantity.isEmpty()||ingredients.isEmpty()||name.isEmpty()||link.isEmpty()){
            return false;
        }
        HashMap<String, Object> newFoodItem = new HashMap();
        newFoodItem.put("imageLink", link);
        newFoodItem.put("ingredients", ingredients);
        newFoodItem.put("name", name);
        newFoodItem.put("price", Long.valueOf(price));
        newFoodItem.put("quantity", Integer.valueOf(quantity));
        databaseReference.setValue(newFoodItem);
        return true;
    }

    public boolean addNewDrinkItem(String link,String ingredients, String name, String price, String quantity,String itemNameInDb) {

        DatabaseReference databaseReference = database.getReference(Constants.DRINKS).child(itemNameInDb);
        if (!(price.matches("[0-9]") && price.length() >= 1)||!(quantity.matches("[0-9]") && quantity.length() >= 1)||price.isEmpty()||quantity.isEmpty()||ingredients.isEmpty()||name.isEmpty()||link.isEmpty()){
            return false;
        }

        HashMap<String, Object> newDrinkItem = new HashMap();
        newDrinkItem.put("imageLink", link);
        newDrinkItem.put("ingredients", ingredients);
        newDrinkItem.put("name", name);
        newDrinkItem.put("price", Long.valueOf(price));
        newDrinkItem.put("quantity", Integer.valueOf(quantity));
        databaseReference.setValue(newDrinkItem);
        return true;
    }

    public void deleteItem(String nameOfItem,boolean isFood){
        Log.e("tag","sunt in delete");
       /* DatabaseReference databaseReference;
        if(isFood){
             databaseReference=database.getReference(Constants.MENU).child(nameOfItem);
             databaseReference.removeValue();
        }
        else{
            databaseReference=database.getReference(Constants.DRINKS).child(nameOfItem);
            databaseReference.removeValue();
        }*/

        DatabaseReference databaseReference;
        DatabaseReference databaseReference2;

        if(isFood){
            databaseReference2=database.getReference(Constants.MENU);

            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                boolean aux=true;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data:snapshot.getChildren()){
                       // Order or = data.getValue(Order.class);
                        if (data.getKey().equals(nameOfItem)) {
                            DatabaseReference databaseReference = database.getReference(Constants.MENU).child(nameOfItem);
                            databaseReference.removeValue();
                            databaseReference2.removeEventListener(this);
                            aux=false;
                        }
                    }

                    if(aux) {//se apeleaza de 2 ori si intra aici si cand se sterge prima data
                        Log.e("delete", "nu exista nodul");
                        Toast.makeText(getApplication().getApplicationContext(), "Nu exista nodul", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            /*databaseReference=database.getReference(Constants.MENU).child(nameOfItem);
            databaseReference.removeValue();*/
        }
        else{
            DatabaseReference databaseReference3;
            /*databaseReference=database.getReference(Constants.DRINKS).child(nameOfItem);
            databaseReference.removeValue();*/
            databaseReference3=database.getReference(Constants.DRINKS);

            databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                boolean aux=true;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot data:snapshot.getChildren()){
                        // Order or = data.getValue(Order.class);
                        if (data.getKey().equals(nameOfItem)) {
                            DatabaseReference databaseReference = database.getReference(Constants.DRINKS).child(nameOfItem);
                            databaseReference.removeValue();
                            databaseReference3.removeEventListener(this);
                            aux=false;
                        }
                    }

                    if(aux) {//se apeleaza de 2 ori si intra aici si cand se sterge prima data
                        Log.e("delete", "nu exista nodul");
                        Toast.makeText(getApplication().getApplicationContext(), "Nu exista nodul", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


}

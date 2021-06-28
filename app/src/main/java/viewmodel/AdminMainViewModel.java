package viewmodel;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import Util.Constants;
import model.Order;

public
class AdminMainViewModel extends AndroidViewModel {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference(Constants.ORDERS).child(Constants.DEVICE_EMUI).child(getMainDate());
    DatabaseReference databaseReferenceEmui = database.getReference(Constants.ORDERS).child(Constants.DEVICE_EMUI);
    public MutableLiveData<String> currentDayIncome = new MutableLiveData<>();
    public MutableLiveData<String> comparativeData = new MutableLiveData<>();



    public AdminMainViewModel(@NonNull Application application) {
        super(application);
    }





    public void getCurrentDayIncome() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double total = 0;
                for (DataSnapshot child : snapshot.getChildren()) {

                    Order order = child.getValue(Order.class);
                    total += orderTotalOperation(order.getTotal());
                }
                currentDayIncome.postValue(String.valueOf(total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void getComparativePercentage() {

        databaseReferenceEmui.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean shouldCalculate;
                boolean shouldCalculateForLastMonth;
                double thisMontTotal = 0;
                double lastMontTotal = 0;

                for (DataSnapshot child : snapshot.getChildren()) {
                    shouldCalculate = false;
                    shouldCalculateForLastMonth = false;

                    shouldCalculate = checkForThisMonth(child.getKey());
                    shouldCalculateForLastMonth = checkForLastMonth(child.getKey());

                    if (shouldCalculateForLastMonth) {
                        for (DataSnapshot childOfChild : child.getChildren()) {

                            Order order = childOfChild.getValue(Order.class);
                            lastMontTotal += orderTotalOperation(order.getTotal());
                        }
                    }
                    if (shouldCalculate) {

                        for (DataSnapshot childOfChild : child.getChildren()) {

                            Order order = childOfChild.getValue(Order.class);
                            thisMontTotal += orderTotalOperation(order.getTotal());
                        }


                    }
                }

                double difference = 0;
                double total = 0;
                if (lastMontTotal == 0) {
                    comparativeData.postValue("+ " + thisMontTotal + " %");
                } else if (thisMontTotal > lastMontTotal) {
                    difference = thisMontTotal - lastMontTotal;
                    total = difference * 100 / lastMontTotal;
                    comparativeData.postValue("+ " + total + " %");
                } else if (thisMontTotal < lastMontTotal) {
                    difference = lastMontTotal - thisMontTotal;
                    total = difference * 100 / lastMontTotal;
                    comparativeData.postValue("- " + total + " %");
                } else {
                    comparativeData.postValue(total + " %");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private double orderTotalOperation(String total) {
        total = total.substring(0, total.length() - 4);
        return Double.valueOf(total);
    }

    private String getMainDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_DMY);
        return dateFormat.format(new Date());
    }


    private boolean checkForLastMonth(String key) {
        String month = key.substring(2, 3);
        String mainDate = getMainDate();
        String finalMainDate = mainDate.substring(2, 3);
        if (Integer.valueOf(month) == (Integer.valueOf(finalMainDate) + 1) || (Integer.valueOf(month) == 12 && Integer.valueOf(finalMainDate) == 1)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkForThisMonth(String key) {
        String month = key.substring(2, 3);
        String mainDate = getMainDate();
        String finalMainDate = mainDate.substring(2, 3);
        if (month.equals(finalMainDate)) {
            return true;
        } else {
            return false;
        }

    }


}

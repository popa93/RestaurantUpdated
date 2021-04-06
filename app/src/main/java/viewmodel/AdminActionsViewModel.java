package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Util.Constants;

public
class AdminActionsViewModel extends AndroidViewModel {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

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

    }
}

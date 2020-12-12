package viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Util.Constants;

public class LoginViewModel extends AndroidViewModel {

    public MutableLiveData<String> firebaseUserMutableLiveData = new MutableLiveData<>();

    private FirebaseAuth auth;
    private AlertDialog.Builder alertDialogBuilder;
    private final Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = application;
    }

    public void login(String email, String pass) {

        auth = FirebaseAuth.getInstance();
        alertDialogBuilder = new AlertDialog.Builder(context);


        if (email.isEmpty() || pass.isEmpty()) {

            firebaseUserMutableLiveData.postValue(Constants.EMPTY);

        } else {

            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        firebaseUserMutableLiveData.postValue(email);

                    } else {

                        firebaseUserMutableLiveData.postValue(null);

                    }
                }
            });
        }
    }
}

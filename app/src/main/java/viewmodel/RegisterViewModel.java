package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public
class RegisterViewModel extends AndroidViewModel {

    public MutableLiveData<String> registerLiveData = new MutableLiveData<>();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private FirebaseAuth authentication;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void register(String email, String password, String confirmPassword) {

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {

            registerLiveData.postValue("All fields must be completed");

        } else if (!(password.equals(confirmPassword))) {

            registerLiveData.postValue("Password fields must be identical");
        } else if (!(email.matches(emailPattern))) {

            registerLiveData.postValue("Email field bad format");
        } else {
            authentication = FirebaseAuth.getInstance();

            int letterCountPass = password.length();


            if (letterCountPass >= 6) {
                authentication.createUserWithEmailAndPassword(email, password);

                registerLiveData.postValue("OK");
            } else {

                registerLiveData.postValue("Password must contain at least 6 characters");

            }
        }


    }

}

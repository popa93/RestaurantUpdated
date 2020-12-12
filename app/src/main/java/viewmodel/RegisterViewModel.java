package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import Util.Constants;

public
class RegisterViewModel extends AndroidViewModel {

    public MutableLiveData<String> registerLiveData = new MutableLiveData<>();
    private FirebaseAuth authentication;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public void register(String email, String password, String confirmPassword) {

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {

            registerLiveData.postValue(Constants.COMPLETE_ALL_FIELDS);

        } else if (!(password.equals(confirmPassword))) {

            registerLiveData.postValue(Constants.IDENTICAL_PASSWORDS);
        } else if (!(email.matches(Constants.EMAIL_PATTERN))) {

            registerLiveData.postValue(Constants.EMAIL_BAD_FORMAT);
        } else {
            authentication = FirebaseAuth.getInstance();

            int letterCountPass = password.length();


            if (letterCountPass >= 6) {
                authentication.createUserWithEmailAndPassword(email, password);

                registerLiveData.postValue(Constants.OK);
            } else {

                registerLiveData.postValue(Constants.PASSWORD_MIN_CHARACTERS);

            }
        }


    }

}

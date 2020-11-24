package viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public
class SplashScreenViewModel extends ViewModel {

    private FirebaseUser user;
    public MutableLiveData<String> loginStateLiveData = new MutableLiveData<>();


    public void checkUserLoginState() {

        user = FirebaseAuth.getInstance().getCurrentUser();
        String result = user == null ? "YES" : "NO";

        loginStateLiveData.postValue(result);

    }
}

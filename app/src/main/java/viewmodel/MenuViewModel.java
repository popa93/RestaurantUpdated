package viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public
class MenuViewModel extends ViewModel {

    FirebaseAuth authentication;
    public MutableLiveData<String> menuLiveData=new MutableLiveData<>();


    public void signOut(){

        authentication=FirebaseAuth.getInstance();

        try{
            authentication.signOut();
            menuLiveData.postValue("OK");
        }catch (Exception e){

            menuLiveData.postValue("Error");
        }


    }


}

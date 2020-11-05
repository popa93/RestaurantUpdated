package view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantupdated.R;


public class SplashScreenFragment extends Fragment {


    private Handler handler;

    public SplashScreenFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler=new Handler();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpToLogin();
            }
        },1500);
    }

    private void jumpToLogin(){

      NavDirections action=SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginNestedFragment();
        if(getView()!=null)
            Navigation.findNavController(getView()).navigate(action);

    }
}
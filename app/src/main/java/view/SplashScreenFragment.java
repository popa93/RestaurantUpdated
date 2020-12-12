package view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.restaurantupdated.R;

import Util.Constants;
import viewmodel.SplashScreenViewModel;


public class SplashScreenFragment extends Fragment {

    private SplashScreenViewModel splashScreenViewModel;
    private Handler handler;

    public SplashScreenFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();

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
        splashScreenViewModel = ViewModelProviders.of(this).get(SplashScreenViewModel.class);
        observeViewModel();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                splashScreenViewModel.checkUserLoginState();
            }
        }, Constants.DELAY_TIME);
    }


    private void observeViewModel() {

        splashScreenViewModel.loginStateLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals(Constants.YES)) {
                    jumpToLogin();
                } else {
                    jumpToMenu();
                }
            }
        });

    }


    private void jumpToLogin() {

        NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment();
        if (getView() != null)
            Navigation.findNavController(getView()).navigate(action);

    }

    private void jumpToMenu() {

        NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToMenuFragment();
        if (getView() != null)
            Navigation.findNavController(getView()).navigate(action);

    }
}
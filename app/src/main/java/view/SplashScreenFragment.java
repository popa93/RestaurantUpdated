package view;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.restaurantupdated.R;

import Util.Constants;
import Util.SharedPreferencesHelper;


public class SplashScreenFragment extends Fragment {

    private static final String TAG = SplashScreenFragment.class.getSimpleName();

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


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                popThemeFragmentOrChosenTheme();
                // jumpToMenu();
            }
        }, Constants.DELAY_TIME);
    }

    private void jumpToChosenTheme() {
        String theme = SharedPreferencesHelper.getInstance(getContext()).getTheme();
        if (theme.equals(Constants.CLIENT_THEME)) {
            //Log.d(TAG,"Client theme");
            jumpToMenu();
        } else if (theme.equals(Constants.KITCHEN_THEME)) {
            // Log.d(TAG,"Kitchen theme");
            jumpToKitchen();
        } else {
            Log.d(TAG, "Admin theme");
        }
    }

    private void popThemeFragmentOrChosenTheme() {
        if (SharedPreferencesHelper.getInstance(getContext()).getTheme().equals(Constants.NO_THEME)) {
            NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToThemeChooserFragment();
            if (getView() != null) {
                Navigation.findNavController(getView()).navigate(action);
            }
        } else {
            jumpToChosenTheme();
        }
    }


    private void jumpToMenu() {

        NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToMenuFragment();
        if (getView() != null)
            Navigation.findNavController(getView()).navigate(action);

    }

    private void jumpToKitchen() {

        NavDirections action = SplashScreenFragmentDirections.actionSplashScreenFragmentToKitchenMainFragment();
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(action);
        }
    }
}
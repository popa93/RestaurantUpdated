package view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.restaurantupdated.databinding.FragmentThemeChooserBinding;

import Util.Constants;
import Util.SharedPreferencesHelper;


public class ThemeChooserFragment extends Fragment {

    public final static String TAG = ThemeChooserFragment.class.getSimpleName();

    private FragmentThemeChooserBinding binding;
    private boolean clientValue, kitchenValue, adminValue;


    public ThemeChooserFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientValue = false;
        kitchenValue = false;
        adminValue = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentThemeChooserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientValue = true;
                SharedPreferencesHelper.getInstance(getContext()).setTheme(clientValue, kitchenValue, adminValue);
                jumpToChosenTheme();
            }
        });

        binding.kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kitchenValue = true;
                SharedPreferencesHelper.getInstance(getContext()).setTheme(clientValue, kitchenValue, adminValue);
                jumpToChosenTheme();
            }
        });

        binding.admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminValue = true;
                SharedPreferencesHelper.getInstance(getContext()).setTheme(clientValue, kitchenValue, adminValue);
                jumpToChosenTheme();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void jumpToChosenTheme() {
        String theme = SharedPreferencesHelper.getInstance(getContext()).getTheme();
        if (theme.equals(Constants.CLIENT_THEME)) {
            Log.d(TAG, "Client theme");
            jumpToMenu();
        } else if (theme.equals(Constants.KITCHEN_THEME)) {
            Log.d(TAG, "Kitchen theme");
            jumpToKitchen();
        } else if (theme.equals(Constants.ADMIN_THEME)) {
            jumpToAdmin();
            Log.d(TAG, "Admin theme");
        }
    }

    private void jumpToAdmin() {

        NavDirections action = ThemeChooserFragmentDirections.actionThemeChooserFragmentToAdminMainFragment();
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(action);
        }
    }


    private void jumpToMenu() {

        NavDirections action = ThemeChooserFragmentDirections.actionThemeChooserFragmentToMenuFragment();
        if (getView() != null)
            Navigation.findNavController(getView()).navigate(action);

    }

    private void jumpToKitchen() {

        NavDirections action = ThemeChooserFragmentDirections.actionThemeChooserFragmentToKitchenMainFragment();
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(action);
        }
    }
}
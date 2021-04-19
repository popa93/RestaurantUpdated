package view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.restaurantupdated.databinding.FragmentAdminActionsBinding;

import Util.Constants;
import viewmodel.AdminActionsViewModel;


public class AdminActionsFragment extends Fragment {    //build all actions layout from 1 fragment(hide and show elements based on admin selection)


    FragmentAdminActionsBinding binding;
    AdminActionsViewModel adminViewModel;
    String data;

    public AdminActionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // data=getArguments().getString(AdminMainFragment.TAG_ADMIN_MAIN_FRAGMENT);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentAdminActionsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adminViewModel = ViewModelProviders.of(this).get(AdminActionsViewModel.class);


        if (getView() != null) {
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(Navigation.findNavController(getView()).getGraph()).build();
            NavigationUI.setupWithNavController(binding.topAppBar, Navigation.findNavController(getView()), appBarConfiguration);
        }


        data = getArguments().getString(AdminMainFragment.TAG_ADMIN_MAIN_FRAGMENT);
        if (data.equals("add menu")) {

            binding.topAppBar.setTitle(Constants.ADD_NEW_MENU_ITEM);
            setVisibility(false, false, false, false, false, false, false);
        } else {

            binding.topAppBar.setTitle(Constants.CACHE_TIME);
            binding.setTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (TextUtils.isEmpty(binding.hours.getText().toString()) || TextUtils.isEmpty(binding.minutes.getText().toString()) || TextUtils.isEmpty(binding.seconds.getText().toString())) {
                        setHMSToDefault();
                    }
                    adminViewModel.onClickSetTime(Integer.parseInt(binding.hours.getText().toString()), Integer.parseInt(binding.minutes.getText().toString()), Integer.parseInt(binding.seconds.getText().toString()));

                }
            });
        }


    }

    private void setVisibility(boolean ETseconds, boolean ETminutes, boolean EThours, boolean TVhours, boolean TVminutes, boolean TVseconds, boolean button) {
        if (!ETseconds) {
            binding.seconds.setVisibility(View.INVISIBLE);
        } else {
            binding.seconds.setVisibility(View.VISIBLE);
        }
        if (!ETminutes) {
            binding.minutes.setVisibility(View.INVISIBLE);
        } else {
            binding.minutes.setVisibility(View.VISIBLE);
        }
        if (!EThours) {
            binding.hours.setVisibility(View.INVISIBLE);
        } else {
            binding.hours.setVisibility(View.VISIBLE);
        }
        if (!TVseconds) {
            binding.secondsLabel.setVisibility(View.INVISIBLE);
        } else {
            binding.secondsLabel.setVisibility(View.VISIBLE);
        }
        if (!TVminutes) {
            binding.minutesLabel.setVisibility(View.INVISIBLE);
        } else {
            binding.minutesLabel.setVisibility(View.VISIBLE);
        }
        if (!TVhours) {
            binding.hoursLabel.setVisibility(View.INVISIBLE);
        } else {
            binding.hoursLabel.setVisibility(View.VISIBLE);
        }
        if (!button) {
            binding.setTime.setVisibility(View.INVISIBLE);
        } else {
            binding.setTime.setVisibility(View.VISIBLE);
        }
    }

    private void setHMSToDefault() {
        binding.hours.setText("0");
        binding.minutes.setText("0");
        binding.seconds.setText("0");
    }


}
package view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.FragmentAdminMainBinding;
import com.google.android.material.navigation.NavigationView;

import viewmodel.AdminMainViewModel;


public class AdminMainFragment extends Fragment {

    public static String TAG_ADMIN_MAIN_FRAGMENT = AdminMainFragment.class.getSimpleName();

    FragmentAdminMainBinding binding;
    AdminMainViewModel adminMainViewModel;

    public AdminMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAdminMainBinding.inflate(inflater, container, false);

        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() { // opens drawer when icon is clicked
            @Override
            public void onClick(View view) {
                binding.drawerLayout.open();
            }
        });

        adminMainViewModel = ViewModelProviders.of(this).get(AdminMainViewModel.class);
        observeViewModel();
        adminMainViewModel.getCurrentDayIncome();
        adminMainViewModel.getComparativePercentage();

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isEnabled()) {

                    if (item.getTitle().equals("Add new menu item")) {
                        jumpToAdminActionFragment("add menu");
                        //here navigate to proper fragment
                        Log.d(TAG_ADMIN_MAIN_FRAGMENT, "item 1 clicked");
                    } else if (item.getTitle().equals("Edit or remove menu item")) {
                        jumpToAdminActionFragment("delete item");
                        Log.d(TAG_ADMIN_MAIN_FRAGMENT, "item 2 clicked");
                    } else {
                        Log.d(TAG_ADMIN_MAIN_FRAGMENT, "item 3 clicked");
                        jumpToAdminActionFragment("timer");
                    }
                    binding.drawerLayout.close();
                }

                return true;
            }
        });

        return binding.getRoot();

    }


    private void observeViewModel() {
        adminMainViewModel.currentDayIncome.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.todayIncome.setText(s);
            }
        });

        adminMainViewModel.comparativeData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.percentage.setText(s);
            }
        });
    }


    private void jumpToAdminActionFragment(String itemClicked) {

        if (getView() != null) {
            Bundle bundle = new Bundle();
            bundle.putString(TAG_ADMIN_MAIN_FRAGMENT, itemClicked);
            Navigation.findNavController(getView()).navigate(R.id.action_adminMainFragment_to_adminActionsFragment, bundle);
        }
    }
}
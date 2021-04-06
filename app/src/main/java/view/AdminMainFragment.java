package view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.FragmentAdminMainBinding;
import com.google.android.material.navigation.NavigationView;

import java.lang.reflect.AccessibleObject;


public class AdminMainFragment extends Fragment {

    public static String TAG_ADMIN_MAIN_FRAGMENT=AdminMainFragment.class.getSimpleName();

    FragmentAdminMainBinding binding;
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

        binding=FragmentAdminMainBinding.inflate(inflater,container,false);

        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() { // opens drawer when icon is clicked
            @Override
            public void onClick(View view) {
                binding.drawerLayout.open();
            }
        });

        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.isEnabled()){

                    if(item.getTitle().equals("Add new menu item")){
                        //here navigate to proper fragment
                        Log.d(TAG_ADMIN_MAIN_FRAGMENT,"item 1 clicked");
                    }else if(item.getTitle().equals("Remove menu item")){
                        Log.d(TAG_ADMIN_MAIN_FRAGMENT,"item 2 clicked");
                    }else{
                        Log.d(TAG_ADMIN_MAIN_FRAGMENT,"item 3 clicked");
                        jumpToAdminActionFragment();
                    }
                    binding.drawerLayout.close();
                }

                return true;
            }
        });

        return binding.getRoot();

    }


    private void jumpToAdminActionFragment(){

        if(getView()!=null){
            Bundle bundle=new Bundle();
            bundle.putString(TAG_ADMIN_MAIN_FRAGMENT,"val");
            Navigation.findNavController(getView()).navigate(R.id.action_adminMainFragment_to_adminActionsFragment,bundle);
        }
    }
}
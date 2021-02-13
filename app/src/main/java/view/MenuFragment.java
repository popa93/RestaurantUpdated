package view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.FragmentMenuBinding;
import com.google.android.material.tabs.TabLayout;

import Util.Constants;
import model.FragmentAdapter;


public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;

    public MenuFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(Constants.ACTION_BAR_MENU_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.show();

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initViewPagedAndTabs();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_action_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.cartActionButton) {

            jumpToOrder();
        }

        return super.onOptionsItemSelected(item);
    }


    private void initViewPagedAndTabs() {

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(Constants.FOOD));

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(Constants.DRINKS));

        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void jumpToOrder() {

        NavDirections action = MenuFragmentDirections.actionMenuFragmentToOrderFragment();
        Navigation.findNavController(getView()).navigate(action);

    }
}
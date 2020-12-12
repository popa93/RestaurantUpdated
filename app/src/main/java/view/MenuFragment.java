package view;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.example.restaurantupdated.R;
import com.google.android.material.tabs.TabLayout;

import Util.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.FragmentAdapter;
import viewmodel.MenuViewModel;


public class MenuFragment extends Fragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    private MenuViewModel menuViewModel;
    private AlertDialog.Builder alertDialogBuilder;

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
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(Constants.ACTION_BAR_MENU_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.show();
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        initViewPagedAndTabs();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obsereViewModel();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_action_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logoutActionButton) {

            menuViewModel.signOut();


        } else if (item.getItemId() == R.id.cartActionButton) {

            jumpToOrder();
        }

        return super.onOptionsItemSelected(item);
    }

    private void obsereViewModel() {

        menuViewModel.menuLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("OK")) {
                    jumpToLogin();
                } else {

                    alertDialogBuilder.setTitle(Constants.ERROR_MSG);

                    alertDialogBuilder.setPositiveButton(Constants.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                }
            }
        });


    }

    private void initViewPagedAndTabs() {

        tabLayout.addTab(tabLayout.newTab().setText(Constants.FOOD));

        tabLayout.addTab(tabLayout.newTab().setText(Constants.DRINKS));

        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void jumpToLogin() {
        NavDirections action = MenuFragmentDirections.actionMenuFragmentToLoginFragment();
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(action);
        }


    }

    private void jumpToOrder() {

        NavDirections action = MenuFragmentDirections.actionMenuFragmentToOrderFragment();
        Navigation.findNavController(getView()).navigate(action);

    }
}
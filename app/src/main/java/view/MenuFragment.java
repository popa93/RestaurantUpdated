package view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.FragmentAdapter;
import viewmodel.MenuViewModel;
import viewmodel.RegisterViewModel;


public class MenuFragment extends Fragment {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    //FirebaseAuth authentication;
    private MenuViewModel menuViewModel;
    private AlertDialog.Builder alertDialogBuilder;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("menu","sunt in onCreate");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // authentication=FirebaseAuth.getInstance();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Menu");
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.show();
        Log.e("menu","sunt in onCreateView");
        View view= inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this,view);
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        initViewPagedAndTabs();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("menu","sunt in onViewCreated");
        obsereViewModel();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_action_button,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.logoutActionButton){

            menuViewModel.signOut();


        }else if(item.getItemId()==R.id.cartActionButton){

            //jump to orderActivity
        }

        return super.onOptionsItemSelected(item);
    }

    private void obsereViewModel(){

        menuViewModel.menuLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("OK")) {
                    jumpToLogin();
                }else{

                    alertDialogBuilder.setTitle("Something went wrong!");

                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                }
            }
        });


    }

    private void initViewPagedAndTabs(){

        tabLayout.addTab(tabLayout.newTab().setText("Food"));

        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));

        FragmentAdapter adapter = new FragmentAdapter(getChildFragmentManager());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.e("tab","tab selectat"+tab.getPosition());

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
}
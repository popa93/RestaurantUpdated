package view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.restaurantupdated.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import viewmodel.LoginViewModel;


public class LoginFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.signUpButton)
    Button register;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.pass)
    EditText pass;

    @BindView(R.id.loginButton)
    Button loginButton;


    private LoginViewModel loginViewModel;

    private GoogleMap map;
    private AlertDialog.Builder alertDialogBuilder;

    public LoginFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alertDialogBuilder = new AlertDialog.Builder(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        observeViewModel();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToRegister();
            }

        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderFragment.orderList.clear();
                loginViewModel.login(email.getText().toString(), pass.getText().toString());
            }
        });
    }


    private void observeViewModel() {

        loginViewModel.firebaseUserMutableLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String firebaseUserEmail) {
                if (firebaseUserEmail != null) {
                    if (firebaseUserEmail.equals("empty")) {

                        alertDialogBuilder.setTitle("Email or password field is empty");

                        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {

                        jumpToMenu();

                    }

                } else if (firebaseUserEmail == null) {

                    alertDialogBuilder.setTitle("task is not succesful");
                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }


    private void jumpToRegister() {

        NavDirections action = LoginFragmentDirections.actionLoginNestedFragmentToRegisterFragment();
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(action);
        }


    }

    private void jumpToMenu() {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToMenuFragment();
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(action);
        }

        /*NavDirections action = LoginFragmentDirections.actionLoginFragmentToFoodFragment();
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(action);
        }*/


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMinZoomPreference(16);
        LatLng location = new LatLng(46.547890, 24.568380);
        map.addMarker(new MarkerOptions().position(location).title("Restaurant"));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
package view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

import com.example.restaurantupdated.R;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import viewmodel.RegisterViewModel;


public class RegisterFragment extends Fragment {

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.coonfirmPassord)
    EditText confirmPassword;

    @BindView(R.id.registerButton)
    Button registerButton;

    private RegisterViewModel registerViewModel;
    private AlertDialog.Builder alertDialogBuilder;
    private FirebaseAuth authentication;

    public RegisterFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.show();

        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        authentication = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerViewModel.register(username.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
            }
        });

        obserViewModel();
    }

    private void obserViewModel() {


        registerViewModel.registerLiveData.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("All fields must be completed")) {
                    alertDialogBuilder.setTitle("All fields must be completed");

                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                } else if (s.equals("Password fields must be identical")) {
                    alertDialogBuilder.setTitle("Password fields must be identical");

                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                } else if (s.equals("Email field bad format")) {
                    alertDialogBuilder.setTitle("Email field bad format");

                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                } else if (s.equals("OK")) {
                    Log.e("register", "OK");

                    authentication.createUserWithEmailAndPassword(username.getText().toString(), password.getText().toString());
                    jumpToMenu();


                } else if (s.equals("Password must contain at least 6 characters")) {

                    alertDialogBuilder.setTitle("Password must contain at least 6 characters");

                    alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                }

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


    }

    private void jumpToMenu() {
        NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToMenuFragment();
        if (getView() != null) {
            Navigation.findNavController(getView()).navigate(action);
        }


    }
}
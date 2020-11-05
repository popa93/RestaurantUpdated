package view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.restaurantupdated.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginNestedFragment extends Fragment implements OnMapReadyCallback {

    @BindView(R.id.signUpButton)
    Button register;

    private GoogleMap map;

    public LoginNestedFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login_nested, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(this);





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                jumpToRegister();
                /*RegisterFragment registerFragment=new RegisterFragment();
                FragmentTransaction transaction=getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer,registerFragment);
                transaction.addToBackStack(registerFragment.getClass().getSimpleName());
                transaction.commit();*/

            }
        });
    }

    private void jumpToRegister(){

        NavDirections action=LoginNestedFragmentDirections.actionLoginNestedFragmentToRegisterFragment();
        if(getView()!=null){
            Navigation.findNavController(getView()).navigate(action);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.setMinZoomPreference(16);
        LatLng location=new LatLng(46.547890,24.568380);
        map.addMarker(new MarkerOptions().position(location).title("Restaurant"));
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
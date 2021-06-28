package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.FragmentAdminActionsBinding;

import java.io.IOException;

import Util.Constants;
import Util.SharedPreferencesHelper;
import model.Pizza;
import viewmodel.AdminActionsViewModel;


public class AdminActionsFragment extends Fragment {    //build all actions layout from 1 fragment(hide and show elements based on admin selection)


    FragmentAdminActionsBinding binding;
    AdminActionsViewModel adminViewModel;
    String data;
    Uri imageUri;
    String pictureName;
    String itemNameInDb;

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
       // SharedPreferencesHelper.getInstance(getActivity()).setDBFoodNodeName(6);
        adminViewModel = ViewModelProviders.of(this).get(AdminActionsViewModel.class);
        addNewItem();

        if (getView() != null) {
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(Navigation.findNavController(getView()).getGraph()).build();
            NavigationUI.setupWithNavController(binding.topAppBar, Navigation.findNavController(getView()), appBarConfiguration);
        }


        data = getArguments().getString(AdminMainFragment.TAG_ADMIN_MAIN_FRAGMENT);
        if (data.equals("add menu")) {

            binding.topAppBar.setTitle(Constants.ADD_NEW_MENU_ITEM);
            setVisibility(false, false, false, false, false, false, false);
            binding.itemPicture.setVisibility(View.VISIBLE);
            binding.itemPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("dada", "on click open gallery to upload photo");
                    openGallery();


                }
            });



        }else if(data.equals("delete item")){
            setVisibility(false, false, false, false, false, false, false);
            Log.d("dada", "sunt in delete");
            binding.topAppBar.setTitle("Delete item");
            binding.ingredientsOfItem.setVisibility(View.INVISIBLE);
            binding.namesOfItem.setVisibility(View.INVISIBLE);
            binding.quantityOfItem.setHint("name of item");
            binding.priceOfItem.setVisibility(View.INVISIBLE);
            binding.addNewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adminViewModel.deleteItem(binding.quantityOfItem.getText().toString(),binding.radioFood.isChecked());
                }
            });
            //adminViewModel.deleteItem(binding.quantityOfItem.getText().toString(),true);
            //delete specific item from db
           // adminViewModel.deleteItem("a",true);
        }
         else {

            binding.topAppBar.setTitle(Constants.CACHE_TIME);
            binding.ingredientsOfItem.setVisibility(View.INVISIBLE);
            binding.namesOfItem.setVisibility(View.INVISIBLE);
            binding.quantityOfItem.setVisibility(View.INVISIBLE);
            binding.priceOfItem.setVisibility(View.INVISIBLE);
            binding.radioGroup.setVisibility(View.INVISIBLE);
            binding.addNewItem.setVisibility(View.INVISIBLE);
            binding.setTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {    //daca nu e scris un spatiu va seta timpul defaul la 5 sec. altfel va seta cat apare inclusiv 0

                    if (TextUtils.isEmpty(binding.hours.getText().toString()) || TextUtils.isEmpty(binding.minutes.getText().toString()) || TextUtils.isEmpty(binding.seconds.getText().toString())) {
                        setHMSToDefault();
                    }
                    /*if(!binding.hours.getText().toString().matches("[0-9]")||!binding.minutes.getText().toString().matches("[0-9]")||!binding.secondsLabel.getText().toString().matches("[0-9]")){
                        Toast.makeText(getContext(), "Only digits allowed!", Toast.LENGTH_LONG).show();
                    }
                    else {*/
                        adminViewModel.onClickSetTime(Integer.parseInt(binding.hours.getText().toString()), Integer.parseInt(binding.minutes.getText().toString()), Integer.parseInt(binding.seconds.getText().toString()));
                   // }

                }
            });
        }


    }

    private void addNewItem(){
        binding.addNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri==null||pictureName==null||pictureName.isEmpty()) {
                    Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_LONG).show();
                    return;
                }
                    adminViewModel.uploadAndGetImageUrl(imageUri,pictureName);
                    observeUri();
            }
        });
    }

    private void observeUri(){
        adminViewModel.storageUri.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String link) {
                if(binding.radioFood.isChecked()){
                    boolean returnValue=adminViewModel.addNewFoodItem(link,binding.ingredientsOfItem.getText().toString(),binding.namesOfItem.getText().toString(),binding.priceOfItem.getText().toString(),binding.quantityOfItem.getText().toString(),itemNameInDb);
                    if (!returnValue){
                        Toast.makeText(getContext(), "Please insert correct numeric values and complete all fields", Toast.LENGTH_LONG).show();
                    }
                }
                else if(binding.radioDrink.isChecked()){
                    boolean returnValue=adminViewModel.addNewDrinkItem(link,binding.ingredientsOfItem.getText().toString(),binding.namesOfItem.getText().toString(),binding.priceOfItem.getText().toString(),binding.quantityOfItem.getText().toString(),itemNameInDb);
                    if (!returnValue){
                        Toast.makeText(getContext(), "Please insert correct numeric values and complete all fields", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }



    private void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

                //set name of image(for firebase storage)
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose a name:");
                View customView=getLayoutInflater().inflate(R.layout.layout_name_of_image_dialog,null);
                builder.setView(customView);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        binding.itemPicture.setImageBitmap(bitmap);
                        EditText editText=customView.findViewById(R.id.dialogPictureName);
                        EditText dbItemName=customView.findViewById(R.id.dialogItemNameInDb);
                        if(!dbItemName.getText().toString().isEmpty()&&!editText.getText().toString().isEmpty()) {
                            itemNameInDb = dbItemName.getText().toString();
                            pictureName = editText.getText().toString();
                        }
                        else{
                            Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_LONG).show();
                            return;
                        }
                        Log.e("sda",pictureName);

                    }
                });
                builder.show();



                imageUri=data.getData();

            } catch (IOException e) {
                e.printStackTrace();
            }
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
        binding.seconds.setText("5");
    }


}
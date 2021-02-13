package view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restaurantupdated.R;
import com.example.restaurantupdated.databinding.FragmentKitchenMainBinding;

import java.util.ArrayList;

import Util.Constants;
import Util.SharedPreferencesHelper;
import model.ICheckedOrderListener;
import model.IExtraInfoOrderClick;
import model.Order;
import model.RecyclerAdapter;
import viewmodel.KitchenViewModel;


public class KitchenMainFragment extends Fragment {

    private final static String TAG = KitchenMainFragment.class.getSimpleName();

    RecyclerAdapter adapter;
    ArrayList<Order> kitchenList = new ArrayList<>();
    FragmentKitchenMainBinding binding;
    KitchenViewModel kitchenViewModel;


    public KitchenMainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kitchenViewModel = ViewModelProviders.of(this).get(KitchenViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentKitchenMainBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        observeViewModel();
        kitchenViewModel.fetchClientOrderList();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.kitchenList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new RecyclerAdapter(toString(), kitchenList, false);
        binding.kitchenList.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                kitchenList.remove(viewHolder.getAdapterPosition());
                kitchenViewModel.deleteOrderFromList(viewHolder.getAdapterPosition());// when i delete item from kitchenlist i have to delete from myList also to sync the lists for positions in list and db
                adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(binding.kitchenList);

        adapter.setCheckedOrderListener(new ICheckedOrderListener() {
            @Override
            public void onClickCheckOrder(int position) {       //writes to db order status checked
                kitchenList.get(position).setOrderStatus(Constants.TRUE);
                kitchenViewModel.writeOrderStatusToDB(kitchenList, position);

            }
        });
        adapter.setExtraInfoOrderClick(new IExtraInfoOrderClick() {
            @Override
            public void onClickViewExtraInfo(int position) { //gets reamarks from clicked order
                Order clickedOrder = kitchenList.get(position);
                showExtraInfoDialog(clickedOrder.getRemarks());
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferencesHelper.getInstance(getContext()).setChildCallFalse();
    }

    private void observeViewModel() {

        kitchenViewModel.clientOrderList.observe(getViewLifecycleOwner(), new Observer<Order>() {
            @Override
            public void onChanged(Order s) {

                kitchenList.add(s);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showExtraInfoDialog(String extraInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setTitle(Constants.EXTRA_INFO);
        builder.setMessage(extraInfo);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @NonNull
    @Override
    public String toString() {
        return Constants.KITCHEN;
    }
}
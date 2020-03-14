package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import apiit.lk.onlinecraftstore.Adapters.CartItems_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.R;


public class CartFragment extends Fragment {

    private CartItems_RecyclerViewAdapter rvAdapter;

    private TextView noOfItems_tv;
    private TextView order_total;
    private Button buy_btn;

    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> imageUrls=new ArrayList<>();
    private ArrayList<String> mDescriptions=new ArrayList<>();
    private ArrayList<String> prices=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_cart, container, false);

        noOfItems_tv=rootView.findViewById(R.id.noOfItemsTV);
        order_total=rootView.findViewById(R.id.orderTotalTV);
        buy_btn=rootView.findViewById(R.id.buyCartBtnTV);



        //After retrieval and setting in the arraylists
        RecyclerView rv=rootView.findViewById(R.id.cartRV);
        rvAdapter=new CartItems_RecyclerViewAdapter(mNames,mDescriptions,prices,imageUrls,getContext());
        rv.setAdapter(rvAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_onClick(v);
            }
        });

        return rootView;
    }

    public void buy_onClick(View view){
//        implementation
    }

    //to be able to show toast message from the non-activity class(CartItems_Recycler view Adapter)
    public void showToast(String message,Context context){
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }


}

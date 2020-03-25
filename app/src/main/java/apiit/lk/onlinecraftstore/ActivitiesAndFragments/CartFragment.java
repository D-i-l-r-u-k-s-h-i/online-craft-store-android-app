package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.Adapters.CartItems_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.DTOs.OrderDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.CraftItemApis;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.OrderApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {

    private CartItems_RecyclerViewAdapter rvAdapter;

    private TextView noOfItems_tv;
    private TextView order_total;
    private Button buy_btn;

    OrderApis orderApis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_cart, container, false);

        noOfItems_tv=rootView.findViewById(R.id.noOfItemsTV);
        order_total=rootView.findViewById(R.id.orderTotalTV);
        buy_btn=rootView.findViewById(R.id.buyCartBtnTV);

        orderApis= ApiClient.getClient().create(OrderApis.class);

        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(getContext()));
        headers.put("content-type", "application/json");

        Call<List<OrderDTO>> call=orderApis.getCartItems(headers);

        call.enqueue(new Callback<List<OrderDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                List<OrderDTO> orderDTOList=response.body();

                noOfItems_tv.setText(orderDTOList.size()==1?orderDTOList.size()+ "item":orderDTOList.size()+ "items");

                //After retrieval and setting in the arraylists
                RecyclerView rv=rootView.findViewById(R.id.cartRV);
                rvAdapter=new CartItems_RecyclerViewAdapter(orderDTOList,getContext());
                rv.setAdapter(rvAdapter);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                Log.d("failed",t.getMessage());
            }
        });

        //api to get/calculate total

        Call<ResponseBody> orderTotalCall=orderApis.getOrderTotal(headers);

        orderTotalCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }
                try {
                    order_total.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("failed",t.getMessage());
            }
        });

        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> buyOrderCall=orderApis.buyCartOrder(headers);

                buyOrderCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Log.d("responseCode", String.valueOf(response.code()));
                            return;
                        }

                        try {
                            showToast(response.body().string(),getContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("failed",t.getMessage());
                    }
                });
            }
        });

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        return rootView;
    }

    //to be able to show toast message from the non-activity class(CartItems_Recycler view Adapter)
    public void showToast(String message,Context context){
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            double price = intent.getDoubleExtra("price",0);
            double currentPrice=Double.valueOf(String.valueOf(order_total.getText()));

            int currentItemsNo=Integer.valueOf(String.valueOf(noOfItems_tv.getText().charAt(0)));

            order_total.setText(String.valueOf(currentPrice-Double.valueOf(String.valueOf(price))));
            noOfItems_tv.setText(String.valueOf(currentItemsNo-1));
        }
    };

}

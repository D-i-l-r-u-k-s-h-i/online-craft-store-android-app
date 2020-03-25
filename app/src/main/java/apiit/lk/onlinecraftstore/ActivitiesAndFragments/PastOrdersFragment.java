package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.Adapters.AllOrders_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.Adapters.CraftCreatorsPurchasedFrom_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.Adapters.CraftsPurchased_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.DTOs.CraftCreator;
import apiit.lk.onlinecraftstore.DTOs.CraftItem;
import apiit.lk.onlinecraftstore.DTOs.UserOrdersDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.OrderApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PastOrdersFragment extends Fragment {

    AllOrders_RecyclerViewAdapter allOrders_recyclerViewAdapter;
    CraftsPurchased_RecyclerViewAdapter craftsPurchased_recyclerViewAdapter;
    CraftCreatorsPurchasedFrom_RecyclerViewAdapter craftCreatorsPurchasedFrom_recyclerViewAdapter;

    OrderApis orderApis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_past_orders, container, false);

        TextView ordersTV=rootView.findViewById(R.id.allOrdersTV);
        TextView craftsTV=rootView.findViewById(R.id.craftsTV);
        TextView creatorsTV=rootView.findViewById(R.id.creatorsTV);

        orderApis= ApiClient.getClient().create(OrderApis.class);

        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(getContext()));
        headers.put("content-type", "application/json");

        Call<List<UserOrdersDTO>> call=orderApis.getPastOrders(headers);

        call.enqueue(new Callback<List<UserOrdersDTO>>() {
            @Override
            public void onResponse(Call<List<UserOrdersDTO>> call, Response<List<UserOrdersDTO>> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                ordersTV.setText("All Orders ("+response.body().size()+")");

                //first rv
                RecyclerView rv1 = rootView.findViewById(R.id.allOrdersRV);

                if(response.body().size()>0){
                    rv1.setVisibility(View.VISIBLE);
                }

                int numberOfColumns = 1;
                rv1.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                allOrders_recyclerViewAdapter= new AllOrders_RecyclerViewAdapter(getActivity(),response.body());
//        allOrders_recyclerViewAdapter.setClickListener(this);
                rv1.setAdapter(allOrders_recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<UserOrdersDTO>> call, Throwable t) {
                Log.d("failed",t.getMessage());
            }
        });

        Call<List<CraftItem>> craftItemsCall=orderApis.alreadyPurchasedCraftItems(headers);

        craftItemsCall.enqueue(new Callback<List<CraftItem>>() {
            @Override
            public void onResponse(Call<List<CraftItem>> call, Response<List<CraftItem>> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                craftsTV.setText("All Crafts purchased by you so far.. ("+response.body().size()+")");

                //second rv
                RecyclerView rv2=rootView.findViewById(R.id.craftsPurchasedRV);

                if(response.body().size()>0){
                    rv2.setVisibility(View.VISIBLE);
                }

                craftsPurchased_recyclerViewAdapter=new CraftsPurchased_RecyclerViewAdapter(response.body(),getContext());
                rv2.setAdapter(craftsPurchased_recyclerViewAdapter);
                rv2.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<List<CraftItem>> call, Throwable t) {
                Log.d("failed",t.getMessage());
            }
        });

        Call<List<CraftCreator>> craftCreatorsCall=orderApis.creatorsTheUserPurchasedFrom(headers);

        craftCreatorsCall.enqueue(new Callback<List<CraftCreator>>() {
            @Override
            public void onResponse(Call<List<CraftCreator>> call, Response<List<CraftCreator>> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                creatorsTV.setText("All Craft creators you have purchased from so far.. ("+String.valueOf(response.body().size())+")");

                //third rv
                RecyclerView rv3=rootView.findViewById(R.id.creatorsRV);
                if(response.body().size()>0){
                    rv3.setVisibility(View.VISIBLE);
                }

                craftCreatorsPurchasedFrom_recyclerViewAdapter=new CraftCreatorsPurchasedFrom_RecyclerViewAdapter(response.body(),getContext());
                rv3.setAdapter(craftCreatorsPurchasedFrom_recyclerViewAdapter);
                rv3.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure(Call<List<CraftCreator>> call, Throwable t) {
                Log.d("failed",t.getMessage());
            }
        });

        return rootView;
    }

}

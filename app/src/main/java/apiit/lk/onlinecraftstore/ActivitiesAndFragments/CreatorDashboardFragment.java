package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.Adapters.CraftItems_Creator_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.Adapters.CraftItems_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.Adapters.ShowReviews_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.DTOs.ItemDTO;
import apiit.lk.onlinecraftstore.DTOs.ReviewDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.CraftItemApis;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.RatingsReviewsApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatorDashboardFragment extends Fragment {

    private CraftItemApis craftItemApis;
    private RatingsReviewsApis ratingsReviewsApis;

    private LinearLayout linearLayoutBar;
    TextView ratings_tv;
    TextView reviewCount_tv;
    Button previous_btn;
    Button next_btn;

    int counter=0;
    long creatorId=0;
    double rating=0;

    ShowReviews_RecyclerViewAdapter reviewsRV_adapter;
    CraftItems_Creator_RecyclerViewAdapter creatorCraftItemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_creator_dashboard, container, false);

        linearLayoutBar=rootView.findViewById(R.id.hor_lin_layout);
        ratings_tv=rootView.findViewById(R.id.creatorRatingTV);
        reviewCount_tv=rootView.findViewById(R.id.reviewsCountTV);

        previous_btn=rootView.findViewById(R.id.previousBtn);
        next_btn=rootView.findViewById(R.id.nextBtn);

        craftItemApis= ApiClient.getClient().create(CraftItemApis.class);

        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(getContext()));
        headers.put("content-type", "application/json");

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            creatorId=bundle.getLong("id");
            rating=bundle.getDouble("rating");
        }

        if(creatorId!=0){
            linearLayoutBar.setVisibility(View.GONE);
        }

        ratings_tv.setText("Rating: "+String.valueOf(rating));

        if(counter==0){
            previous_btn.setEnabled(false);

            if(creatorId==0){
                callApi(rootView,craftItemApis.getCraftItemsOfCreator(headers,0,0));
            }
            else {
                callApi(rootView,craftItemApis.getCraftItemsOfCreator(headers,creatorId,0));
            }

        }

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++counter;
                if(creatorId==0){
                    callApi(rootView,craftItemApis.getCraftItemsOfCreator(headers,0,counter));
                }
                else {
                    callApi(rootView,craftItemApis.getCraftItemsOfCreator(headers,creatorId,counter));
                }
            }
        });

        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --counter;
                if(creatorId==0){
                    callApi(rootView,craftItemApis.getCraftItemsOfCreator(headers,0,counter));
                }
                else {
                    callApi(rootView,craftItemApis.getCraftItemsOfCreator(headers,creatorId,counter));
                }
            }
        });

        ratingsReviewsApis=ApiClient.getClient().create(RatingsReviewsApis.class);

        Call<List<ReviewDTO>> call;

        if(creatorId==0) {
            call= ratingsReviewsApis.getCraftReviews(headers, 0);
        }
        else{
            call=ratingsReviewsApis.getCraftReviews(headers, creatorId);
        }

        call.enqueue(new Callback<List<ReviewDTO>>() {
            @Override
            public void onResponse(Call<List<ReviewDTO>> call, Response<List<ReviewDTO>> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                List<ReviewDTO> reviewDTOS=response.body();
                reviewCount_tv.setText("("+reviewDTOS.size()+")");

                RecyclerView rv=rootView.findViewById(R.id.showReviewsRV);
                reviewsRV_adapter=new ShowReviews_RecyclerViewAdapter(reviewDTOS,getContext());
                rv.setAdapter(reviewsRV_adapter);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Call<List<ReviewDTO>> call, Throwable t) {

            }
        });

        return rootView;
    }

    //common method to call apis
    public void callApi(View rootView, Call<List<ItemDTO>> call){

        call.enqueue(new Callback<List<ItemDTO>>() {
            @Override
            public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                List<ItemDTO> crafts=response.body();

                rating=crafts.get(0).getCreator().getOverallRating();

                if(counter==crafts.get(0).getNoOfPages()){
                    next_btn.setVisibility(View.GONE);
                }

                if(crafts.get(0).getNoOfPages()<=8){
                    next_btn.setVisibility(View.GONE);
                    previous_btn.setVisibility(View.GONE);
                }

                RecyclerView recyclerView = rootView.findViewById(R.id.craftItemsRV);

                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                creatorCraftItemsAdapter = new CraftItems_Creator_RecyclerViewAdapter(getActivity(),crafts);
//        adapter.setClickListener(this);
                recyclerView.setAdapter(creatorCraftItemsAdapter);

            }

            @Override
            public void onFailure(Call<List<ItemDTO>> call, Throwable t) {
                Log.d("failed",t.getMessage());
            }
        });
    }
}

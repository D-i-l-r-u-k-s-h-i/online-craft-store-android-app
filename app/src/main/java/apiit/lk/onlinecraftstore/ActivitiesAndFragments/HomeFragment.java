package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.Adapters.CraftItems_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.DTOs.ItemDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.CraftItemApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private CraftItemApis craftItemApis;

    private Button scrapbooking;
    private Button kidsCraft;
    private Button drawings;
    private Button embroidary;
    private Button sewingNquilting;
    private Button crotchetKnitting;
    private Button beading;
    private Button woodcraft;
    private Button quilling;
    private Button other;
    private Button all;

    private ProgressBar progressBar;

    CraftItems_RecyclerViewAdapter adapter;
    private SearchView searchView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_home, container, false);

        craftItemApis= ApiClient.getClient().create(CraftItemApis.class);

        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer "+SaveSharedPreferenceInstance.getAuthToken(getContext()));
        headers.put("content-type", "application/json");

        callApi(rootView,craftItemApis.getMostRecentCraft(headers));

        this.progressBar=rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        this.scrapbooking=rootView.findViewById(R.id.view1);
        this.kidsCraft=rootView.findViewById(R.id.view2);
        this.drawings=rootView.findViewById(R.id.view3);
        this.embroidary=rootView.findViewById(R.id.view4);
        this.sewingNquilting=rootView.findViewById(R.id.view5);
        this.crotchetKnitting=rootView.findViewById(R.id.view6);
        this.beading=rootView.findViewById(R.id.view7);
        this.woodcraft=rootView.findViewById(R.id.view8);
        this.quilling=rootView.findViewById(R.id.view9);
        this.other=rootView.findViewById(R.id.view10);
        this.all=rootView.findViewById(R.id.view11);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi(rootView,craftItemApis.getAllCraft(headers));

            }
        });

        scrapbooking.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Scrapbooking"));

        });
        kidsCraft.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Kids craft"));

        });
        drawings.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Drawings"));

        });
        embroidary.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Embroidery"));

        });
        sewingNquilting.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Sewing and quilting"));

        });
        crotchetKnitting.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Crochet & knitting"));

        });
        beading.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Beading"));

        });
        woodcraft.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Woodcraft"));

        });
        quilling.setOnClickListener((View v)-> {
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Quilling"));

        });
        other.setOnClickListener((View v)->{
                callApi(rootView,craftItemApis.getItemsByCategory(headers,"Other"));

        });

        SearchView searchView=rootView.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                callApi(rootView,craftItemApis.searchCrafts(headers,newText.trim()));
                return false;
            }
        });


        return rootView;
    }

    //implement setClickListener if clicking on item

    //common method to call apis
    public void callApi(View rootView,Call<List<ItemDTO>> call){

        call.enqueue(new Callback<List<ItemDTO>>() {
            @Override
            public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }
                progressBar.setVisibility(View.INVISIBLE);
                List<ItemDTO> allCrafts=response.body();
                RecyclerView recyclerView = rootView.findViewById(R.id.craftItemsRV);

                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                adapter = new CraftItems_RecyclerViewAdapter(getActivity(), allCrafts);
//        adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<ItemDTO>> call, Throwable t) {
                Log.d("failed",t.getMessage());
            }
        });
    }

}

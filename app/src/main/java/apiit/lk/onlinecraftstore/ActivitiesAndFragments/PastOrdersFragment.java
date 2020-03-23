package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import apiit.lk.onlinecraftstore.Adapters.AllOrders_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.Adapters.CraftCreatorsPurchasedFrom_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.Adapters.CraftsPurchased_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.R;

public class PastOrdersFragment extends Fragment {

    AllOrders_RecyclerViewAdapter allOrders_recyclerViewAdapter;
    CraftsPurchased_RecyclerViewAdapter craftsPurchased_recyclerViewAdapter;
    CraftCreatorsPurchasedFrom_RecyclerViewAdapter craftCreatorsPurchasedFrom_recyclerViewAdapter;


    //samples
    String[] data={"test Data","Change later"};
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<String> imageUrls=new ArrayList<>();
    private ArrayList<String> emails=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_past_orders, container, false);

        //first rv
        RecyclerView rv1 = rootView.findViewById(R.id.allOrdersRV);
        int numberOfColumns = 1;
        rv1.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        allOrders_recyclerViewAdapter= new AllOrders_RecyclerViewAdapter(getActivity(), data);
//        allOrders_recyclerViewAdapter.setClickListener(this);
        rv1.setAdapter(allOrders_recyclerViewAdapter);

        //second rv
        RecyclerView rv2=rootView.findViewById(R.id.craftsPurchasedRV);
        craftsPurchased_recyclerViewAdapter=new CraftsPurchased_RecyclerViewAdapter(mNames,imageUrls,getContext());
        rv2.setAdapter(craftsPurchased_recyclerViewAdapter);
        rv2.setLayoutManager(new LinearLayoutManager(getContext()));

        //third rv
        RecyclerView rv3=rootView.findViewById(R.id.creatorsRV);
        craftCreatorsPurchasedFrom_recyclerViewAdapter=new CraftCreatorsPurchasedFrom_RecyclerViewAdapter(mNames,emails,getContext());
        rv3.setAdapter(craftCreatorsPurchasedFrom_recyclerViewAdapter);
        rv3.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

}

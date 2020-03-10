package apiit.lk.onlinecraftstore.SupportClasses;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import apiit.lk.onlinecraftstore.Adapters.CraftItems_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.R;

public class HomeFragment extends Fragment {
    CraftItems_RecyclerViewAdapter adapter;
    String[] data = {"Test1", "Test2","Test3","Test4"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_home, container, false);
        String[] images={"test1","test2","test3","test4"};

        RecyclerView recyclerView = rootView.findViewById(R.id.craftItemsRV);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        adapter = new CraftItems_RecyclerViewAdapter(getActivity(), data,images);
//        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

//implement setClickListener if clicking on item
}

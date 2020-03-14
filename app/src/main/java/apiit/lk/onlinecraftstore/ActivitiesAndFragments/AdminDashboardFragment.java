package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import apiit.lk.onlinecraftstore.R;

public class AdminDashboardFragment extends Fragment {

    Button addAdminBtn;
    ListView users_lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        users_lv=rootView.findViewById(R.id.usersLV);

        addAdminBtn=rootView.findViewById(R.id.addAdminBtn);
        addAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAdminBtn_onClick(v);
            }
        });

        String[] orders={"something1", "something2", "something3"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,orders);
        users_lv.setAdapter(adapter);

        return rootView;
    }

    public void addAdminBtn_onClick(View view){
//        implementation
    }
}

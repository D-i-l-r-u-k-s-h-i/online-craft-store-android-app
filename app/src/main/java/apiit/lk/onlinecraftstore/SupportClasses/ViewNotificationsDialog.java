package apiit.lk.onlinecraftstore.SupportClasses;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import apiit.lk.onlinecraftstore.Adapters.ViewNotifications_RecyclerViewAdapter;
import apiit.lk.onlinecraftstore.DTOs.CreatorCraftOrderDTO;
import apiit.lk.onlinecraftstore.DTOs.NotificationsDTO;
import apiit.lk.onlinecraftstore.R;

public class ViewNotificationsDialog extends AppCompatDialogFragment {
    ViewNotifications_RecyclerViewAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.layout_orders_dialog,null);

        RecyclerView recyclerView =view.findViewById(R.id.viewDialogRV);

        Bundle bundle=getArguments();
        //get List of orders
        List<NotificationsDTO> dtoList=bundle.getParcelableArrayList("notification_dtos");

        adapter=new ViewNotifications_RecyclerViewAdapter(getContext(),dtoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        builder.setView(view)
                .setTitle("Notifications")
                .setNegativeButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}

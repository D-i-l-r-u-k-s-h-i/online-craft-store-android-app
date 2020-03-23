package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import apiit.lk.onlinecraftstore.DTOs.NotificationsDTO;
import apiit.lk.onlinecraftstore.R;

public class ViewNotifications_RecyclerViewAdapter extends RecyclerView.Adapter<ViewNotifications_RecyclerViewAdapter.ViewHolder>{
    private List<NotificationsDTO> mData;
    private LayoutInflater mInflater;
    private CraftItems_RecyclerViewAdapter.ItemClickListener mClickListener;
    private final Context mContext;

    public ViewNotifications_RecyclerViewAdapter(Context context, List<NotificationsDTO> data) {
        this.mContext=context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewNotifications_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.notifications_rv, parent, false);
        return new ViewNotifications_RecyclerViewAdapter.ViewHolder(view);
    }
    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewNotifications_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.notification_tv.setText(mData.get(position).getNotification());
        holder.date_tv.setText(mData.get(position).getDatetime());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    } // total number of cells

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView notification_tv;
        TextView date_tv;


        ViewHolder(View itemView) {
            super(itemView);
            notification_tv = itemView.findViewById(R.id.notificationDescTV);
            date_tv=itemView.findViewById(R.id.dateTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenient method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getNotification();
    }

    // allows clicks events to be caught
    public void setClickListener(CraftItems_RecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // HomeFragment will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}

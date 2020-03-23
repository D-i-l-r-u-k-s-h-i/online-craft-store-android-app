package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import apiit.lk.onlinecraftstore.DTOs.CreatorCraftOrderDTO;
import apiit.lk.onlinecraftstore.R;

public class ViewCreatorOrders_RecyclerViewAdapter extends RecyclerView.Adapter<ViewCreatorOrders_RecyclerViewAdapter.ViewHolder>{
    private List<CreatorCraftOrderDTO> mData;
    private LayoutInflater mInflater;
    private CraftItems_RecyclerViewAdapter.ItemClickListener mClickListener;
    private final Context mContext;

    public ViewCreatorOrders_RecyclerViewAdapter(Context context, List<CreatorCraftOrderDTO> data) {
        this.mContext=context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewCreatorOrders_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.orders_rv, parent, false);
        return new ViewCreatorOrders_RecyclerViewAdapter.ViewHolder(view);
    }
    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewCreatorOrders_RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.purchaseDate_tv.setText(mData.get(position).getPurchaseDate());
        holder.orderItems_tv.setText(mData.get(position).getCraftName()+"*"+mData.get(position).getQuantity());
        holder.customerName_tv.setText(mData.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    } // total number of cells

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView purchaseDate_tv;
        TextView orderItems_tv;
        TextView customerName_tv;


        ViewHolder(View itemView) {
            super(itemView);
            purchaseDate_tv = itemView.findViewById(R.id.purchaseDateTV);
            orderItems_tv=itemView.findViewById(R.id.orderItemsTV);
            customerName_tv = itemView.findViewById(R.id.customerTV);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenient method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getCraftName();
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

package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apiit.lk.onlinecraftstore.DTOs.OrderCraftItem;
import apiit.lk.onlinecraftstore.DTOs.UserOrdersDTO;
import apiit.lk.onlinecraftstore.R;

public class AllOrders_RecyclerViewAdapter extends RecyclerView.Adapter<AllOrders_RecyclerViewAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    private List<UserOrdersDTO> mData;
    private Context mContext;

    public AllOrders_RecyclerViewAdapter(Context context,List<UserOrdersDTO> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext=context;
    }

    @NonNull
    @Override
    public AllOrders_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.all_orders_rv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrders_RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.purchaseDate_tv.setText(mData.get(position).getPurchaseDate());
        holder.orderStatus_tv.setText(mData.get(position).getOrderStatus());
        holder.orderTotal_tv.setText("Total: Rs."+String.valueOf(mData.get(position).getOrderTotal()));

        //for now hardcoding the listview
        List<String> orders=new ArrayList<>();
        for (OrderCraftItem ci:mData.get(position).getOrderItemsList()) {
            orders.add(ci.getCraftItem().getCiName()+"  "+ci.getQuantity()+"*"+ci.getCraftItem().getCiPrice()+"   "+ci.getStatus());
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,orders);
        holder.orderItems_lv.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView purchaseDate_tv;
        TextView orderTotal_tv;
        TextView orderStatus_tv;
        ListView orderItems_lv;


        ViewHolder(View itemView) {
            super(itemView);
            purchaseDate_tv = itemView.findViewById(R.id.purchaseDateTV);
            orderTotal_tv = itemView.findViewById(R.id.orderTotalTV);
            orderStatus_tv = itemView.findViewById(R.id.orderStatusTV);
            orderItems_lv = itemView.findViewById(R.id.craftsOrderedLV);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}

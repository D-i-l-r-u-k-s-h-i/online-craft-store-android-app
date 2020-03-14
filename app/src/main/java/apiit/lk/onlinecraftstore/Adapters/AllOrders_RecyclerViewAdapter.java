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

import apiit.lk.onlinecraftstore.R;

public class AllOrders_RecyclerViewAdapter extends RecyclerView.Adapter<AllOrders_RecyclerViewAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    private String[] mData;

    public AllOrders_RecyclerViewAdapter(Context context,String[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public AllOrders_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.all_orders_rv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrders_RecyclerViewAdapter.ViewHolder holder, int i) {
        holder.purchaseDate_tv.setText(mData[i]);
    }

    @Override
    public int getItemCount() {
        return mData.length;
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

            //for now hardcoding the listview
            String[] orders={"something1", "something2", "something3"};

            ArrayAdapter<String> adapter=new ArrayAdapter<String>(itemView.getContext(),android.R.layout.simple_list_item_1,orders);
            orderItems_lv.setAdapter(adapter);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}

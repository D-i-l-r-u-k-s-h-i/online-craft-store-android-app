package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.CreatorCraftOrderDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.CraftItemApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCreatorOrders_RecyclerViewAdapter extends RecyclerView.Adapter<ViewCreatorOrders_RecyclerViewAdapter.ViewHolder>{
    private List<CreatorCraftOrderDTO> mData;
    private LayoutInflater mInflater;
    private CraftItems_RecyclerViewAdapter.ItemClickListener mClickListener;
    private final Context mContext;

    CraftItemApis craftItemApis;

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

        holder.delevered_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call api to change delevery status
                craftItemApis= ApiClient.getClient().create(CraftItemApis.class);

                Map<String,String> headers=new HashMap<>();
                headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(mContext));
                headers.put("content-type", "application/json");

                Call<ResponseBody> call=craftItemApis.deleverItem(headers,mData.get(position).getOrderCraftItemId());

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Log.d("responseCode", String.valueOf(response.code()));
                            return;
                        }

                        try {
                            showToast(response.body().string(),mContext);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        mData.remove(position);

                        notifyItemRemoved(position);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("failed",t.getMessage());
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    } // total number of cells

    public void showToast(String message,Context context){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView purchaseDate_tv;
        TextView orderItems_tv;
        TextView customerName_tv;
        Button delevered_btn;

        ViewHolder(View itemView) {
            super(itemView);
            purchaseDate_tv = itemView.findViewById(R.id.purchaseDateTV);
            orderItems_tv=itemView.findViewById(R.id.orderItemsTV);
            customerName_tv = itemView.findViewById(R.id.customerTV);
            delevered_btn=itemView.findViewById(R.id.deliverBtn);

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

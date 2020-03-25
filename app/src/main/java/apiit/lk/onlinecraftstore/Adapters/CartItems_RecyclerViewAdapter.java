package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.OrderDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.OrderApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartItems_RecyclerViewAdapter extends RecyclerView.Adapter<CartItems_RecyclerViewAdapter.ViewHolder>{

    private List<OrderDTO> mData;
    private OrderApis orderApis;

    private Context mContext;

    public CartItems_RecyclerViewAdapter(List<OrderDTO> orderData, Context mContext) {
        this.mData=orderData;
        this.mContext = mContext;
    }

public class ViewHolder extends RecyclerView.ViewHolder{

    ImageView productImage;
    TextView name_tv;
    TextView availability_tv;
    TextView description_tv;
    TextView price_tv;
    ConstraintLayout parent_layout;
    ImageView removeItem_iv;
    NumberPicker quantityNP;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        productImage=itemView.findViewById(R.id.c_image2);
        name_tv=itemView.findViewById(R.id.nameTV2);
        description_tv=itemView.findViewById(R.id.descriptionTV2);
        price_tv=itemView.findViewById(R.id.priceTV2);
        removeItem_iv=itemView.findViewById(R.id.removeItemIV);
        quantityNP=itemView.findViewById(R.id.numberPicker);
        availability_tv=itemView.findViewById(R.id.availabilityCartTV);

        parent_layout=itemView.findViewById(R.id.parentLayoutCart);

    }
}

    @NonNull
    @Override
    public CartItems_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_rv,parent,false);
        CartItems_RecyclerViewAdapter.ViewHolder viewHolder=new CartItems_RecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartItems_RecyclerViewAdapter.ViewHolder viewHolder,
                                 final int position) {
        viewHolder.name_tv.setText(mData.get(position).getCraftItem().getCiName());
        viewHolder.description_tv.setText(mData.get(position).getCraftItem().getShortDescription());
        viewHolder.price_tv.setText(String.valueOf(mData.get(position).getCraftItem().getCiPrice()));
        viewHolder.availability_tv.setText(mData.get(position).getCraftItem().isAvailabilityStatus()?"Available":"Not Available");

        byte[] decodedString = Base64.decode(mData.get(position).getCraftItem().getImgFile() , Base64.DEFAULT);
        Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

        viewHolder.productImage.setImageBitmap(decodedByte);

        viewHolder.quantityNP.setMinValue(1);
        viewHolder.quantityNP.setMaxValue(mData.get(position).getCraftItem().getItemQuantity());
        viewHolder.quantityNP.setValue(mData.get(position).getQuantity());

        viewHolder.quantityNP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //api on quantity change
                orderApis= ApiClient.getClient().create(OrderApis.class);

                OrderDTO quantityUpdatedDTO=mData.get(position);
                quantityUpdatedDTO.setQuantity(newVal);
                quantityUpdatedDTO.setCraftId(mData.get(position).getCraftItem().getCraftId());

                Map<String,String> headers=new HashMap<>();
                headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(mContext));
                headers.put("content-type", "application/json");

                Call<ResponseBody> call=orderApis.changeQuantity(headers,quantityUpdatedDTO);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Log.d("responseCode", String.valueOf(response.code()));
                            return;
                        }

                        try {
                            showToast(mData.get(position).getCraftItem().getCiName()+" Item "+response.body().string()+" to "+newVal,mContext);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("failed",t.getMessage());
                    }
                });

            }
        });

        //on click of the remove icon in the recyclerview item,
        viewHolder.removeItem_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //product order will be detleted from the database
                orderApis= ApiClient.getClient().create(OrderApis.class);

                Map<String,String> headers=new HashMap<>();
                headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(mContext));
                headers.put("content-type", "application/json");

                Call<ResponseBody> call=orderApis.removeFromCart(headers,mData.get(position).getCraftItem().getCraftId());

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Log.d("responseCode", String.valueOf(response.code()));
                            return;
                        }

                        try {
                            showToast(mData.get(position).getCraftItem().getCiName()+" "+response.body().string(),mContext);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent("custom-message");
                        intent.putExtra("price",mData.get(position).getCraftItem().getCiPrice()*mData.get(position).getQuantity());
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

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
    }

    public void showToast(String message,Context context){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }
}

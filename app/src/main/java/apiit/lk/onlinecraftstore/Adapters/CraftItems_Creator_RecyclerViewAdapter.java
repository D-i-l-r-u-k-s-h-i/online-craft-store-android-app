package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.ActivitiesAndFragments.HomeActivity;
import apiit.lk.onlinecraftstore.DTOs.AlertMessageDTO;
import apiit.lk.onlinecraftstore.DTOs.ItemDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.CraftItemApis;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.OrderApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.BuyItemDialog;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import apiit.lk.onlinecraftstore.SupportClasses.UpdateCraftDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CraftItems_Creator_RecyclerViewAdapter extends RecyclerView.Adapter<CraftItems_Creator_RecyclerViewAdapter.ViewHolder>{

    private List<ItemDTO> mData;
    private LayoutInflater mInflater;
    private CraftItems_RecyclerViewAdapter.ItemClickListener mClickListener;
    private Context mContext;

    private OrderApis orderApis;
    private CraftItemApis craftItemApis;

    ItemDTO itemAtCurrentPosition;

    public CraftItems_Creator_RecyclerViewAdapter(Context context, List<ItemDTO> data) {
        this.mContext=context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public CraftItems_Creator_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.craft_items_rv, parent, false);
        return new CraftItems_Creator_RecyclerViewAdapter.ViewHolder(view);
    }
    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull CraftItems_Creator_RecyclerViewAdapter.ViewHolder holder, int position) {

        String currentUserRole=SaveSharedPreferenceInstance.getRole(mContext);
        String currentUserName=SaveSharedPreferenceInstance.getUsername(mContext);

        if(currentUserRole.equals("ROLE_CRAFT_CREATOR") && currentUserName.equals(mData.get(position).getCreator().getCreatorName())){
            holder.edit_iv.setVisibility(View.VISIBLE);
            holder.delete_iv.setVisibility(View.VISIBLE);
            holder.buy_btn.setVisibility(View.GONE);
            holder.addToCart_iv.setVisibility(View.GONE);
        }

        holder.creator_tv.setVisibility(View.GONE);
        holder.craftName_tv.setText(mData.get(position).getCiName());
        holder.creator_tv.setText("Creator: "+mData.get(position).getCreator().getCreatorName());
        holder.shortDiscription_tv.setText(mData.get(position).getShortDescription());
        holder.longDiscription_tv.setText(mData.get(position).getLongDescription());
        holder.price_tv.setText("Rs."+mData.get(position).getCiPrice().toString());
        holder.availability_tv.setText(mData.get(position).getAvailabilityStatus()?"Available":"Not Available");

        int available_qty=mData.get(position).getItemQuantity();


        byte[] decodedString = Base64.decode(mData.get(position).getImgFile() , Base64.DEFAULT);
        Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

        holder.myImageView.setImageBitmap(decodedByte);

        holder.buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAtCurrentPosition=mData.get(position);
                openBuyItemDialog(decodedString,mData.get(position).getCiName(),"Rs."+mData.get(position).getCiPrice().toString(),available_qty);
            }
        });

        holder.addToCart_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderApis= ApiClient.getClient().create(OrderApis.class);

                Map<String,String> headers=new HashMap<>();
                headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(mContext));
                headers.put("content-type", "application/json");

                Call<ResponseBody> call=orderApis.addToCart(headers,mData.get(position).getCraftId());

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful()){
                            Log.d("responseCode", String.valueOf(response.code()));
                            return;
                        }

                        try {
                            showToast(mData.get(position).getCiName()+" "+response.body().string(),mContext);
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

        holder.delete_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=new AlertDialog.Builder(mContext)
                        .setTitle("Delete Craft")
                        .setMessage("Are you sure you want to delete this craft item from your collection?")
                        .setPositiveButton("Yes",null)
                        .setNegativeButton("No",null)
                        .show();

                Button positiveButton=alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        craftItemApis=ApiClient.getClient().create(CraftItemApis.class);

                        Map<String,String> headers=new HashMap<>();
                        headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(mContext));
                        headers.put("content-type", "application/json");

                        Call<AlertMessageDTO> call=craftItemApis.deleteItem(headers,mData.get(position).getCraftId());

                        call.enqueue(new Callback<AlertMessageDTO>() {
                            @Override
                            public void onResponse(Call<AlertMessageDTO> call, Response<AlertMessageDTO> response) {
                                if(!response.isSuccessful()){
                                    Log.d("responseCode", String.valueOf(response.code()));
                                    return;
                                }

                                showToast(mData.get(position).getCiName()+"- "+response.body().getMessage(),mContext);

                                if(response.body().getStatus().equals("DELETE")){
                                    mData.remove(position);
                                    notifyItemRemoved(position);
                                }

                            }

                            @Override
                            public void onFailure(Call<AlertMessageDTO> call, Throwable t) {
                                Log.d("failed",t.getMessage());
                            }
                        });

                        //after calling the
                        alertDialog.dismiss();
                    }
                });
            }
        });

        holder.edit_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemAtCurrentPosition=mData.get(position);

                openUpdateCraftDialog();

            }
        });

        holder.share_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");

                //sharing the name of the product and the url of the picture here
                String shareString_name=mData.get(position).getCiName();
                String shareString_longdesc=mData.get(position).getLongDescription();
                String shareString_shortdesc=mData.get(position).getShortDescription();

                String shareString_price=mData.get(position).getCiPrice().toString();


                //parsing the content to the app using putExtra
                intent.putExtra(Intent.EXTRA_SUBJECT,"Check this out from the Craft Store\uD83C\uDF40");
                intent.putExtra(Intent.EXTRA_TEXT,"Check this out from the Craft Store\uD83C\uDF40 \n"+shareString_name + "\n" +shareString_shortdesc+" ,"+shareString_longdesc+"\nRs."+" "+shareString_price);

//                intent.putExtra(Intent.EXTRA_STREAM, "data:image/png;base64,"+mData.get(position).getImgFile());

                mContext.startActivity(Intent.createChooser(intent,"Share using"));
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

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView craftName_tv;
        ImageView myImageView;
        TextView creator_tv;
        TextView shortDiscription_tv;
        TextView longDiscription_tv;
        TextView price_tv;
        TextView availability_tv;

        ImageView addToCart_iv;
        ImageView edit_iv;
        ImageView delete_iv;
        Button buy_btn;
        ImageView share_icon;

        ViewHolder(View itemView) {
            super(itemView);
            craftName_tv = itemView.findViewById(R.id.craftNameTV);
            myImageView=itemView.findViewById(R.id.mImageIV);
            creator_tv = itemView.findViewById(R.id.creatorTV);
            shortDiscription_tv = itemView.findViewById(R.id.shortDescriptionTV);
            longDiscription_tv = itemView.findViewById(R.id.longDescriptionTV);
            price_tv = itemView.findViewById(R.id.priceTV);
            availability_tv = itemView.findViewById(R.id.availabilityTV);

            addToCart_iv=itemView.findViewById(R.id.addToCartBtn);
            edit_iv=itemView.findViewById(R.id.editBtn);
            delete_iv=itemView.findViewById(R.id.deleteBtn);
            buy_btn=itemView.findViewById(R.id.buyBtn);

            share_icon=itemView.findViewById(R.id.shareIcon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenient method for getting data at click position
    String getItem(int id) {
        return mData.get(id).getCiName();
    }

    // allows clicks events to be caught
    public void setClickListener(CraftItems_RecyclerViewAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // HomeFragment will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void openBuyItemDialog(byte[] decodedString ,String craftName,String craftPrice,int availableQuantity){
        BuyItemDialog buyItemDialog=new BuyItemDialog();

        Bundle args = new Bundle();
        args.putByteArray("img", decodedString);
        args.putString("name", craftName);
        args.putString("price", craftPrice);
        args.putInt("availability", availableQuantity);
        args.putSerializable("dto",itemAtCurrentPosition);

        buyItemDialog.setArguments(args);
        buyItemDialog.show(((HomeActivity) mContext).getSupportFragmentManager(),"buy item dialog");
        notifyDataSetChanged();
    }

    public void openUpdateCraftDialog(){
        UpdateCraftDialog updateItemDialog=new UpdateCraftDialog();

        Bundle args = new Bundle();
        args.putSerializable("dto",itemAtCurrentPosition);

        updateItemDialog.setArguments(args);
        updateItemDialog.show(((HomeActivity) mContext).getSupportFragmentManager(),"update item dialog");

    }
}

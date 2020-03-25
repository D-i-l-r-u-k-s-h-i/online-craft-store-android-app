package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.CraftItem;
import apiit.lk.onlinecraftstore.DTOs.ReviewDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.RatingsReviewsApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CraftsPurchased_RecyclerViewAdapter extends RecyclerView.Adapter<CraftsPurchased_RecyclerViewAdapter.
        ViewHolder>{
    private List<CraftItem> mData;

    RatingsReviewsApis ratingsReviewsApis;

    private Context mContext;

    public CraftsPurchased_RecyclerViewAdapter(List<CraftItem> data, Context mContext){
        this.mData= data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.crafts_purchased_rv,parent,
                false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //this is where retrieving of information happens from a db and show them on the view
        viewHolder.name_tv.setText(mData.get(position).getCiName());

        //load the image using here.
        byte[] decodedString = Base64.decode(mData.get(position).getImgFile() , Base64.DEFAULT);
        Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

        viewHolder.craftImage.setImageBitmap(decodedByte);

        //api to get review
        viewHolder.send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String review=viewHolder.review_et.getText().toString();

                ReviewDTO reviewDTO=new ReviewDTO();
                reviewDTO.setReviewDescription(review);
                reviewDTO.setCraftId(mData.get(position).getCraftId());

                ratingsReviewsApis= ApiClient.getClient().create(RatingsReviewsApis.class);

                Map<String,String> headers=new HashMap<>();
                headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(mContext));
                headers.put("content-type", "application/json");

                Call<ResponseBody> call=ratingsReviewsApis.addReviewForCraft(headers,reviewDTO);

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

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView craftImage;
        TextView name_tv;
        ImageView send_button;
        EditText review_et;
//        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            craftImage=itemView.findViewById(R.id.craftImgIV);
            name_tv=itemView.findViewById(R.id.craftNameTV);
            send_button=itemView.findViewById(R.id.sendBtn);
            review_et=itemView.findViewById(R.id.reviewET);

//            parent_layout=itemView.findViewById(R.id.parentLayout);
        }
    }
}

package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.CraftCreator;
import apiit.lk.onlinecraftstore.DTOs.RatingsDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.RatingsReviewsApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CraftCreatorsPurchasedFrom_RecyclerViewAdapter extends RecyclerView.Adapter<CraftCreatorsPurchasedFrom_RecyclerViewAdapter.
        ViewHolder>{
    private List<CraftCreator> mData;

    RatingsReviewsApis ratingsReviewsApis;

    private Context mContext;

    public CraftCreatorsPurchasedFrom_RecyclerViewAdapter(List<CraftCreator> data, Context mContext){
        this.mData=data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CraftCreatorsPurchasedFrom_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.craft_creators_purchased_from_rv,parent,
                false);
        CraftCreatorsPurchasedFrom_RecyclerViewAdapter.ViewHolder viewHolder=new CraftCreatorsPurchasedFrom_RecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CraftCreatorsPurchasedFrom_RecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        //this is where retrieving of information happens from a db and show them on the view
        viewHolder.creatorName_tv.setText(mData.get(position).getCreatorName());
        viewHolder.creatorEmail_tv.setText(mData.get(position).getCreatorEmail());

        viewHolder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingsReviewsApis= ApiClient.getClient().create(RatingsReviewsApis.class);

                RatingsDTO ratingsDTO=new RatingsDTO();
                ratingsDTO.setRating(rating);
                ratingsDTO.setCreatorId(mData.get(position).getCreatorId());

                Map<String,String> headers=new HashMap<>();
                headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(mContext));
                headers.put("content-type", "application/json");

                Call<ResponseBody> call=ratingsReviewsApis.addRatingForCreator(headers,ratingsDTO);

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

        //load the image using here.
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void showToast(String message,Context context){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView creatorName_tv;
        TextView creatorEmail_tv;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            creatorName_tv=itemView.findViewById(R.id.creatorNameTV);
            creatorEmail_tv=itemView.findViewById(R.id.creatorrEmailTV);
            ratingBar=itemView.findViewById(R.id.ratingBar);

        }
    }
}

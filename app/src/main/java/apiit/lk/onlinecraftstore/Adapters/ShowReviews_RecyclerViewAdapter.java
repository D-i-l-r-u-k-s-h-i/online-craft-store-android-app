package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apiit.lk.onlinecraftstore.DTOs.ReviewDTO;
import apiit.lk.onlinecraftstore.R;

public class ShowReviews_RecyclerViewAdapter extends RecyclerView.Adapter<ShowReviews_RecyclerViewAdapter.
        ViewHolder>{
    private List<ReviewDTO> mData;

//    private ArrayList<Product> productArrayList;

    private Context mContext;

    public ShowReviews_RecyclerViewAdapter(List<ReviewDTO> reviewDTO, Context mContext){
        this.mContext = mContext;
        this.mData= reviewDTO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_rv,parent,
                false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        //this is where retrieving of information happens from a db and show them on the view
        viewHolder.craft_name_tv.setText(mData.get(position).getCraftItem().getCiName());
        viewHolder.review_description_tv.setText(mData.get(position).getReviewDescription()+" -"+mData.get(position).getUser().getUsername()+"-");
        viewHolder.timestamp_tv.setText(mData.get(position).getDate());

        byte[] decodedString = Base64.decode(mData.get(position).getCraftItem().getImgFile() , Base64.DEFAULT);
        Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);

        viewHolder.craftImage.setImageBitmap(decodedByte);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView craftImage;
        TextView craft_name_tv;
        TextView review_description_tv;
        TextView timestamp_tv;
//        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            craftImage=itemView.findViewById(R.id.c_imageIV);
            craft_name_tv=itemView.findViewById(R.id.craftNameReviewTV);
            review_description_tv=itemView.findViewById(R.id.reviewDescTV);
            timestamp_tv=itemView.findViewById(R.id.timeTV);

//            parent_layout=itemView.findViewById(R.id.parentLayout);
        }
    }
}

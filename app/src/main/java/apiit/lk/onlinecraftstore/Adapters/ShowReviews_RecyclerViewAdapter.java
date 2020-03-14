package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apiit.lk.onlinecraftstore.R;

public class ShowReviews_RecyclerViewAdapter extends RecyclerView.Adapter<ShowReviews_RecyclerViewAdapter.
        ViewHolder>{
    private ArrayList<String> craft_names;
    private ArrayList<String> review_descriptiolnss;
    private ArrayList<String> usernames;
    private ArrayList<String> images;

//    private ArrayList<Product> productArrayList;

    private Context mContext;

    public ShowReviews_RecyclerViewAdapter(/*ArrayList<String> usernames,*/ ArrayList<String> review_descriptiolnss,
                                            ArrayList<String> images,ArrayList<String> craft_names, Context mContext){
//        this.usernames = usernames;
        this.review_descriptiolnss = review_descriptiolnss;
        this.images = images;
        this.mContext = mContext;
        this.craft_names=craft_names;
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
        viewHolder.craft_name_tv.setText(craft_names.get(position));
        viewHolder.review_description_tv.setText(review_descriptiolnss.get(position));
//        viewHolder.timestamp_tv.setText(prices.get(position));
        //load the image using picaso here.
//        Picasso.get().load(images.get(position)).into(viewHolder.productImage);


    }

    @Override
    public int getItemCount() {
        return review_descriptiolnss.size();
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

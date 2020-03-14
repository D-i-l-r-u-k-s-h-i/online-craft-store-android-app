package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import apiit.lk.onlinecraftstore.R;

public class CraftsPurchased_RecyclerViewAdapter extends RecyclerView.Adapter<CraftsPurchased_RecyclerViewAdapter.
        ViewHolder>{
    private ArrayList<String> names;
    private ArrayList<String> images;

//    private ArrayList<Product> productArrayList;

    private Context mContext;

    public CraftsPurchased_RecyclerViewAdapter(ArrayList<String> names,ArrayList<String> images, Context mContext){
        this.names = names;
        this.images = images;
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
        viewHolder.name_tv.setText(names.get(position));

        //load the image using picaso here.
//        Picasso.get().load(images.get(position)).into(viewHolder.productImage);
        //on click of an item..pass the image url and product name to DetailedItemActivity
//        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(view.getContext(),DetailedItemActivity.class);
//                intent.putExtra("product_name",names.get(position));
//                intent.putExtra("product_image",images.get(position));
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return names.size();
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

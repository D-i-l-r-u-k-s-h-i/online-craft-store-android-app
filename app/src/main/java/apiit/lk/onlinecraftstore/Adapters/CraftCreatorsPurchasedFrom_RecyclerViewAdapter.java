package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import apiit.lk.onlinecraftstore.R;

public class CraftCreatorsPurchasedFrom_RecyclerViewAdapter extends RecyclerView.Adapter<CraftCreatorsPurchasedFrom_RecyclerViewAdapter.
        ViewHolder>{
    private ArrayList<String> names;
    private ArrayList<String> emails;

//    private ArrayList<Product> productArrayList;

    private Context mContext;

    public CraftCreatorsPurchasedFrom_RecyclerViewAdapter(ArrayList<String> names,ArrayList<String> emails, Context mContext){
        this.names = names;
        this.emails = emails;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CraftCreatorsPurchasedFrom_RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.crafts_purchased_rv,parent,
                false);
        CraftCreatorsPurchasedFrom_RecyclerViewAdapter.ViewHolder viewHolder=new CraftCreatorsPurchasedFrom_RecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CraftCreatorsPurchasedFrom_RecyclerViewAdapter.ViewHolder viewHolder, final int position) {
        //this is where retrieving of information happens from a db and show them on the view
        viewHolder.creatorName_tv.setText(names.get(position));
        viewHolder.creatorEmail_tv.setText(emails.get(position));

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


        TextView creatorName_tv;
        TextView creatorEmail_tv;


//        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            creatorName_tv=itemView.findViewById(R.id.creatorNameTV);
            creatorEmail_tv=itemView.findViewById(R.id.creatorrEmailTV);

//            parent_layout=itemView.findViewById(R.id.parentLayout);
        }
    }
}

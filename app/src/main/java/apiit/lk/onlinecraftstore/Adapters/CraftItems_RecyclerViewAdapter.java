package apiit.lk.onlinecraftstore.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import apiit.lk.onlinecraftstore.R;

public class CraftItems_RecyclerViewAdapter extends RecyclerView.Adapter<CraftItems_RecyclerViewAdapter.ViewHolder>{
    private String[] mImages;
    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public CraftItems_RecyclerViewAdapter(Context context, String[] data, String[] mImages) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mImages=mImages;
    }
    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.craft_items_rv, parent, false);
        return new ViewHolder(view);
    }
    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.craftName_tv.setText(mData[position]);
        holder.myImageView.setImageResource(mInflater.getContext()
                .getResources()
                .getIdentifier(mImages[position], "drawable",mInflater.getContext()
                        .getPackageName()));
    }

    @Override
    public int getItemCount() {
        return mData.length;
    } // total number of cells

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView craftName_tv;
        ImageView myImageView;
        TextView creator_tv;
        TextView shortDiscription_tv;
        TextView longDiscription_tv;
        TextView price_tv;
        TextView availability_tv;

        ViewHolder(View itemView) {
            super(itemView);
            craftName_tv = itemView.findViewById(R.id.craftNameTV);
            myImageView=itemView.findViewById(R.id.mImageIV);
            creator_tv = itemView.findViewById(R.id.creatorTV);
            shortDiscription_tv = itemView.findViewById(R.id.shortDescriptionTV);
            longDiscription_tv = itemView.findViewById(R.id.longDescriptionTV);
            price_tv = itemView.findViewById(R.id.priceTV);
            availability_tv = itemView.findViewById(R.id.availabilityTV);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenient method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // HomeFragment will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


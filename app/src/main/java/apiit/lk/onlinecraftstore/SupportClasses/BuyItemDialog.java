package apiit.lk.onlinecraftstore.SupportClasses;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import apiit.lk.onlinecraftstore.DTOs.ItemDTO;
import apiit.lk.onlinecraftstore.R;

public class BuyItemDialog extends AppCompatDialogFragment {
    private ImageView imageView;
    private TextView itemName;
    private TextView itemPrice;
    private TextView availability;
    private NumberPicker numberPicker;

    private BuyItemDialogListener buyItemDialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.layout_buy_item_dialog,null);

        imageView =view.findViewById(R.id.imageView3);
        itemName=view.findViewById(R.id.itemNameTV);
        itemPrice=view.findViewById(R.id.itemPriceTV);
        availability=view.findViewById(R.id.availableNoTV);
        numberPicker=view.findViewById(R.id.numberPicker2);

        Bundle mArgs = getArguments();
        String name = mArgs.getString("name");
        String price = mArgs.getString("price");
        int qty = mArgs.getInt("availability");
        byte[] decodedString=mArgs.getByteArray("img");
        ItemDTO dto= (ItemDTO) mArgs.getSerializable("dto");

        Bitmap decodedByte= BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        imageView.setImageBitmap(decodedByte);

        itemName.setText(name);
        itemPrice.setText(price);
        availability.setText("only "+qty+ " available");

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(qty);
        numberPicker.setValue(1);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });


        builder.setView(view)
                .setTitle("Purchase Item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyItemDialogListener.passData(numberPicker.getValue(),dto);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            buyItemDialogListener= (BuyItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement BuyItemListener");
        }
    }

    public interface BuyItemDialogListener{
        void passData(int quantity,ItemDTO itemAtCurrentPosition);
    }
}

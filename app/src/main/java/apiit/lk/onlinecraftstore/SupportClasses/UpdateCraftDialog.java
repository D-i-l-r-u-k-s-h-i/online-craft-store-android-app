package apiit.lk.onlinecraftstore.SupportClasses;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import java.io.File;
import apiit.lk.onlinecraftstore.DTOs.ItemDTO;
import apiit.lk.onlinecraftstore.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class UpdateCraftDialog extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener{

    private Spinner categories_spinner;
    private ImageView upload_iv;
    private CheckedTextView availability_ctv;
    private EditText craftName_et;
    private EditText price_et;
    private EditText quantity_et;
    private EditText shortDesc_et;
    private EditText longDesc_et;

    private RadioGroup radioGroup;

    private Button updateBtn;

    MultipartBody image;

    String selected_category;
    boolean availability;

    private UpdateItemDialogListener updateItemDialogListener;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.layout_edit_craft_dialog,null);

        categories_spinner=view.findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.categories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories_spinner.setAdapter(adapter);

        categories_spinner.setOnItemSelectedListener(this);

        craftName_et=view.findViewById(R.id.craftTitleET);
        price_et=view.findViewById(R.id.priceUpET);
        quantity_et=view.findViewById(R.id.qtyET);
        shortDesc_et=view.findViewById(R.id.shortDescET);
        longDesc_et=view.findViewById(R.id.longDescET);

        radioGroup=view.findViewById(R.id.typeRG);

        int selectedId=radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton=view.findViewById(selectedId);

        String type=selectedButton!=null ? selectedButton.getText().toString(): "Ready Made";

        updateBtn=view.findViewById(R.id.updateCraftBtn);


        Bundle mArgs = getArguments();
        ItemDTO dto= (ItemDTO) mArgs.getSerializable("dto");

        upload_iv=view.findViewById(R.id.uploadIV);

        upload_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,"select picture"),1);

            }
        });

        availability_ctv=view.findViewById(R.id.isAvailableCheckedTV_ADD);
        availability_ctv.setChecked(dto.getAvailabilityStatus());
        if(dto.getAvailabilityStatus()){
            availability_ctv.setCheckMarkDrawable(R.drawable.ic_check_box_24dp);
            availability_ctv.setChecked(true);
            availability=true;
        }
        else{
            availability_ctv.setCheckMarkDrawable(R.drawable.ic_check_box_outline_blank_24dp);
            availability_ctv.setChecked(false);
            availability=false;
        }

        availability_ctv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean value=availability_ctv.isChecked();
                if(value){
                    Log.d("check1", String.valueOf(availability_ctv.isChecked()));
                    availability_ctv.setCheckMarkDrawable(R.drawable.ic_check_box_outline_blank_24dp);
                    availability_ctv.setChecked(false);
                    //not available
                }
                else{
                    Log.d("check2", String.valueOf(availability_ctv.isChecked()));
                    availability_ctv.setCheckMarkDrawable(R.drawable.ic_check_box_24dp);
                    availability_ctv.setChecked(true);
                    //available
                }
            }
        });

        craftName_et.setText(dto.getCiName());
        price_et.setText(dto.getCiPrice().toString());
        quantity_et.setText(dto.getItemQuantity().toString());
        shortDesc_et.setText(dto.getShortDescription());
        longDesc_et.setText(dto.getLongDescription());

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDTO updatedDto=new ItemDTO();
                updatedDto.setCiName(craftName_et.getText().toString().trim());
                updatedDto.setCiPrice(Double.valueOf(price_et.getText().toString()));
                updatedDto.setCategory(selected_category!=null?selected_category:"Other");
                updatedDto.setCraftId(dto.getCraftId());
                updatedDto.setItemQuantity(Integer.valueOf(quantity_et.getText().toString()));
                updatedDto.setLongDescription(longDesc_et.getText().toString().trim());
                updatedDto.setShortDescription(shortDesc_et.getText().toString().trim());
                updatedDto.setType(type);
//        updatedDto.setImgFile();
                updatedDto.setAvailabilityStatus(availability);

                updateItemDialogListener.passData(updatedDto,image);

                dismiss();
            }
        });


        builder.setView(view)
                .setTitle("Update Item")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                        getActivity(),
                        PERMISSIONS_STORAGE,
                        REQUEST_EXTERNAL_STORAGE
                );
            }
            Uri imageUri=data.getData();

            String result;
            Cursor cursor = getActivity().getContentResolver().query(imageUri, null, null, null, null);
            if (cursor == null) { // Source is Dropbox or other similar local file path
                result = imageUri.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            File file = new File(result);
            RequestBody requestFile =
                    RequestBody.create(file,MediaType.parse("multipart/form-data"));

// MultipartBody.Part is used to send also the actual file name
            image = new MultipartBody.Builder().addFormDataPart("imgFile", file.getName(), requestFile).build();

            Log.d("imageBytes", String.valueOf(image));

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_category=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            updateItemDialogListener= (UpdateItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement UpdateItemListener");
        }
    }

    public interface UpdateItemDialogListener{
        void passData(ItemDTO updatedDto,MultipartBody imgFile);
    }
}

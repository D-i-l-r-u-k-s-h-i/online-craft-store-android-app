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

public class AddCraftDialog  extends AppCompatDialogFragment implements AdapterView.OnItemSelectedListener{

    private Spinner categories_spinner;
    private ImageView upload_iv;
    private EditText craftName_et;
    private EditText price_et;
    private EditText quantity_et;
    private EditText shortDesc_et;
    private EditText longDesc_et;

    private RadioGroup radioGroup;

    private Button addBtn;

    MultipartBody image;

    String selected_category;
    boolean availability;

    private AddCraftDialog.AddItemDialogListener addItemDialogListener;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();

        View view=inflater.inflate(R.layout.layout_add_craft_dialog,null);

        categories_spinner=view.findViewById(R.id.categorySpinner_ADD);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.categories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categories_spinner.setAdapter(adapter);

        categories_spinner.setOnItemSelectedListener(this);

        craftName_et=view.findViewById(R.id.craftTitleET_ADD);
        price_et=view.findViewById(R.id.priceUpET_ADD);
        quantity_et=view.findViewById(R.id.qtyET_ADD);
        shortDesc_et=view.findViewById(R.id.shortDescET_ADD);
        longDesc_et=view.findViewById(R.id.longDescET_ADD);

        radioGroup=view.findViewById(R.id.typeRG_ADD);

        int selectedId=radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton=view.findViewById(selectedId);

        String type=selectedButton!=null ? selectedButton.getText().toString(): "Ready Made";

        addBtn=view.findViewById(R.id.addCraftBtn);

//        Bundle mArgs = getArguments();
//        ItemDTO dto= (ItemDTO) mArgs.getSerializable("dto");

        upload_iv=view.findViewById(R.id.uploadIV_ADD);

        upload_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent,"select picture"),1);

            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemDTO itemDto=new ItemDTO();
                itemDto.setCiName(craftName_et.getText().toString().trim());
                itemDto.setCiPrice(Double.valueOf(price_et.getText().toString()));
                itemDto.setCategory(selected_category!=null?selected_category:"Other");
                itemDto.setItemQuantity(Integer.valueOf(quantity_et.getText().toString()));
                itemDto.setLongDescription(longDesc_et.getText().toString().trim());
                itemDto.setShortDescription(shortDesc_et.getText().toString().trim());
                itemDto.setType(type);
                itemDto.setAvailabilityStatus(availability);

                addItemDialogListener.passNewData(itemDto,image);

                dismiss();
            }
        });


        builder.setView(view)
                .setTitle("Add New Craft")
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
                    RequestBody.create(file, MediaType.parse("multipart/form-data"));

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
            addItemDialogListener= (AddCraftDialog.AddItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+" must implement AddItemListener");
        }
    }

    public interface AddItemDialogListener{
        void passNewData(ItemDTO itemDto,MultipartBody imgFile);
    }
}

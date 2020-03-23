package apiit.lk.onlinecraftstore.ActivitiesAndFragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apiit.lk.onlinecraftstore.DTOs.CraftItem;
import apiit.lk.onlinecraftstore.DTOs.ItemDTO;
import apiit.lk.onlinecraftstore.DTOs.OrderDTO;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.CraftItemApis;
import apiit.lk.onlinecraftstore.JsonPlaceholderAPIs.OrderApis;
import apiit.lk.onlinecraftstore.R;
import apiit.lk.onlinecraftstore.SupportClasses.AddCraftDialog;
import apiit.lk.onlinecraftstore.SupportClasses.ApiClient;
import apiit.lk.onlinecraftstore.SupportClasses.BuyItemDialog;
import apiit.lk.onlinecraftstore.SupportClasses.SaveSharedPreferenceInstance;
import apiit.lk.onlinecraftstore.SupportClasses.UpdateCraftDialog;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,BuyItemDialog.BuyItemDialogListener,UpdateCraftDialog.UpdateItemDialogListener,AddCraftDialog.AddItemDialogListener{

    private DrawerLayout drawer;
    private OrderApis orderApis;
    private CraftItemApis craftItemApis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        String userRole=SaveSharedPreferenceInstance.getRole(this);

        if(!userRole.equals("ROLE_CRAFT_CREATOR")){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_dashboard).setVisible(false);
        }

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    //to prevent leaving to the next activity before closing the navigation drawer
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_cart:
                FragmentTransaction ft2=clearBackStack();
                ft2.replace(R.id.fragment_container,new CartFragment(),"cart").addToBackStack(null).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(),"home")
                        .addToBackStack(null).commit();
                break;
            case R.id.nav_past_orders:
                FragmentTransaction ft3=clearBackStack();
                ft3.replace(R.id.fragment_container,new PastOrdersFragment(),"pastorders").addToBackStack(null).commit();
                break;
            case R.id.nav_dashboard:
                String userRole=SaveSharedPreferenceInstance.getRole(this);

                if (userRole.equals("ROLE_CRAFT_CREATOR")) {

                    FragmentTransaction ft4=clearBackStack();
                    //admin dashboard only for now, later check the role in shared preference and direct respectively
                    ft4.replace(R.id.fragment_container,new CreatorDashboardFragment(),"dashboard").addToBackStack(null).commit();

                }
                break;

            case R.id.nav_logout:
                //clear shared preference
                SaveSharedPreferenceInstance.clearUser(this);
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                Toast.makeText(this,"Signed out.",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment())
                        .addToBackStack(null).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public FragmentTransaction clearBackStack(){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();

        int backstackEntry=getSupportFragmentManager().getBackStackEntryCount();
        List<Fragment> fragmentList=getSupportFragmentManager().getFragments();
        //get the fragment's tag and clear all the other fragments in the stack upto 'home'
        if(backstackEntry>0){
            for(int i=0;i<backstackEntry;i++){
                getSupportFragmentManager().popBackStackImmediate();
                if(fragmentList.size()<i){
                    Fragment fragment=fragmentList.get(0);
                    ft.remove(fragment);

                    if(fragment.getTag()!=null && !(fragment.getTag().toString()).equals("home"))
                        break;
                }
                fragmentList=getSupportFragmentManager().getFragments();
            }
        }
        return ft;
    }

    @Override
    public void passData(int quantity, ItemDTO itemAtCurrentPosition) {

        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setCraftId(itemAtCurrentPosition.getCraftId());
        orderDTO.setQuantity(quantity);

        orderApis= ApiClient.getClient().create(OrderApis.class);

        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(this));
        headers.put("content-type", "application/json");

        Call<ResponseBody> call=orderApis.buyItem(headers,orderDTO);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                try {
                    showToast(""+response.body().string()+" of "+itemAtCurrentPosition.getCiName()+"-"+ itemAtCurrentPosition.getCiPrice()*quantity,getApplicationContext());
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

    public void showToast(String message,Context context){
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void passData(ItemDTO updatedDto, MultipartBody imgF) {
        craftItemApis=ApiClient.getClient().create(CraftItemApis.class);

        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(this));
        headers.put("content-type", "multipart/form-data; boundary="+imgF.boundary());

        Call<ResponseBody> call=craftItemApis.updateItem(headers,imgF,updatedDto.getCraftId(),
                updatedDto.getCiName(),updatedDto.getCiPrice(),updatedDto.getItemQuantity(),updatedDto.getShortDescription(),
                updatedDto.getLongDescription(),updatedDto.getType(),updatedDto.getCategory(),updatedDto.getAvailabilityStatus());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                try {
                    showToast(response.body().string()+" "+updatedDto.getCiName(),getApplicationContext());

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CreatorDashboardFragment())
                            .addToBackStack(null).commit();

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

    @Override
    public void passNewData(ItemDTO itemDto, MultipartBody imgFile) {
        craftItemApis=ApiClient.getClient().create(CraftItemApis.class);

        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer "+ SaveSharedPreferenceInstance.getAuthToken(this));
        headers.put("content-type", "multipart/form-data; boundary="+imgFile.boundary());

        Call<ResponseBody> call=craftItemApis.addItem(headers,imgFile,
                itemDto.getCiName(),itemDto.getCiPrice(),itemDto.getItemQuantity(),itemDto.getShortDescription(),
                itemDto.getLongDescription(),itemDto.getType(),itemDto.getCategory(),itemDto.getAvailabilityStatus());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Log.d("responseCode", String.valueOf(response.code()));
                    return;
                }

                try {
                    showToast(itemDto.getCiName()+" "+response.body().string(),getApplicationContext());

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CreatorDashboardFragment())
                            .addToBackStack(null).commit();

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
}

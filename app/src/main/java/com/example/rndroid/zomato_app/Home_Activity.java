package com.example.rndroid.zomato_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Home_Activity extends AppCompatActivity {

    FragmentManager manager;
    Restaurant_ResyclerView_FragmentOne resyclerView_fragmentOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
//        resyclerView_fragmentOne = new Restaurant_ResyclerView_FragmentOne();
        Fragment_RecyclerView_Restaurant_With_Database with_database = new Fragment_RecyclerView_Restaurant_With_Database();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container1, with_database);
        transaction.commit();
    }

    public void onOverFlowMenuSelected(String name){
        Webview_FragmentTwo webview_fragmentTwo = new Webview_FragmentTwo();
        manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        webview_fragmentTwo.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container1, webview_fragmentTwo);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public boolean checkInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null){
            return true;
        }
        return false;
    }
}

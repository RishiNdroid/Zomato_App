package com.example.rndroid.zomato_app;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_RecyclerView_Restaurant_With_Database extends Fragment {


    public Fragment_RecyclerView_Restaurant_With_Database() {
        // Required empty public constructor
    }
    EditText editText;
    Button button;
    Cursor cursor;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    MyTask mytask;
    LinearLayoutManager linearLayoutManager;
    MyDatabase myDatabase;
// 14. a Asynctask <String,Void,String>, prepare life cycle methods

    public class MyTask extends AsyncTask<String, Void, String> {

        StringBuilder stringBuilder;
        @Override
        protected String doInBackground(String... strings) {
            try {
// 20. go to AsyncTask inner class implement doInBackground()
//                7 steps process to connect zomato server and get restaurant details.
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
// below three lines saying that we need JSON Data and for security purpose we will provide them our API Key
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("user-key", "8d8f5a54bd4a584013dd5c813fcf4fe0");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                stringBuilder = new StringBuilder("");
                String line = null;
                line = bufferedReader.readLine();
                while (line != null){
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
/*21. go to AsyncTask inner class implement onPostExecute(),  write json reverseparsing logic,
      from for loop - fill restaurant details to arraylist, notify recyclerview adapter.* */
            super.onPostExecute(s);
            try {
                JSONObject rootJsonObject = new JSONObject(s);
                JSONArray nearby_restaurantsJsonArray = rootJsonObject.getJSONArray("nearby_restaurants");
                for (int i = 0; i < nearby_restaurantsJsonArray.length(); i++){
                    JSONObject Root_Child_jsonObject = nearby_restaurantsJsonArray.getJSONObject(i);
                    JSONObject restaurant_JsonObject = Root_Child_jsonObject.getJSONObject("restaurant");
                    String name = restaurant_JsonObject.getString("name");
                    String thub = restaurant_JsonObject.getString("thumb");
                    JSONObject locationJsonObject = restaurant_JsonObject.getJSONObject("location");
                    String locality = locationJsonObject.getString("locality");
                    String address = locationJsonObject.getString("address");
                    String latitude = locationJsonObject.getString("latitude");
                    String longitude = locationJsonObject.getString("longitude");
                    myDatabase.insertContact(name,locality,address,thub,latitude,longitude);
                    cursor.requery();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    // 14.    b. RecyclerViewAdapter, with ViewHolder inner class, and life cycle methods
    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.my_row, parent, false);
            MyRecyclerViewAdapter.MyViewHolder myViewHolder = new MyRecyclerViewAdapter.MyViewHolder(view);
            return myViewHolder;
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final String getName, getLocality, getAddress, getLatitude, getLongitude, getImageThumb;

                cursor.moveToPosition(position);
                getName = cursor.getString(cursor.getColumnIndex("name"));
                getLocality = cursor.getString(cursor.getColumnIndex("locality"));
                getAddress = cursor.getString(cursor.getColumnIndex("address"));
                getImageThumb = cursor.getString(cursor.getColumnIndex("thumb"));
                getLatitude = cursor.getString(cursor.getColumnIndex("latitude"));
                getLongitude = cursor.getString(cursor.getColumnIndex("longitude"));

            holder.textViewName.setText(getName);
            holder.textViewLocality.setText(getLocality);
            holder.textViewAddress.setText(getAddress);
            holder.imageOverFlow.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(getActivity(), view);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.map_menu:
                                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                                    intent.putExtra("lat", getLatitude);
                                    intent.putExtra("long", getLongitude);
                                    intent.putExtra("name", getName);
                                    startActivity(intent);
                                    return true;
                                case R.id.search_google_menu:
                                    Home_Activity home_activity = (Home_Activity) getActivity();
                                    home_activity.onOverFlowMenuSelected(getName);
                                    return true;
                            }
                            return false;
                        }
                    });
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.overflow_menu, popup.getMenu());
                    popup.show();
                }
            });
            Glide.with(getActivity()).load(getImageThumb).placeholder(R.mipmap.ic_launcher).crossFade().into(holder.imageThumb);
        }

        @Override
        public int getItemCount() {
            return cursor.getCount();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageThumb, imageOverFlow;
            TextView textViewName, textViewLocality, textViewAddress;
            public MyViewHolder(View itemView) {
                super(itemView);
                imageThumb = (ImageView) itemView.findViewById(R.id.imageview_photo_row);
                imageOverFlow = (ImageView) itemView.findViewById(R.id.image_over_flow);
                textViewAddress = (TextView) itemView.findViewById(R.id.textview_address_row);
                textViewLocality = (TextView) itemView.findViewById(R.id.textview_locality_row);
                textViewName = (TextView) itemView.findViewById(R.id.textViewName_row);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment__recycler_view__restaurant__with__database, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_database);
        myRecyclerViewAdapter = new MyRecyclerViewAdapter();
        mytask = new MyTask();
        cursor = myDatabase.getRestaurants();
        recyclerView.setAdapter(myRecyclerViewAdapter);

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Home_Activity home_activity = (Home_Activity) getActivity();
        if (home_activity.checkInternet()){
            mytask.execute("https://developers.zomato.com/api/v2.1/geocode?lat=12.66&lon=77.98");
        }else {
            Toast.makeText(getActivity(), "No Active Internet..!", Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDatabase = new MyDatabase(getActivity());
        myDatabase.openDB();
    }
}

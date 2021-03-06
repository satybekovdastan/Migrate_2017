package com.migrate.admin.pagination.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.migrate.admin.pagination.Adapters.RVProhibitionAdapter;
import com.migrate.admin.pagination.Helpers.DataHelper;
import com.migrate.admin.pagination.Helpers.DateDateDB;
import com.migrate.admin.pagination.R;
import com.migrate.admin.pagination.Serializables.Istories;
import com.migrate.admin.pagination.Serializables.RulesOfIncoming;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
//date 6
public class HumanTraffickingActivity extends AppCompatActivity {
    String date,dateDB;
    Istories istories;
    int limit=15;
    DataHelper dataHelper;
    int lang;
    URL urlM;
    String TAG="TAG";

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVProhibitionAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<RulesOfIncoming> studentList;

    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_of_incoming_ht);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_ht);

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);
        Cursor cursor=dataHelper.getDataLanguage();
        if (cursor.getCount()>0)
        {

            cursor.moveToFirst();
            lang=cursor.getInt(cursor.getColumnIndex(DataHelper.LANGUAGE_COLUMN));

        }
        else lang=0;
        if (lang==0) {
            try {
                urlM=new URL("http://176.126.167.249/api/v1/human_traffic/?format=json&limit=0");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                urlM=new URL("http://176.126.167.249/api/v1/human_traffic_kg/?format=json&limit=0");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_rules_of_incoming);
        studentList = new ArrayList<RulesOfIncoming>();
        handler = new Handler();
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);

        ifConnect();





    }

    public void db(){
        Cursor cursor = dataHelper.getDataHT();
        Log.e("TAG_NEWS",cursor.getCount()+" kol");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                RulesOfIncoming istories=new RulesOfIncoming();
                istories.setTitle(cursor.getString(cursor.getColumnIndex(DataHelper.HT_ZAGOLOVOK_COLUMN)));
                istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.HT_TEXT_COLUMN)));
                istories.setImage(cursor.getString(cursor.getColumnIndex(DataHelper.HT_IMAGE_COLUMN)));

                studentList.add(istories);
            }
            mAdapter=new RVProhibitionAdapter(studentList,mRecyclerView,this,1,getString(R.string.ac_prohib));
            mRecyclerView.setAdapter(mAdapter);


        }
    }

    public void ifConnect(){
        Calendar calendar=Calendar.getInstance();

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH);
        int year=calendar.get(Calendar.YEAR);
        date=day+"."+month+"."+year;
        Cursor cursor=dataHelper.getDataDate("6");
        if (cursor.getCount()==0){
            ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                new ParseTask().execute();
                progressBar.setVisibility(View.VISIBLE);
            }
            else {
                Toast.makeText(this,R.string.toast_no_internet,Toast.LENGTH_SHORT).show();

            }

        }
        else {
            cursor.moveToFirst();

            dateDB=cursor.getString(cursor.getColumnIndex(DataHelper.DATE_LAST_DATE_COLUMN));
            DateDateDB dateDateDB=new DateDateDB();
            if (dateDateDB.calendar1(dateDB)){
                ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                    new ParseTask().execute();
                    progressBar.setVisibility(View.VISIBLE);

                }
                else {
                    Toast.makeText(this,R.string.toast_no_internet,Toast.LENGTH_SHORT).show();

                }
            }else db();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();

                return true;
            case R.id.action_ubdate:
                dataHelper.updateDate("ss","6");
                ifConnect();

                return true;

            default:  return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);



        return true;
    }

    public void onClick(View view) {
        startActivity(new Intent(HumanTraffickingActivity.this,NKOActivity.class));
    }


    public class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonResult = "";


        @Override
        protected String doInBackground(Void... params) {

            try {

                URL url = urlM;

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                jsonResult = builder.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return jsonResult;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            studentList.clear();

            JSONObject dataJsonObject;

            try {
                dataJsonObject = new JSONObject(json);
                JSONArray menus = dataJsonObject.getJSONArray("objects");
                JSONObject meta=dataJsonObject.getJSONObject("meta");
                total_count=meta.getInt("total_count");


                for (int i = 0; i < menus.length(); i++) {
                    JSONObject menu = menus.getJSONObject(i);
                    RulesOfIncoming student = new RulesOfIncoming();

                    student.setImage("http://176.126.167.249/"+menu.getString("image"));
                    student.setText(menu.getString("text_ru"));
                    student.setTitle(menu.getString("title_ru"));
                    if (i==0){dataHelper.deleteHT();
                    }
                    dataHelper.insertHT(student);
                    studentList.add(student);





                }



            } catch (JSONException e) {
                e.printStackTrace();
                Log.d(TAG, "JSON_PIZDEC");
            }
            progressBar.setVisibility(View.GONE);


            mAdapter=new RVProhibitionAdapter(studentList,mRecyclerView,HumanTraffickingActivity.this,1,getString(R.string.ac_prohib));
            mRecyclerView.setAdapter(mAdapter);
            dataHelper.updateDate(date,"6");

        }
    }

}
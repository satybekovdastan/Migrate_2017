package com.migrate.admin.pagination.Activities.SecondActivities;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.migrate.admin.pagination.Adapters.RVHHHHAdapter;
import com.migrate.admin.pagination.Helpers.DataHelper;
import com.migrate.admin.pagination.R;
import com.migrate.admin.pagination.Serializables.Diaspora;

import java.util.ArrayList;
import java.util.List;
//datahelper.getDataDate=0;
public class DiasporySecondActivity extends AppCompatActivity {
    DataHelper dataHelper;
    String date,dateDB;
    String TAG="TAG";
    private Toolbar toolbar;
    Button button;
    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVHHHHAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Diaspora> studentList;
    String category="";
    ProgressBar progressBar;
    protected Handler handler;
    String position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyfrom_life_second);
        position=getIntent().getStringExtra("id");
        Log.e("TAGGGG___ID",position);
        category=getIntent().getStringExtra("text");
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(category+"+");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);

        button=(Button) findViewById(R.id.new_story_button);
        tvEmptyView = (TextView) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_forum);
        studentList = new ArrayList<Diaspora>();
        handler = new Handler();
        progressBar=(ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        if (toolbar != null) {


        }

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);





           Cursor cursor = dataHelper.getDataDiaspora(position);
            studentList.clear();
            Diaspora istories;


            Log.e("TAG_FORUM",cursor.getCount()+" kol");
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    istories=new Diaspora();
                    istories.setEmail(cursor.getString(cursor.getColumnIndex(DataHelper.DIASPORA_EMAIL_COLUMN)));
                    istories.setPlace(cursor.getString(cursor.getColumnIndex(DataHelper.DIASPORA_PLACE_COLUMN)));
                    istories.setManager(cursor.getString(cursor.getColumnIndex(DataHelper.DIASPORA_MANAGER_COLUMN)));
                    istories.setCity(cursor.getString(cursor.getColumnIndex(DataHelper.DIASPORA_city_COLUMN)));
                    istories.setNumber(cursor.getString(cursor.getColumnIndex(DataHelper.DIASPORA_NUMBER_COLUMN)));
                    istories.setAddress(cursor.getString(cursor.getColumnIndex(DataHelper.DIASPORA_ADDRESS_COLUMN)));
                    istories.setKarta(cursor.getString(cursor.getColumnIndex(DataHelper.DIASPORA_KARTA_COLUMN)));

                    studentList.add(istories);
                }



            }
            mAdapter=new RVHHHHAdapter(studentList,this);
            mRecyclerView.setAdapter(mAdapter);

        }








    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}

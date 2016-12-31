



package com.migrate.admin.pagination.Activities;

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
import android.widget.ProgressBar;
import android.widget.TextView;


import com.migrate.admin.pagination.Adapters.RVRulesOfIncomingAdapter;
import com.migrate.admin.pagination.Helpers.DataHelper;
import com.migrate.admin.pagination.R;
import com.migrate.admin.pagination.Serializables.Istories;
import com.migrate.admin.pagination.Serializables.RulesOfIncoming;


import java.util.ArrayList;
import java.util.List;

public class RulesOfIncomingActivity extends AppCompatActivity {

    Istories istories;
    int limit=15;
    int offset=0;
    DataHelper dataHelper;
    ArrayList<Istories> listNews;

    RecyclerView recyclerView;
    String TAG="TAG";
    private Toolbar toolbar;

    private TextView tvEmptyView;
    private RecyclerView mRecyclerView;
    private RVRulesOfIncomingAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int total_count=100000;
    private List<RulesOfIncoming> studentList;
    String position;
    ProgressBar progressBar;
    protected Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_of_incoming);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_eaeu);
        position=getIntent().getStringExtra("id");

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        dataHelper=new DataHelper(this);

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

            Cursor cursor = dataHelper.getDataROI(position);
            Log.e("TAG_NEWS",cursor.getCount()+" kol");
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    RulesOfIncoming istories=new RulesOfIncoming();
                    istories.setTitle(cursor.getString(cursor.getColumnIndex(DataHelper.RULES_OF_INCOMING_ZAGOLOVOK_COLUMN)));
                    istories.setText(cursor.getString(cursor.getColumnIndex(DataHelper.RULES_OF_INCOMING_TEXT_COLUMN)));
                    istories.setImage(cursor.getString(cursor.getColumnIndex(DataHelper.RULES_OF_INCOMING_IMAGE_COLUMN)));

                    studentList.add(istories);
                }

                mAdapter=new RVRulesOfIncomingAdapter(studentList,mRecyclerView,this,getString(R.string.ac_eaeu));
                mRecyclerView.setAdapter(mAdapter);


            }




    }


    // load initial data

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }



}
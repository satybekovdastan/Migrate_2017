package com.migrate.admin.pagination.Activities.SecondActivities;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.migrate.admin.pagination.Activities.EAEUActivity;
import com.migrate.admin.pagination.Activities.EmployementActivity;
import com.migrate.admin.pagination.Activities.HumanTraffickingActivity;
import com.migrate.admin.pagination.Activities.ProhibitionRFActivity;
import com.migrate.admin.pagination.Helpers.DataHelper;
import com.migrate.admin.pagination.R;

import java.net.MalformedURLException;
import java.net.URL;

public class FAQSecondActivity extends AppCompatActivity {
    int position;
    DataHelper dataHelper;
    int lang;
    ImageView answer, queston;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqsecond);
        TextView tvNews=(TextView) findViewById(R.id.tv_news_second);
        TextView tvAnswer=(TextView) findViewById(R.id.tv_answer);
        Button button=(Button) findViewById(R.id.bt_faq_second);
        answer=(ImageView) findViewById(R.id.answer);
        queston=(ImageView) findViewById(R.id.queston);

        String text=getIntent().getStringExtra("text");


        String title=getIntent().getStringExtra("title");
         position=getIntent().getIntExtra("position",-1);
        if (position==0||position==1||position==2||position==3){
            button.setVisibility(View.VISIBLE);
        }
        tvAnswer.setText(title);
        tvNews.setText(text);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.ac_faq);
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

                answer.setImageResource(R.drawable.title_quastion);
                queston.setImageResource(R.drawable.title_answer);


        }else {
            answer.setImageResource(R.drawable.title_quastion_kg);
            queston.setImageResource(R.drawable.title_answer_kg);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

    public void onclick(View view) {
     if (position==0){
         startActivity(new Intent(FAQSecondActivity.this,HumanTraffickingActivity.class));
     }
        else if (position==1){
         startActivity(new Intent(FAQSecondActivity.this,EAEUActivity.class));
     }
        else if (position==2){
         startActivity(new Intent(FAQSecondActivity.this,ProhibitionRFActivity.class));

     }
        else if (position==3){
         startActivity(new Intent(FAQSecondActivity.this,EmployementActivity.class));

     }
    }
}

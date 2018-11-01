package com.drying.calendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.drying.calendar.view.CheckCalendarView;

/**
 * Author: drying
 * E-mail: drying@erongdu.com
 * Date: 2018/9/11 16:10
 * <p/>
 * Description:自定义日历
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckCalendarView aa = findViewById(R.id.aa);
        aa.setOnCheckClickListener(new CheckCalendarView.OnCheckClickListener() {
            @Override
            public void onCheckClickListeners(View v, String year, String month, String day) {
                Toast.makeText(MainActivity.this, year + "年" + month + "月" + day + "日", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

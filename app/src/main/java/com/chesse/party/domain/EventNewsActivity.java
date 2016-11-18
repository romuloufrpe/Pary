package com.chesse.party.domain;


import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chesse.party.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class EventNewsActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DialogInterface.OnCancelListener{

    private TextView tvDate;
    private Button mbtnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_news);

        tvDate = (TextView)findViewById(R.id.input_date_time);
        mbtnDate = (Button)findViewById(R.id.btn_event);

        mbtnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTime(view);
            }
        });
    }


    private int year, mouth, day, hour, minuto;

    public void dateTime(View view){
        initDateTimeData();
        Calendar cDefault = Calendar.getInstance();
        cDefault.set(year, mouth, day);

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                cDefault.get(Calendar.DAY_OF_MONTH),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.YEAR)
        );

        Calendar cMin = Calendar.getInstance();
        Calendar cMax = Calendar.getInstance();

        cMax.set(cMax.get(Calendar.YEAR), 11, 31);
        datePickerDialog.setMinDate(cMin);
        datePickerDialog.setMaxDate(cMax);

        List<Calendar> daysList = new LinkedList<>();
        Calendar[] daysArray;
        Calendar cAux = Calendar.getInstance();

        while (cAux.getTimeInMillis() <= cMax.getTimeInMillis()){

                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(cAux.getTimeInMillis());

                daysList.add(c);

            cAux.setTimeInMillis( cAux.getTimeInMillis() + (24 * 60 * 60 * 1000) );
        }
        daysArray = new Calendar[daysList.size()];
        for (int i = 0; i < daysArray.length; i++){
            daysArray[i] = daysList.get(i);
        }

        datePickerDialog.setSelectableDays(daysArray);
        datePickerDialog.setOnCancelListener(this);
        datePickerDialog.show(getFragmentManager(), "datePickerDialog");
        datePickerDialog.setThemeDark(true);

    }

    private void initDateTimeData(){
        if (year == 0){
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            mouth = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minuto = c.get(Calendar.MINUTE);
        }
    }

    @Override
    public void onCancel(DialogInterface dialogInterface) {
        year = mouth = day = hour = minuto = 0;
        tvDate.setText("");

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar tDefault = Calendar.getInstance();
        tDefault.set(year, mouth, day, hour, minuto);
        year = year;
        mouth = monthOfYear;
        dayOfMonth = dayOfMonth;

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                tDefault.get(Calendar.HOUR_OF_DAY),
                tDefault.get(Calendar.MINUTE),
                true

        );
        timePickerDialog.setThemeDark(true);
        timePickerDialog.setOnCancelListener(this);
        timePickerDialog.show(getFragmentManager(), "timePickerDialog");

    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        hour = hourOfDay;
        minute = minute;

        tvDate.setText((day < 10 ? "0"+day : day)+"/"+
                (mouth+1 < 10 ? "0"+(mouth+1) : mouth+1)+"/"+
                year+" às "+
                (hour < 10 ? "0"+hour : hour)+":"+
                (minuto < 10 ? "0"+minuto : minuto));

    }
}

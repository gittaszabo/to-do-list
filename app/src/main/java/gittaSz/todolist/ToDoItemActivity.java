package gittaSz.todolist;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import gittaSz.todolist.model.ToDoItem;

public class ToDoItemActivity extends AppCompatActivity {

    private Intent intent;
    private ToDoItem ti;
    private EditText etTask;
    private TextView tvCal;
    private RadioGroup rgPriority;
    private RadioGroup rgStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_item);

        intent = getIntent();
        ti = (ToDoItem) intent.getSerializableExtra("ti");

        etTask = findViewById(R.id.etTask);
        tvCal = findViewById(R.id.tvCal);


        if (ti != null) {
            etTask.setText(ti.getTask());
            tvCal.setText(ti.getDate());
            rgPriority = findViewById(R.id.rgPriority);
            String priority = ti.getPriority();
            switch (priority) {
                case "high":
                    rgPriority.check(R.id.rbHigh);
                    break;
                case "medium":
                    rgPriority.check(R.id.rbMedium);
                    break;
                case "low":
                    rgPriority.check(R.id.rbLow);
                    break;
            }
            rgStatus = findViewById(R.id.rgStatus);
            String status = ti.getStatus();
            switch (status) {
                case "active":
                    rgStatus.check(R.id.rbActive);
                    break;
                case "completed":
                    rgStatus.check(R.id.rbCompleted);
                    break;
            }
        }
    }

    public void cancel(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void save(View view) {
        if (ti == null) {
            ti = new ToDoItem();
        }

        ti.setTask(etTask.getText().toString());
        ti.setDate(tvCal.getText().toString());

        rgPriority = findViewById(R.id.rgPriority);
        int selectedPrior = rgPriority.getCheckedRadioButtonId();
        switch (selectedPrior) {
            case R.id.rbHigh:
                ti.setPriority("high");
                break;
            case R.id.rbMedium:
                ti.setPriority("medium");
                break;
            case R.id.rbLow:
                ti.setPriority("low");
                break;

        }

        rgStatus = findViewById(R.id.rgStatus);
        int selectedStatus = rgStatus.getCheckedRadioButtonId();
        switch (selectedStatus) {
            case R.id.rbCompleted:
                ti.setStatus("completed");
                break;
            case R.id.rbActive:
                ti.setStatus("active");
                break;
        }

        intent.putExtra("ti", ti);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void dateTime(View view) {
        final View dialogView = View.inflate(this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = dialogView.findViewById(R.id.date_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth());

                long time = calendar.getTimeInMillis();
                Date date = new Date(time);
                String myDate = new SimpleDateFormat("dd-MMM-yyyy").format(date);

                tvCal.setText(myDate);
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(dialogView);
        alertDialog.show();
    }
}

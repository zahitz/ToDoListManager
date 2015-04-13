package huji.ac.il.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;


/**
 * Created by Zahi on 09/04/2015.
 */
public class AddNewTodoItemActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void addTask(View v){
        EditText title = (EditText) findViewById(R.id.edtNewItem);
        DatePicker dueDate = (DatePicker) findViewById(R.id.datePicker);
        Intent ret = new Intent();
        ret.putExtra("title", title.getText().toString());
        ret.putExtra("dueDate", dueDate.getCalendarView().getDate());
        setResult(RESULT_OK, ret);
        finish();
    }

    public void cancel(View v){
        Intent ret = new Intent();
        setResult(RESULT_CANCELED, ret);
        finish();
    }
}

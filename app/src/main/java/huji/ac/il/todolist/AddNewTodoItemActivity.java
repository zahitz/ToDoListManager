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

/**
 * Activity for adding a new task item to the todo list.
 */
public class AddNewTodoItemActivity extends Activity {
    public final static String EXTRA_TITLE = "title";
    public final static String EXTRA_DUE_DATE = "dueDate";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void addTask(View v){
        EditText title = (EditText) findViewById(R.id.edtNewItem);
        DatePicker dueDate = (DatePicker) findViewById(R.id.datePicker);
        Intent ret = new Intent();
        ret.putExtra(EXTRA_TITLE, title.getText().toString());
        ret.putExtra(EXTRA_DUE_DATE, dueDate.getCalendarView().getDate());
        setResult(RESULT_OK, ret);
        finish();
    }

    public void cancel(View v){
        Intent ret = new Intent();
        setResult(RESULT_CANCELED, ret);
        finish();
    }
}

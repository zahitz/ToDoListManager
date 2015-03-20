package huji.ac.il.todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class TodoListManagerActivity extends ActionBarActivity {
    ArrayList<String> tasks;
    EditText edtItemView;
    ListView todoList;
    ArrayAdapter<String> tasksAdapter;
    AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        tasks = new ArrayList<String>();
        edtItemView = (EditText) findViewById(R.id.edtNewItem);
        todoList = (ListView)findViewById(R.id.lstTodoItems);
        dialogBuilder = new AlertDialog.Builder(this);
        tasksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasks) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(position % 2 == 0 ? Color.RED : Color.BLUE);
                return view;
            }
        };
        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    dialogBuilder.setTitle(tasks.get(position)).setNegativeButton(R.string.del_string, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tasks.remove(position);
                            todoList.setAdapter(tasksAdapter);
                           dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                    return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.add:
                addTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addTask(){
        String task = edtItemView.getText().toString();
        tasks.add(task);
        todoList.setAdapter(tasksAdapter);
    }
}


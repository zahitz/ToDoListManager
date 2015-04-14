package huji.ac.il.todolist;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {
    private static final int ADD_TASK = 0;
    private static final String CALL_TASK_IDENTIFIER = "Call ";


    ArrayList<task> tasks;
    ListView todoList;
    ArrayAdapter<task> tasksAdapter;
    Date currDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        tasks = new ArrayList<task>();
        todoList = (ListView) findViewById(R.id.lstTodoItems);
        registerForContextMenu(todoList);
        currDate = Calendar.getInstance().getTime();

        tasksAdapter = new ArrayAdapter<task>(this, R.layout.todo_list_item, tasks) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                if (convertView == null) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.todo_list_item, null);
                } else {
                    view = convertView;
                }
                TextView title = (TextView) view.findViewById(R.id.txtTodoTitle);
                TextView dueDate = (TextView) view.findViewById(R.id.txtTodoDueDate);
                title.setText(tasks.get(position).getTitle());
                Date taskDueDate = tasks.get(position).getDueDate();
                //Creating the dueDate string in the format
                String strDate = null;
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                if (taskDueDate != null) {
                    strDate = sdf.format(taskDueDate.getTime());
                    if (currDate.after(taskDueDate)) {
                        title.setTextColor(Color.RED);
                        dueDate.setTextColor(Color.RED);
                    }
                } else {
                    strDate = getResources().getText(R.string.noDueString).toString();
                }
                dueDate.setText(strDate);
                return view;
            }
        };
        todoList.setAdapter(tasksAdapter);
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
                Intent addIntent = new Intent(this, AddNewTodoItemActivity.class);
                startActivityForResult(addIntent, ADD_TASK);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK) {
            if (resultCode == RESULT_OK) {
                String title = data.getStringExtra(AddNewTodoItemActivity.EXTRA_TITLE);
                long dueDate = data.getLongExtra(AddNewTodoItemActivity.EXTRA_DUE_DATE, -1);
                tasks.add(new task(title, dueDate));
                tasksAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = info.position;
        String taskTitle = tasks.get(position).getTitle();
        getMenuInflater().inflate(R.menu.menu_list_item_context_view, menu.setHeaderTitle(taskTitle));
        if (taskTitle.startsWith(CALL_TASK_IDENTIFIER)) {
            menu.findItem(R.id.menuItemCall).setVisible(true).setTitle(taskTitle);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menuItemDelete:
                tasks.remove(info.position);
                tasksAdapter.notifyDataSetChanged();
                return true;
            case R.id.menuItemCall:
                String taskTitle = tasks.get(info.position).getTitle();
                Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+taskTitle.replaceFirst(CALL_TASK_IDENTIFIER, "")));
                startActivity(dial);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
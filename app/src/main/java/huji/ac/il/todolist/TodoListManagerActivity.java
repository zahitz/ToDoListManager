package huji.ac.il.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {
    private static final int ADD_TASK = 0;
    private static final String CALL_TASK_IDENTIFIER = "Call ";

    ListView todoList;
    DBHelper helper;
    ToDoListAdapter tasksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        todoList = (ListView) findViewById(R.id.lstTodoItems);
        registerForContextMenu(todoList);
        helper = new DBHelper(this);
        Cursor cursor = helper.getData();
        tasksAdapter = new ToDoListAdapter(this, cursor);
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
                helper.insertTask(title, dueDate);
                tasksAdapter.changeCursor(helper.getData());
                tasksAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Cursor cursor = (Cursor) tasksAdapter.getItem(info.position);
        if (cursor == null) {
            return;
        }
        String taskTitle = cursor.getString(cursor.getColumnIndex(DBHelper.COL_TITLE));
        menu.setHeaderTitle(taskTitle);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_list_item_context_view, menu);

        // Open dialer in case of a "Call" task.
        if (taskTitle.startsWith(CALL_TASK_IDENTIFIER)) {
            menu.findItem(R.id.menuItemCall).setVisible(true).setTitle(taskTitle);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long id = tasksAdapter.getItemId(info.position);

        switch (item.getItemId()) {
            case R.id.menuItemDelete:
                helper.deleteTask(id);
                tasksAdapter.changeCursor(helper.getData());
                tasksAdapter.notifyDataSetChanged();
                return true;

            case R.id.menuItemCall:
                Cursor cursor = (Cursor) tasksAdapter.getItem(info.position);
                Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+cursor.getString(
                        cursor.getColumnIndex(DBHelper.COL_TITLE)).replaceFirst(CALL_TASK_IDENTIFIER, "")));
                startActivity(dial);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}
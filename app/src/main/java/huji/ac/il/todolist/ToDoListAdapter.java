package huji.ac.il.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import org.joda.time.DateTimeComparator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* Created by Zahi on 24/04/2015.
*/
public class ToDoListAdapter extends CursorAdapter {
    private LayoutInflater mInflater;

    public ToDoListAdapter(Context context, Cursor c) {
        super(context, c);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.todo_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String taskTitle = cursor.getString(cursor.getColumnIndex(DBHelper.COL_TITLE));
        Date taskDueDate = new Date(cursor.getLong(cursor.getColumnIndex(DBHelper.COL_DUE_DATE)));
        //Creating the dueDate string in the format
        String strDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        TextView title = (TextView) view.findViewById(R.id.txtTodoTitle);
        TextView dueDate = (TextView) view.findViewById(R.id.txtTodoDueDate);
        title.setText(taskTitle);
        if (taskDueDate != null) {
            strDate = sdf.format(taskDueDate.getTime());
            Date currDate = Calendar.getInstance().getTime();
            if (DateTimeComparator.getDateOnlyInstance().compare(taskDueDate, currDate) < 0) {
                title.setTextColor(Color.RED);
                dueDate.setTextColor(Color.RED);
            }
        } else {
            strDate = context.getResources().getText(R.string.noDueString).toString();
        }
        dueDate.setText(strDate);
    }
}

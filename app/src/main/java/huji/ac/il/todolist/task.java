package huji.ac.il.todolist;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Zahi on 08/04/2015.
 */
public class task {
    private String title;
    private Date dueDate;

    public task(String title, Date dueDate){
        this.title = title;
        this.dueDate = dueDate;
    }

    public task(String title, long dueDate){
        this.title = title;
        this.dueDate = dueDate >= 0 ? new Date(dueDate) : null;
    }

    public String getTitle(){
        return title;
    }

    public Date getDueDate(){
        return dueDate;
    }
}

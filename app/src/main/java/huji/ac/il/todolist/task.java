package huji.ac.il.todolist;

import java.util.Date;

/**
 * A single task in the ToDo list.
 * Has the task title, and it's due date.
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

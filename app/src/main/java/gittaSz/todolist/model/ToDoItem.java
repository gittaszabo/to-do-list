package gittaSz.todolist.model;

import java.io.Serializable;


public class ToDoItem implements Serializable {

    private int id;
    private String task;
    private String date;
    private String priority;
    private String status;

    public ToDoItem(int id, String task, String date, String priority, String status) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.priority = priority;
        this.status = status;
    }

    public ToDoItem() {
        id = -1;
    }

    public ToDoItem(String task, String date, String priority, String status) {
        this.task = task;
        this.date = date;
        this.priority = priority;
        this.status = status;
        id = -1;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;


    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    //get
    public LiveData<List<Task>> getTask(){return this.taskDao.getTasks();}

    //create
    public void createTask(Task task){taskDao.insertTask(task);
    }

    //delette
    public void deletteTask(long id){taskDao.deleteTask(id);}
}

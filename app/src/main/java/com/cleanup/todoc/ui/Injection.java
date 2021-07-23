package com.cleanup.todoc.ui;

import android.content.Context;

import com.cleanup.todoc.database.TodocMasterDatabase;
import com.cleanup.todoc.repositories.ProjetDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static TaskDataRepository provideTaskDataSource(Context context){
        TodocMasterDatabase database = TodocMasterDatabase.getInstance(context);
        return new TaskDataRepository(database.taskDao());
    }

    public static ProjetDataRepository provideProjectDataRepository(Context context){
        TodocMasterDatabase database = TodocMasterDatabase.getInstance(context);
        return new ProjetDataRepository(database.projectDao());
    }

    public static Executor provideExecutor(){return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjetDataRepository dataSourceProject = provideProjectDataRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}

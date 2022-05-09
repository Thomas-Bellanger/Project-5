package com.cleanup.todoc.ui;

import android.content.Context;

import com.cleanup.todoc.database.TodocMasterDatabase;
import com.cleanup.todoc.repositories.ProjetDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;
import com.cleanup.todoc.ui.viewModel.ViewModelFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {
private static TaskDataRepository mTaskDataRepository=null;
private static ProjetDataRepository sProjetDataRepository=null;

    public static TaskDataRepository provideTaskDataSource(Context context){
        if (mTaskDataRepository==null) {
            TodocMasterDatabase database = TodocMasterDatabase.getInstance(context);
            mTaskDataRepository= new TaskDataRepository(database.taskDao());
        }
        return mTaskDataRepository;
    }

    public static ProjetDataRepository provideProjectDataRepository(Context context){
        if (sProjetDataRepository==null) {
            TodocMasterDatabase database = TodocMasterDatabase.getInstance(context);
            sProjetDataRepository = new ProjetDataRepository(database.projectDao());
        }
        return sProjetDataRepository;
    }

    public static Executor provideExecutor(){return Executors.newSingleThreadExecutor();}

    public static ViewModelFactory provideViewModelFactory(Context context){
        TaskDataRepository dataSourceTask = provideTaskDataSource(context);
        ProjetDataRepository dataSourceProject = provideProjectDataRepository(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceTask, dataSourceProject, executor);
    }
}

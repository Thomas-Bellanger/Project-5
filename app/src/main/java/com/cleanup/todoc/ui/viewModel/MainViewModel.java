package com.cleanup.todoc.ui.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjetDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;



public class MainViewModel extends ViewModel {
    private final TaskDataRepository mTaskDataRepository;
    private final ProjetDataRepository projetDataRepository;
    private final Executor executor;

    @Nullable
    private LiveData<Project> currentProject;

    public MainViewModel(TaskDataRepository taskDataRepository, ProjetDataRepository projetDataRepository, Executor executor) {
        this.mTaskDataRepository = taskDataRepository;
        this.projetDataRepository = projetDataRepository;
        this.executor = executor;
    }

    public void init(long projectId){
        if (this.currentProject !=null){
            return;
        }
        currentProject = projetDataRepository.getProject(projectId);
    }

    public LiveData<Project> getProject(long projectId){return  this.currentProject;}

    public LiveData<List<Task>> getTasks(){return mTaskDataRepository.getTask();}

    public void createTask(Task task){
        executor.execute(()-> {
            mTaskDataRepository.createTask(task);
        });
    }

    public void deleteTask(long taskId){
        executor.execute(()->{
            mTaskDataRepository.deletteTask(taskId);});
    }

}

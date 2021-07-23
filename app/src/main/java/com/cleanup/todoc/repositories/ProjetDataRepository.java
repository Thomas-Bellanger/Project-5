package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

public class ProjetDataRepository {

    private final ProjectDao projectDao;


    public ProjetDataRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public LiveData<Project> getProject(long id){return this.projectDao.getProject(id);}
}

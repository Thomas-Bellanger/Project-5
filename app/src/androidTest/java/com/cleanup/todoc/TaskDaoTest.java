package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.TodocMasterDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private TodocMasterDatabase mTodocMasterDatabase;

    private static long PROJECT_ID=1L;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "projet test", 0xFFA3CED2);
    private static Task TASK_TEST_1 = new Task(1L, "tache 1", 11);
    private static Task TASK_TEST_2 = new Task(1L, "tache 2", 12);
    private static Task TASK_TEST_3 = new Task(1L, "tache 3", 13);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception{
        this.mTodocMasterDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), TodocMasterDatabase.class)
                                    .allowMainThreadQueries()
                                    .build();
        mTodocMasterDatabase.projectDao().insertProject(PROJECT_DEMO);
    }

    @After
    public void closeDb() throws Exception{
        mTodocMasterDatabase.close();
    }

    @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException{
        List<Task> tasks = LiveDataTestUtil.getValue(this.mTodocMasterDatabase.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }
    @Test
    public void insertAndGetTasks() throws InterruptedException{
        this.mTodocMasterDatabase.taskDao().insertTask(TASK_TEST_1);
        this.mTodocMasterDatabase.taskDao().insertTask(TASK_TEST_2);
        this.mTodocMasterDatabase.taskDao().insertTask(TASK_TEST_3);
        List<Task> tasks = LiveDataTestUtil.getValue(this.mTodocMasterDatabase.taskDao().getTasks());
        assertTrue(tasks.size() ==3);
    }

    @Test
    public void insertAndDeletteTasks() throws InterruptedException{
        this.mTodocMasterDatabase.taskDao().insertTask(TASK_TEST_1);
        Task taskAdded = LiveDataTestUtil.getValue(this.mTodocMasterDatabase.taskDao().getTasks()).get(0);
        this.mTodocMasterDatabase.taskDao().deleteTask(taskAdded.getId());

        List<Task> tasks = LiveDataTestUtil.getValue(this.mTodocMasterDatabase.taskDao().getTasks());
        assertTrue(tasks.isEmpty());
    }

}

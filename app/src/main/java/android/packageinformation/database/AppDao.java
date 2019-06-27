package android.packageinformation.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AppDao {

    @Query("SELECT * FROM apps")
    LiveData<List<AppEntry>> loadAllTasks();

    @Insert
    void insertApp(AppEntry appEntry);

}

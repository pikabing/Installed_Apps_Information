package android.packageinformation.Home;

import android.app.Application;
import android.packageinformation.database.AppDatabase;
import android.packageinformation.database.AppEntry;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<AppEntry>> apps;

    public MainViewModel(Application application) {
        super(application);
    }

    public LiveData<List<AppEntry>> getTasks() {
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        apps = database.taskDao().loadAllTasks();
        return apps;
    }


}

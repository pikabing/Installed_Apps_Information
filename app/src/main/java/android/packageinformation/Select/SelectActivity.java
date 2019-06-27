package android.packageinformation.Select;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.packageinformation.AppExecutors;
import android.packageinformation.Home.MainViewModel;
import android.packageinformation.Home.SelectedAppsAdapter;
import android.packageinformation.R;
import android.packageinformation.database.AppDatabase;
import android.packageinformation.database.AppEntry;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity implements InstalledAppsAdapter.OnCardClickListener {

    RecyclerView recyclerView;
    InstalledAppsAdapter installedAppsAdapter;
    List<InstalledApps> installedApps = new ArrayList<>();
    private AppDatabase mDb;
    int add = 1;
    MainViewModel mainViewModel;
    List<AppEntry> mAppEntries;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        mDb = AppDatabase.getInstance(getApplicationContext());

        recyclerView= findViewById(R.id.installed_apps_list);

        mainViewModel = new MainViewModel(getApplication());
        mainViewModel.getTasks().observe(this, new Observer<List<AppEntry>>() {
            @Override
            public void onChanged(List<AppEntry> appEntries) {
                mAppEntries = appEntries;
                getInstalledApps();
            }
        });

    }


    public void getInstalledApps() {

        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);
        for(int i = 0; i < packageInfos.size(); i++) {

            InstalledApps app = new InstalledApps();

            PackageInfo packageInfo = packageInfos.get(i);

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = null;
                String packageName = packageInfo.packageName;
                try {
                    icon = getApplicationContext().getPackageManager().getApplicationIcon(packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                for (int j = 0; j < mAppEntries.size(); j++) {

                    if (mAppEntries.get(j).getPackageName().equals(packageName)){
                        add = 0;
                        break;
                    }

                }

                app.setIcon(icon);
                app.setName(appName);
                app.setPackageName(packageName);

                if (add == 1){
                    installedApps.add(app);
                }

                add = 1;
            }
        }

        installedAppsAdapter = new InstalledAppsAdapter(this, installedApps, this);
        installedAppsAdapter.setData(installedApps);
        installedAppsAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(installedAppsAdapter);
    }

    @Override
    public void onCardClick(int position) {

        final AppEntry appEntry = new AppEntry(installedApps.get(position).getName(), installedApps.get(position).getPackageName());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.taskDao().insertApp(appEntry);
                finish();
            }
        });

    }
}

package android.packageinformation.Home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.packageinformation.Information.InformationActivity;
import android.packageinformation.R;
import android.packageinformation.Select.InstalledApps;
import android.packageinformation.Select.SelectActivity;
import android.packageinformation.database.AppDatabase;
import android.packageinformation.database.AppEntry;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectedAppsAdapter.OnCardClickListener {

    List<InstalledApps> installedApps = new ArrayList<>();
    SelectedAppsAdapter selectedAppsAdapter;
    RecyclerView selectedApps;
    private AppDatabase mDb;
    LinearLayout emptyView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyView = findViewById(R.id.emptyView);
        floatingActionButton = findViewById(R.id.select_app_activity);

        selectedApps = findViewById(R.id.selected_apps_list);
        selectedAppsAdapter = new SelectedAppsAdapter(this, this);
        selectedApps.setLayoutManager(new LinearLayoutManager(this));
        selectedApps.setAdapter(selectedAppsAdapter);

        mDb = AppDatabase.getInstance(getApplicationContext());
        setupViewModel();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<AppEntry>>() {
            @Override
            public void onChanged(@Nullable List<AppEntry> appEntries) {
                selectedAppsAdapter.setData(appEntries);
                if (selectedAppsAdapter.getItemCount() == 0){
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onCardClick(int position) {

        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
        AppEntry appEntry = selectedAppsAdapter.appEntries.get(position);
        intent.putExtra("name",appEntry.getName());
        intent.putExtra("package",appEntry.getPackageName());
        startActivity(intent);

    }
}


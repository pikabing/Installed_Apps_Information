package android.packageinformation.Information;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.packageinformation.R;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InformationActivity extends AppCompatActivity {

    ImageView icon;
    TextView name, packageName, activityName;
    RecyclerView permissions, services, receivers;
    List<String> permissionsList = new ArrayList<>();
    List<String> receiverList = new ArrayList<>();
    List<String> servicesList = new ArrayList<>();
    InformationCommonAdapter permAdapter, receiverAdapter, servicesAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        icon = findViewById(R.id.app_icon);
        name = findViewById(R.id.app_name);
        packageName = findViewById(R.id.app_package);
        permissions = findViewById(R.id.permissions);
        services = findViewById(R.id.services);
        receivers = findViewById(R.id.receivers);
        activityName = findViewById(R.id.app_launch_activity);

        String app_name = getIntent().getStringExtra("name");
        String package_name = getIntent().getStringExtra("package");

        name.setText(app_name);
        packageName.setText(package_name);

        Drawable app_icon = null;
        try {
            app_icon = getPackageManager().getApplicationIcon(package_name);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        icon.setImageDrawable(app_icon);

        String launchActivityName = "";
        Intent intent = getPackageManager().getLaunchIntentForPackage(package_name);
        if (intent != null) {
            launchActivityName = intent.getComponent().getClassName();
            int index = launchActivityName.lastIndexOf(".");
            launchActivityName = launchActivityName.substring(index + 1);
        }
        activityName.setText("Launch Activity: " + launchActivityName);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(package_name, PackageManager.GET_PERMISSIONS|
                                                                                          PackageManager.GET_SERVICES|
                                                                                          PackageManager.GET_RECEIVERS);

            if (packageInfo.requestedPermissions != null) {


                for (int j = 0; j < packageInfo.requestedPermissions.length; j++) {
                    if ((packageInfo.requestedPermissionsFlags[j] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                        String permission = packageInfo.requestedPermissions[j];
                        int index = permission.lastIndexOf(".");
                        permissionsList.add(permission.substring(index + 1));
                    }
                }
            } else {
                permissionsList.add("No Permissions Granted.");
            }

            permAdapter = new InformationCommonAdapter(this);
            permAdapter.setData(permissionsList);
            permissions.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            permissions.setAdapter(permAdapter);

            ActivityInfo[] activityInfo = packageInfo.receivers;
            if (activityInfo!= null){

                for (int j = 0; j < activityInfo.length; j++) {

                    String receiver = activityInfo[j].name;
                    int index = receiver.lastIndexOf(".");
                    receiverList.add(receiver.substring(index + 1));
                }

            } else {
                receiverList.add("No Receivers Found.");
            }

            receiverAdapter = new InformationCommonAdapter(this);
            receiverAdapter.setData(receiverList);
            receivers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            receivers.setAdapter(receiverAdapter);

            ServiceInfo[] serviceInfos = packageInfo.services;
            if (serviceInfos!= null){

                for (int j = 0; j < serviceInfos.length; j++) {

                    String service = serviceInfos[j].name;
                    int index = service.lastIndexOf(".");
                    servicesList.add(service.substring(index + 1));

                }

            } else {
                servicesList.add("No Services Found.");
            }

            servicesAdapter = new InformationCommonAdapter(this);
            servicesAdapter.setData(servicesList);
            services.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            services.setAdapter(servicesAdapter);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}

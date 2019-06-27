package android.packageinformation.Home;

import android.graphics.drawable.Drawable;

public class SelectedApps {

    public SelectedApps() {

    }

    private String pacakage_name, launch_activity, recievers, services;
    private Drawable icon;

    public String getPacakage_name() {
        return pacakage_name;
    }

    public void setPacakage_name(String pacakage_name) {
        this.pacakage_name = pacakage_name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getLaunch_activity() {
        return launch_activity;
    }

    public void setLaunch_activity(String launch_activity) {
        this.launch_activity = launch_activity;
    }

    public String getRecievers() {
        return recievers;
    }

    public void setRecievers(String recievers) {
        this.recievers = recievers;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}

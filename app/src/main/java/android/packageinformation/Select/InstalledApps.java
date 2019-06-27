package android.packageinformation.Select;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class InstalledApps implements Parcelable {

    public InstalledApps() {

    }

    private String name;
    private String packageName;
    private Drawable icon;


    protected InstalledApps(Parcel in) {
        name = in.readString();
        packageName = in.readString();
    }

    public static final Creator<InstalledApps> CREATOR = new Creator<InstalledApps>() {
        @Override
        public InstalledApps createFromParcel(Parcel in) {
            return new InstalledApps(in);
        }

        @Override
        public InstalledApps[] newArray(int size) {
            return new InstalledApps[size];
        }
    };

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(packageName);
    }
}

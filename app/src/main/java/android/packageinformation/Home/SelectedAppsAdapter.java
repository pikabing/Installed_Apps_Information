package android.packageinformation.Home;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.packageinformation.R;
import android.packageinformation.Select.InstalledApps;
import android.packageinformation.Select.InstalledAppsAdapter;
import android.packageinformation.database.AppEntry;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectedAppsAdapter extends RecyclerView.Adapter<SelectedAppsAdapter.MyViewHolder> {

    List<AppEntry> appEntries = new ArrayList<>();
    Context context;
    private OnCardClickListener monCardClickListener;

    public SelectedAppsAdapter(Context context, OnCardClickListener onCardClickListener) {
        this.context = context;
        this.monCardClickListener = onCardClickListener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.selected_apps_list, parent, false);
        return new SelectedAppsAdapter.MyViewHolder(view, monCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AppEntry appEntry = appEntries.get(position);
        String packageName = appEntry.getPackageName();
        String name = appEntry.getName();
        Drawable icon = null;
        try {
            icon = context.getPackageManager().getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        holder.appIcon.setImageDrawable(icon);
        holder.appName.setText(name);
        holder.packageName.setText(packageName);

    }

    @Override
    public int getItemCount() {
        return appEntries.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView appName, packageName;
        ImageView appIcon;
        OnCardClickListener onCardClickListener;

        public MyViewHolder(@NonNull View itemView, OnCardClickListener onCardClickListener) {
            super(itemView);
            this.onCardClickListener = onCardClickListener;

            appName = itemView.findViewById(R.id.app_name);
            packageName = itemView.findViewById(R.id.app_package);
            appIcon = itemView.findViewById(R.id.app_icon);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            onCardClickListener.onCardClick(getAdapterPosition());

        }
    }

    public void setData(List<AppEntry> appEntries) {
        this.appEntries = appEntries;
        notifyDataSetChanged();
    }

    public interface OnCardClickListener {
        void onCardClick(int position);
    }
}

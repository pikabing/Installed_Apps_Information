package android.packageinformation.Select;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.packageinformation.AppExecutors;
import android.packageinformation.Home.SelectedAppsAdapter;
import android.packageinformation.R;
import android.packageinformation.database.AppDatabase;
import android.packageinformation.database.AppEntry;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstalledAppsAdapter extends RecyclerView.Adapter<InstalledAppsAdapter.MyViewHolder>  {

    List<InstalledApps> installedApps = new ArrayList<>();
    Context context;
    private OnCardClickListener monCardClickListener;

    public InstalledAppsAdapter(Context context, List<InstalledApps> installedApps, OnCardClickListener onCardClickListener) {
        this.installedApps = installedApps;
        this.context = context;
        this.monCardClickListener = onCardClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.installed_apps_list, parent, false);
        return new InstalledAppsAdapter.MyViewHolder(view, monCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final InstalledApps app = installedApps.get(position);

        holder.appIcon.setImageDrawable(app.getIcon());
        holder.appName.setText(app.getName());

    }

    @Override
    public int getItemCount() {
        return installedApps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView appName;
        ImageView appIcon;
        MaterialCardView appCard;
        OnCardClickListener onCardClickListener;

        public MyViewHolder(@NonNull View itemView, OnCardClickListener onCardClickListener) {
            super(itemView);
            appName = itemView.findViewById(R.id.app_name);
            appIcon = itemView.findViewById(R.id.app_logo);
            appCard = itemView.findViewById(R.id.installed_app_card);
            this.onCardClickListener = onCardClickListener;

            appCard.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            onCardClickListener.onCardClick(getAdapterPosition());

        }
    }

    public void setData(List<InstalledApps> installedApps) {
        this.installedApps = installedApps;
    }

    public interface OnCardClickListener {
        void onCardClick(int position);
    }


}

package android.packageinformation.Information;

import android.content.Context;
import android.packageinformation.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InformationCommonAdapter extends RecyclerView.Adapter<InformationCommonAdapter.MyViewHolder> {

    private List<String> items = new ArrayList<>();
    private Context context;

    public InformationCommonAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_rv_layout, parent, false);
        return new InformationCommonAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         String string = items.get(position);
         holder.item.setText(string);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
        }
    }

    public void setData(List<String> strings) {
        this.items = strings;
        notifyDataSetChanged();
    }
}

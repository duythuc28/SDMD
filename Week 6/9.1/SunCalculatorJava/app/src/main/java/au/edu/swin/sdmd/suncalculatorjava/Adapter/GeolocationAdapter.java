package au.edu.swin.sdmd.suncalculatorjava.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import au.edu.swin.sdmd.suncalculatorjava.R;
import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;

import static android.app.Activity.RESULT_OK;

public class GeolocationAdapter  extends RecyclerView.Adapter<GeolocationAdapter.GeoLocationViewHolder>{
    private List<GeoLocation> locations;
    private Context mContext;

    public GeolocationAdapter(List<GeoLocation> locations, Context mContext) {
        this.locations = locations;
        this.mContext = mContext;
    }

    public class GeoLocationViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout linearLayout;
        public GeoLocationViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.geoTitle);
            linearLayout = view.findViewById(R.id.geo_row);
        }
    }

    public GeoLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.geolocation_layout, parent, false);

        return new GeoLocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GeoLocationViewHolder holder, int position) {
        final GeoLocation location = locations.get(position);
        holder.title.setText(location.getLocationName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("geoLocation", location);
                ((Activity)mContext).setResult(RESULT_OK, intent);
                ((Activity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}

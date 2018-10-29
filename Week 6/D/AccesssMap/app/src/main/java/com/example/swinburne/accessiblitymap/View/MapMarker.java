package com.example.swinburne.accessiblitymap.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.swinburne.accessiblitymap.Model.Building;
import com.example.swinburne.accessiblitymap.Model.BuildingCluster;
import com.example.swinburne.accessiblitymap.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class MapMarker extends DefaultClusterRenderer<BuildingCluster> {

    private Context mContext;

    public MapMarker(Context context, GoogleMap map, ClusterManager<BuildingCluster> clusterManager) {
        super(context, map, clusterManager);
        this.mContext = context;
    }

    @Override
    protected void onBeforeClusterItemRendered(BuildingCluster item, MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.icon(item.getMarkerLogo());
    }


}

package com.example.swinburne.accessiblitymap.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.swinburne.accessiblitymap.R;

public class MapLegend extends LinearLayout {
    public MapLegend(Context context) {
        super(context);
        inflate(context, R.layout.map_legend, this);
    }

    public MapLegend(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.map_legend, this);
//        initView(context);
    }

    public MapLegend(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.map_legend, this);
//        initView(context);
    }
}

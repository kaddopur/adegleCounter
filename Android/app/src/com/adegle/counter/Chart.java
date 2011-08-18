package com.adegle.counter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Chart extends Activity {
	
	private TextView title;
	private LinearLayout container;
	private ChartView chart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chart);
        bindViews();
        
        initViews();
	}

	private void initViews() {
		title.setText(Global.styleValue + ", " + 
				      Global.stageValue + ", " +
				      Global.nameValue);
	}

	private void bindViews() {
		title = (TextView)findViewById(R.id.title);
		container = (LinearLayout)findViewById(R.id.container);
		chart = new ChartView(this);
		container.addView(chart);
	}
	

}

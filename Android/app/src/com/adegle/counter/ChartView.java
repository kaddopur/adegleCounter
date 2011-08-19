package com.adegle.counter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.View;
import android.util.Log;

public class ChartView extends View {
	public ChartView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		
		int chartWidth = getWidth() - 100;
		int chartHeight = getHeight() - 50;
		long startTime = Global.historyTable.get(0)[0];
		long endTime = Global.historyTable.get(Global.historyTable.size()-1)[0];
		long maxPoint = Global.historyTable.get(Global.historyTable.size()-1)[1];
		long minPoint = 0;
		
		for(long[] history: Global.historyTable){
			if(history[3] < minPoint){
				minPoint = history[3];
			}
		}
		Log.v("123", ""+minPoint);
		
		
		if (maxPoint < Global.historyTable.get(Global.historyTable.size()-1)[2]){
			maxPoint = Global.historyTable.get(Global.historyTable.size()-1)[2];
		}
		
		float timeRatio = 1.0f * chartWidth / (endTime - startTime + 1);
		float pointRatio = 1.0f * chartHeight / (maxPoint - minPoint + 1); 
		
		
		
		// coord
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
		float xBase = 50;
		float xEnd = getWidth()-50;
		float yBase = chartHeight + minPoint * pointRatio;
		
		canvas.drawLine(xBase,     0,   xBase, chartHeight, paint);
		canvas.drawLine(xBase,     0, xBase-5,          10, paint);
		canvas.drawLine(xBase,     0, xBase+5,          10, paint);
		canvas.drawLine(xBase, yBase,    xEnd,       yBase, paint);
		canvas.drawLine(xEnd , yBase, xEnd-10,     yBase+5, paint);
		canvas.drawLine(xEnd , yBase, xEnd-10,     yBase-5, paint);
		canvas.drawText("points", xBase+10,      10, paint);
		canvas.drawText("time"  ,  xEnd-25, yBase-8, paint);
		
		// legend
		canvas.drawText("plus", xBase+10, 40, paint);
		canvas.drawText("minus", xBase+10, 55, paint);
		canvas.drawText("sum", xBase+10, 70, paint);
		
		paint.setColor(Color.BLUE);
		canvas.drawLine(100, 35, 125, 35, paint);
		paint.setColor(Color.RED);
		canvas.drawLine(100, 50, 125, 50, paint);
		paint.setARGB(255, 255, 144, 0);
		canvas.drawLine(100, 65, 125, 65, paint);
		
		// grid
		int curPoint = 0;
		int pointStep = 5;
		while ((maxPoint - minPoint) / pointStep > 5){
			pointStep *= 2;
		}
		while(curPoint + pointStep <= maxPoint) {
			curPoint += pointStep;
			paint.setARGB(10, 0, 0, 0);
			canvas.drawLine(50, chartHeight-(curPoint-minPoint)*pointRatio, 
					        getWidth()-50, chartHeight-(curPoint-minPoint)*pointRatio, paint);
			paint.setARGB(100, 0, 0, 0);
			canvas.drawText(""+curPoint, 20, chartHeight-(curPoint-minPoint)*pointRatio+5, paint);
		}
		curPoint = 0;
		while(curPoint - pointStep >= minPoint) {
			curPoint -= pointStep;
			paint.setARGB(10, 0, 0, 0);
			canvas.drawLine(50, chartHeight-(curPoint-minPoint)*pointRatio, 
					        getWidth()-50, chartHeight-(curPoint-minPoint)*pointRatio, paint);
			paint.setARGB(100, 0, 0, 0);
			canvas.drawText(""+curPoint, 20, chartHeight-(curPoint-minPoint)*pointRatio+5, paint);
		}
		
		int curTime = 0;
		int timeStep = 7500;
		while ((endTime - startTime) / timeStep > 5){
			timeStep *= 2;
		}
		while(curTime + startTime + timeStep < endTime) {
			curTime += timeStep;
			paint.setARGB(20, 0, 0, 0);
			canvas.drawLine(50 + curTime * timeRatio, 0, 
					        50 + curTime * timeRatio, chartHeight, paint);
			paint.setARGB(100, 0, 0, 0);
			canvas.drawText(""+curTime/1000, 42 + curTime * timeRatio, yBase+20, paint);
		}
		
		
		// plus
		float x1=0.0f, y1=0.0f;
		float x2=0.0f, y2=0.0f;
		float plusY, minusY, sumY;
		int i;
		paint.setColor(Color.BLUE);
		paint.setStyle(Style.STROKE);
		for(i=0; i<Global.historyTable.size()-1; i++){
			x1 = 50 + (Global.historyTable.get(i)[0] - startTime) * timeRatio;
			//y1 = chartHeight - Global.historyTable.get(i)[1] * pointRatio;
			y1 = chartHeight - (Global.historyTable.get(i)[1] - minPoint) * pointRatio;
			x2 = 50 + (Global.historyTable.get(i+1)[0] - startTime) * timeRatio;
			//y2 = chartHeight - Global.historyTable.get(i+1)[1] * pointRatio;
			y2 = chartHeight - (Global.historyTable.get(i+1)[1] - minPoint) * pointRatio;
			
			canvas.drawLine(x1, y1, x2, y2, paint);
		}
		plusY = y2+5;
		
		
		// minus
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
		for(i=0; i<Global.historyTable.size()-1; i++){
			x1 = 50 + (Global.historyTable.get(i)[0] - startTime) * timeRatio;
			//y1 = chartHeight - Global.historyTable.get(i)[2] * pointRatio;
			y1 = chartHeight - (Global.historyTable.get(i)[2] - minPoint) * pointRatio;
			x2 = 50 + (Global.historyTable.get(i+1)[0] - startTime) * timeRatio;
			//y2 = chartHeight - Global.historyTable.get(i+1)[2] * pointRatio;
			y2 = chartHeight - (Global.historyTable.get(i+1)[2] - minPoint) * pointRatio;
			
			canvas.drawLine(x1, y1, x2, y2, paint);
		}
		minusY = y2+5;
		
		// sum
		paint.setARGB(255, 255, 144, 0);
		paint.setStyle(Style.STROKE);
		for(i=0; i<Global.historyTable.size()-1; i++){
			x1 = 50 + (Global.historyTable.get(i)[0] - startTime) * timeRatio;
			//y1 = chartHeight - Global.historyTable.get(i)[3] * pointRatio;
			y1 = chartHeight - (Global.historyTable.get(i)[3] - minPoint) * pointRatio;
			x2 = 50 + (Global.historyTable.get(i+1)[0] - startTime) * timeRatio;
			//y2 = chartHeight - Global.historyTable.get(i+1)[3] * pointRatio;
			y2 = chartHeight - (Global.historyTable.get(i+1)[3] - minPoint) * pointRatio;
			
			canvas.drawLine(x1, y1, x2, y2, paint);
		}
		sumY = y2+5;

		// mark
		if (plusY <= minusY){
			if (plusY < 10) {
				plusY = 10;
			}
			
			if (Math.abs(plusY - minusY) < 15){
				while (Math.abs(plusY - minusY) < 15){
					minusY += 1;
				}
			} else if(Math.abs(plusY - sumY) < 15) {
				while (Math.abs(plusY - sumY) < 15){
					sumY += 1;
				}
			} else {
				if (minusY < sumY && sumY - minusY < 15){
					while (sumY - minusY < 15){
						minusY -= 1;
						sumY += 1;
						
					}
				} else if (sumY <= minusY && minusY - sumY < 15) {
					while (minusY - sumY < 15){
						sumY -= 1;
						minusY += 1;
					}
				}
			}
		} else {
			if (minusY < 10) {
				minusY = 10;
			}
			
			if (Math.abs(plusY - minusY) < 15){
				while (Math.abs(plusY - minusY) < 15){
					plusY += 1;
				}
			}
		}
		
		
		
		
		
		paint.setColor(Color.BLUE);
		canvas.drawText(""+Global.historyTable.get(i)[1], x2+10, plusY, paint);
		paint.setColor(Color.RED);
		canvas.drawText(""+Global.historyTable.get(i)[2], x2+10, minusY, paint);
		paint.setARGB(255, 255, 144, 0);
		canvas.drawText(""+Global.historyTable.get(i)[3], x2+10, sumY, paint);
	}
}

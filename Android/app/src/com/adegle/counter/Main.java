package com.adegle.counter;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ContentProviderOperation.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
	
	private Button add;
	private Button addSwap;
    private Button sub;
    private Button subSwap;
    private Button save;
    private TextView plus;
    private TextView minus;
    private TextView sum;
    private Spinner style;
    private Spinner stage;
    private EditText name;
    
    private int plusValue;
    private int minusValue;
    private int sumValue;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        bindViews();
        initViews();

    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 參數1:群組id, 參數2:itemId, 參數3:item順序, 參數4:item名稱
		menu.add(0, 0, 0, "Clear");
		menu.add(0, 1, 1, "Swap");
		menu.add(0, 2, 2, "History");
		menu.add(0, 3, 3, "Setting");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			// clear
			plusValue = 0;
			minusValue = 0;
			sumValue = 0;
			
			plus.setText("" + plusValue);
			minus.setText("" + minusValue);
			sum.setText("" + sumValue);

			Global.historyTable = new ArrayList<long[]>();
			break;
		case 1:
			// swap
			if (add.getVisibility() == View.VISIBLE){
				add.setVisibility(View.INVISIBLE);
				sub.setVisibility(View.INVISIBLE);
				addSwap.setVisibility(View.VISIBLE);
				subSwap.setVisibility(View.VISIBLE);
			} else {
				add.setVisibility(View.VISIBLE);
				sub.setVisibility(View.VISIBLE);
				addSwap.setVisibility(View.INVISIBLE);
				subSwap.setVisibility(View.INVISIBLE);
			}
			break;
		case 2:
			//history
			break;
		case 3:
			//setting
			
			break;
		default:
		}
		return super.onOptionsItemSelected(item);
	}

	private void initViews() {
		plusValue = 0;
		minusValue = 0;
		sumValue = 0;
		Global.styleValue = "";
		Global.stageValue = "";
		Global.nameValue = "";
	    Global.historyTable = new ArrayList<long[]>();
	    
	    
	    add.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int id = event.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT;

				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.argb(128, 0, 0, 255));
					increase();
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Color.LTGRAY);
					sub.setBackgroundColor(Color.LTGRAY);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					if (id == 0) {
						v.setBackgroundColor(Color.LTGRAY);
					} else {
						sub.setBackgroundColor(Color.LTGRAY);
					}
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					if (id == 0) {
						if (inSelfSide(v, event.getX(), event.getY())) {
							v.setBackgroundColor(Color.argb(128, 0, 0, 255));
							increase();
						}
					} else if(inOtherSide(v, sub, event.getX(1), event.getY(1))){
						sub.setBackgroundColor(Color.argb(128, 255, 0, 0));
						decrease();
					}
					break;
				}
				return true;
			}
		});
	    
	    addSwap.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int id = event.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT;

				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.argb(128, 0, 0, 255));
					increase();
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Color.LTGRAY);
					subSwap.setBackgroundColor(Color.LTGRAY);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					if (id == 0) {
						v.setBackgroundColor(Color.LTGRAY);
					} else {
						subSwap.setBackgroundColor(Color.LTGRAY);
					}
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					if (id == 0) {
						if (inSelfSide(v, event.getX(), event.getY())) {
							v.setBackgroundColor(Color.argb(128, 0, 0, 255));
							increase();
						}
					} else if(inOtherSide(v, subSwap, event.getX(1), event.getY(1))){
						subSwap.setBackgroundColor(Color.argb(128, 255, 0, 0));
						decrease();
					}
					break;
				}
				return true;
			}
		});
	   
	   
	    sub.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int id = event.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT;

				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.argb(128, 255, 0, 0));
					decrease();
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Color.LTGRAY);
					add.setBackgroundColor(Color.LTGRAY);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					if (id == 0) {
						v.setBackgroundColor(Color.LTGRAY);
					} else {
						add.setBackgroundColor(Color.LTGRAY);
					}
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					if (id == 0) {
						if (inSelfSide(v, event.getX(), event.getY())) {
							v.setBackgroundColor(Color.argb(128, 255, 0, 0));
							decrease();
						}
					} else if(inOtherSide(v, add, event.getX(1), event.getY(1))){
						add.setBackgroundColor(Color.argb(128, 0, 0, 255));
						increase();
					}
					break;
				}
				return true;
			}
		});
		    
	    subSwap.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int id = event.getAction() >> MotionEvent.ACTION_POINTER_ID_SHIFT;

				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.argb(128, 255, 0, 0));
					decrease();
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Color.LTGRAY);
					addSwap.setBackgroundColor(Color.LTGRAY);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					if (id == 0) {
						v.setBackgroundColor(Color.LTGRAY);
					} else {
						addSwap.setBackgroundColor(Color.LTGRAY);
					}
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					if (id == 0) {
						if (inSelfSide(v, event.getX(), event.getY())) {
							v.setBackgroundColor(Color.argb(128, 255, 0, 0));
							decrease();
						}
					} else if(inOtherSide(v, addSwap, event.getX(1), event.getY(1))){
						addSwap.setBackgroundColor(Color.argb(128, 0, 0, 255));
						increase();
					}
					break;
				}
				return true;
			}
		});
		
		
		ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"1A","2A","3A", "4A", "5A"});
		styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		style.setAdapter(styleAdapter);
		style.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			public void onItemSelected(AdapterView adapterView, View view, int position, long id){
				Global.styleValue = adapterView.getSelectedItem().toString();
			}
			public void onNothingSelected(AdapterView arg0) {
				Toast.makeText(Main.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
			}
		});
		
		
		ArrayAdapter<String> stageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"prelim", "final"});
		stageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stage.setAdapter(stageAdapter);
		stage.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			public void onItemSelected(AdapterView adapterView, View view, int position, long id){
				Global.stageValue = adapterView.getSelectedItem().toString();
			}
			public void onNothingSelected(AdapterView arg0) {
				Toast.makeText(Main.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
			}
		});
		
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Global.nameValue = name.getText().toString();
				if(Global.nameValue.equals("")) {
					Global.nameValue = "Anonymous";
				}
				
				if(Global.historyTable.size() >= 2){
					Intent intent = new Intent();
					intent.setClass(Main.this, Chart.class);
					startActivity(intent);
				} else {
					AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(Main.this);
					mAlertDialog.setTitle("Info")
						.setMessage("There is no data to display!")
						.setPositiveButton("Back", null)
						.setIcon(R.drawable.icon)
						.show();
					
				}
				//Main.this.finish();
			}
		});
	}
	
	protected boolean inSelfSide(View me, float x, float y) {
		int meWidth = me.getWidth();
		int meHeight = me.getWidth();
		
		if (0 <= x && x <= meWidth &&
			0 <= y && y <= meHeight) {
			return true;
		}
		return false;
	}

	protected boolean inOtherSide(View me, View other, float x, float y) {
		int[] meLoc = {0, 0};
		me.getLocationOnScreen(meLoc);
		
		int[] otherLoc = {0, 0};
		other.getLocationOnScreen(otherLoc);
		int otherWidth = other.getWidth();
		int otherHeight = other.getWidth();
		
		if (otherLoc[0] <= meLoc[0]+x && meLoc[0]+x <= otherLoc[0]+otherWidth &&
			otherLoc[1] <= meLoc[1]+y && meLoc[1]+y <= otherLoc[1]+otherHeight){
			return true;
		}
		return false;
	}

	private void writeHistory() {
		if (Global.historyTable.size() == 0){
			Global.historyTable = new ArrayList<long[]>();
			long[] current = {System.currentTimeMillis()-500, 0, 0, 0};
			Global.historyTable.add(current);
		}
		long[] current = {System.currentTimeMillis(), (long)plusValue, (long)minusValue, (long)sumValue};
		Global.historyTable.add(current);
	}
	
	protected void increase() {
		plusValue += 1;
		sumValue += 1;
		plus.setText(""+ plusValue);
		sum.setText(""+ sumValue);
		vibrate(50);
		writeHistory();
	}

	protected void decrease() {
		minusValue += 1;
		sumValue -= 1;
		minus.setText(""+ minusValue);
		sum.setText(""+ sumValue);
		vibrate(50);
		writeHistory();
	}

	protected void vibrate(int duration) {
		Vibrator myVibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
		myVibrator.vibrate(duration);
	}

	private void bindViews() {
		add = (Button)findViewById(R.id.add);
		addSwap = (Button)findViewById(R.id.addSwap);
		sub = (Button)findViewById(R.id.sub);
		subSwap = (Button)findViewById(R.id.subSwap);
	    save = (Button)findViewById(R.id.save);
	    plus = (TextView)findViewById(R.id.plus);
	    minus = (TextView)findViewById(R.id.minus);
	    sum = (TextView)findViewById(R.id.sum);
	    style = (Spinner)findViewById(R.id.style);
	    stage = (Spinner)findViewById(R.id.stage);
	    name = (EditText)findViewById(R.id.name);
	}
}
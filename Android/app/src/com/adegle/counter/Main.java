package com.adegle.counter;

import android.R.string;
import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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
    private String styleValue;
    private String stageValue;
    private String nameValue;
	
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
		styleValue = "";
	    stageValue = "";
	    nameValue = "";
		
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				increase();
			}
		});
		
		addSwap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				increase();
			}
		});
		
		sub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				decrease();
			}
		});
		
		subSwap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				decrease();
			}
		});
		
		
		ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"1A","2A","3A", "4A", "5A"});
		styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		style.setAdapter(styleAdapter);
		style.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			public void onItemSelected(AdapterView adapterView, View view, int position, long id){
				styleValue = adapterView.getSelectedItem().toString();
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
				stageValue = adapterView.getSelectedItem().toString();
			}
			public void onNothingSelected(AdapterView arg0) {
				Toast.makeText(Main.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
			}
		});
		
		
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nameValue = name.getText().toString();
				if(nameValue.equals("")) {
					nameValue = "Anonymous";
				}
				Log.e("123", styleValue + ", " + 
						     stageValue + ", " + 
						     nameValue + ", " +
						     plusValue + ", " +
						     minusValue + ", " +
						     sumValue);
			}
		});
	}

	protected void decrease() {
		minusValue += 1;
		sumValue -= 1;
		minus.setText(""+ minusValue);
		sum.setText(""+ sumValue);
		vibrate(50);
	}

	protected void increase() {
		plusValue += 1;
		sumValue += 1;
		plus.setText(""+ plusValue);
		sum.setText(""+ sumValue);
		vibrate(50);
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
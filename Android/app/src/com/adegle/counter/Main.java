package com.adegle.counter;

import android.R.string;
import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
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
    private Button sub;
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

	private void initViews() {
		plusValue = 0;
		minusValue = 0;
		sumValue = 0;
		styleValue = "1A";
	    stageValue = "prelim";
	    nameValue = "";
		
		// TODO Auto-generated method stub
		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				plusValue = Integer.valueOf((String)plus.getText()) + 1;
				plus.setText(""+ plusValue);
				sumValue += 1;
				sum.setText(""+ sumValue);
				vibrate(50);
			}
		});
		
		sub.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				minusValue = Integer.valueOf((String)minus.getText()) + 1;
				minus.setText(""+ minusValue);
				sumValue -= 1;
				sum.setText(""+ sumValue);
				vibrate(50);
			}
		});
		
		ArrayAdapter<String> styleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"1A","2A","3A", "4A", "5A"});
		styleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		style.setAdapter(styleAdapter);
		style.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
			public void onItemSelected(AdapterView adapterView, View view, int position, long id){
				styleValue = adapterView.getSelectedItem().toString();
				name.setText(styleValue + ", " + stageValue);
				Log.e("123", "styleValue " + styleValue);
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
				name.setText(styleValue + ", " + stageValue);
				Log.e("123", "stageValue " + stageValue);
			}
			public void onNothingSelected(AdapterView arg0) {
				Toast.makeText(Main.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
			}
		});
		
	
		
//		21.        //設定項目被選取之後的動作
//		22.        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
//		23.            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
//		24.                Toast.makeText(MainActivity.this, "您選擇"+adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
//		25.            }
//		26.            public void onNothingSelected(AdapterView arg0) {
//		27.                Toast.makeText(MainActivity.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
//		28.            }
//		29.        });
	}

	protected void vibrate(int duration) {
		Vibrator myVibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
		myVibrator.vibrate(duration);
	}

	private void bindViews() {
		add = (Button)findViewById(R.id.add);
		sub = (Button)findViewById(R.id.sub);
	    save = (Button)findViewById(R.id.save);
	    plus = (TextView)findViewById(R.id.plus);
	    minus = (TextView)findViewById(R.id.minus);
	    sum = (TextView)findViewById(R.id.sum);
	    style = (Spinner)findViewById(R.id.style);
	    stage = (Spinner)findViewById(R.id.stage);
	    name = (EditText)findViewById(R.id.name);
	}
}
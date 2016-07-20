package com.dimiprount.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	EditText etText;
	Button bCancel, bSave, bView;
	final Context context = this;
	// Button bYellow, bGreen, bPink, bWhite, bGrey;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etText = (EditText) findViewById(R.id.etText);
		bCancel = (Button) findViewById(R.id.bCancel);
		bSave = (Button) findViewById(R.id.bSave);
		bView = (Button) findViewById(R.id.bView);
		
		bCancel.setOnClickListener(this);
		bSave.setOnClickListener(this);
		bView.setOnClickListener(this);

	/*	// Set random colors in edittext
		int[] myColors = getResources().getIntArray(R.array.mycolors);
		int randomColor = myColors[new Random().nextInt(myColors.length)];
		etText.setBackgroundColor(randomColor);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save) {
			save();
			// Hide keyboard when icon save is pushed
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(bSave.getWindowToken(), 0);
		}
		if (id == R.id.cancel) {
			etText.setText("");
			// Hide keyboard when icon cancel is pushed
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(bCancel.getWindowToken(), 0);
		}
		if (id == R.id.view) {
			startActivity(new Intent(this, DbView.class));
		}
	/**	if (id == R.id.yellow) {
			etText.setBackgroundColor(Color.parseColor("#ffff00"));
		}
		if (id == R.id.green) {
			etText.setBackgroundColor(Color.parseColor("#adff2f"));
		}
		if (id == R.id.pink) {
			etText.setBackgroundColor(Color.parseColor("#ffc0cb"));
		}
		if (id == R.id.white) {
			etText.setBackgroundColor(Color.parseColor("#ffffff"));
		}
		if (id == R.id.grey) {
			etText.setBackgroundColor(Color.parseColor("#808080"));
		}*/
		return super.onOptionsItemSelected(item);
	}
	
	// Stop and exit app pushing the back button
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);	// Close the app and don't ask which app you want to use
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Move activities to background
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Login activity will start firstly
		startActivity(intent);
		
		this.finishAffinity();	// If the app is opened again, it won't have the password in the edittext
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bCancel:
			etText.setText("");
			break;

		case R.id.bSave:
			save();
			break;

		case R.id.bView:
			startActivity(new Intent(this, DbView.class));
			break;
		}
	}

	private void save() {
		// TODO Auto-generated method stub
		if(etText.length() == 0)
			Toast.makeText(this, "Nothing to save!", Toast.LENGTH_SHORT).show();
		
		else{
			boolean didItWork = true;
			try {
				String mnotes = etText.getText().toString();
	
				DbDatabase entry = new DbDatabase(this);
	
				entry.open();
				entry.createEntry(mnotes);
				entry.close();
			} catch (Exception e) {
				// TODO: handle exception
				didItWork = false;
				Toast t = Toast.makeText(this,
						"Data hasn't been saved. Please try again!",
						Toast.LENGTH_SHORT);
				t.show();
			} finally {
				if (didItWork) {
					Toast t = Toast
							.makeText(this, "Data has been saved successfully!",
									Toast.LENGTH_SHORT);
					t.show();
				}
				etText.setText("");
				}
			}
		}
	}


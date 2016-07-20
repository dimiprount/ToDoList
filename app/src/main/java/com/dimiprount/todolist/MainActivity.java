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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.save) {
			save();
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(bSave.getWindowToken(), 0);
		}
		if (id == R.id.cancel) {
			etText.setText("");
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(bCancel.getWindowToken(), 0);
		}
		if (id == R.id.view) {
			startActivity(new Intent(this, DbView.class));
		}
	
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		
		this.finishAffinity();
		
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


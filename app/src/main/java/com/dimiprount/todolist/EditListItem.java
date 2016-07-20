package com.dimiprount.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditListItem extends ActionBarActivity implements OnClickListener {

	EditText eteli;
	Button bUpdate, bCancel, bDelete;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editlistitem);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		eteli = (EditText) findViewById(R.id.eteli);
		eteli.getText().toString();
		Bundle recdData = getIntent().getExtras();
		String edData = recdData.getString("item");
		eteli.setText(edData);

		bUpdate = (Button) findViewById(R.id.bUpdate);
		bCancel = (Button) findViewById(R.id.bCanceleli);
		bDelete = (Button) findViewById(R.id.bDelete);

		bUpdate.setOnClickListener(this);
		bCancel.setOnClickListener(this);
		bDelete.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.update, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.update) {
			update();
		}
		if (id == R.id.cancel) {
			startActivity(new Intent(this, DbView.class));
		}

		if (id == R.id.delete) {
			AlertDialog.Builder adbdel = new AlertDialog.Builder(context);

			adbdel.setMessage("Delete?")
					.setCancelable(false)
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
													int id) {
									
									dialog.cancel();
								}
							})
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
													int id) {
									delete();
								}
							});
			AlertDialog alertDialog = adbdel.create();

			alertDialog.show();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
			case R.id.bUpdate:
				update();
				break;

			case R.id.bCanceleli:
				startActivity(new Intent(this, DbView.class));
				break;

			case R.id.bDelete:
				AlertDialog.Builder adbdel = new AlertDialog.Builder(context);

				adbdel.setMessage("Delete?")
						.setCancelable(false)
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
														int id) {
										
										dialog.cancel();
									}
								})
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
														int id) {
										delete();
									}
								});
				AlertDialog alertDialog = adbdel.create();

				alertDialog.show();
				break;
		}


	}


	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, DbView.class));
	}

	private void update() {
		// TODO Auto-generated method stub

		Bundle extras= getIntent().getExtras();
		String oldnotes = extras.getString("item");

		if(eteli.length() != 0){
			boolean didItWork = true;
			try {
				DbDatabase ex = new DbDatabase(this);
				String newnotes = eteli.getText().toString();
				ex.open();
				ex.updateEntry(oldnotes, newnotes);
				ex.close();
			} catch (Exception e) {
				didItWork = false;
				// Set up a dialog window
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Failure!");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();

			}finally {
				if (didItWork) {
					Toast t = Toast
							.makeText(this, "Data has been updated successfully!",
									Toast.LENGTH_SHORT);
					t.show();
					startActivity(new Intent(this, DbView.class));
				}
			}
		}else{
			Toast.makeText(this, "Nothing to update!", Toast.LENGTH_SHORT).show();
		}
	}

	private void delete() {
		// TODO Auto-generated method stub
		String sData = eteli.getText().toString();
		boolean didItWork = true;
		try {
			DbDatabase ex = new DbDatabase(this);

			ex.open();
			ex.deleteItem(sData);
			ex.close();
		} catch (Exception e) {
			didItWork = false;
			// Set up a dialog window
			String error = e.toString();
			Dialog d = new Dialog(this);
			d.setTitle("Failure!");
			TextView tv = new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();

		}finally {
			if (didItWork) {
				startActivity(new Intent(this, DbView.class));
				Toast t = Toast.makeText(this, "Data has been deleted successfully!", Toast.LENGTH_SHORT);
				t.show();
			}
		}
	}
}

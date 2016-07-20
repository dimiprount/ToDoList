package com.dimiprount.todolist;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class DbView extends ActionBarActivity implements OnItemClickListener{

	MyAdapter ma;
	ListView lv;
	ArrayList<Notepad> data = new ArrayList<Notepad>();
	DbDatabase info;
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbview);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		lv = (ListView) findViewById(R.id.listView1);
		info = new DbDatabase(this);
		info.open();
		data = info.getData();
		info.close();

		ma = new MyAdapter(this,data);
		lv.setAdapter(ma);
		lv.setOnItemClickListener(this);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lv.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				MenuInflater mi = mode.getMenuInflater();
				mi.inflate(R.menu.multidelete, menu);
				return true;
			}

			@Override
			public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub

				final SparseBooleanArray selected = lv.getCheckedItemPositions();
				switch (item.getItemId()) {
					case R.id.multidelete:
						AlertDialog.Builder adb = new AlertDialog.Builder(context);

						adb.setMessage("Delete?").setCancelable(true)
								.setNegativeButton("No",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										dialog.cancel();
									}
								})
								.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										for(int i=selected.size() - 1; i >= 0; i--) {
											if(selected.valueAt(i)){
												Notepad selectedItem = ma.getItem(selected.keyAt(i));
												info.delete(selectedItem);
												ma.remove(selectedItem);

											}
											mode.finish();

										}
									}
								});

						AlertDialog alertDialog = adb.create();

						alertDialog.show();


						return true;

					default:
						return false;
				}

			}



			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
												  long id, boolean checked) {
				mode.setTitle("Selected Notes: " +lv.getCheckedItemCount());
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent i=new Intent(arg1.getContext(), EditListItem.class);
		String item = data.get(arg2).getNote();
		i.putExtra("item", item);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, MainActivity.class));
	}

}
	

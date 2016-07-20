package com.dimiprount.todolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends ActionBarActivity implements OnClickListener {

	EditText etpassword,etconfirmpassword;
	Button bSignUp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_form);
		
		etpassword = (EditText) findViewById(R.id.etpassword);
		etconfirmpassword = (EditText) findViewById(R.id.etconfirmpassword);
		bSignUp = (Button) findViewById(R.id.bSignUp);
		
		bSignUp.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bSignUp:
			String pass1 = etpassword.getText().toString();
			String pass2 = etconfirmpassword.getText().toString();
			if (pass1.length() != 0) {	// The password cannot be empty
				if (!pass1.equals(pass2)) // If it were numbers we could use
											// if(pass1 == pass2)
					Toast.makeText(this, "Passwords don't match!",
							Toast.LENGTH_SHORT).show();
				else {
					// Insert password in the database
					Password p = new Password();
					p.setPass(pass1);

					DbDatabase inspas = new DbDatabase(this);

					inspas.open();
					inspas.insertPassword(p);
					inspas.close();
					
					startActivity(new Intent(this, Login.class));
				}
			} else {
				Toast.makeText(this, "Set password!", Toast.LENGTH_SHORT)
						.show();

			}

			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {		// Hide keyboard when pressing out of Eddittext
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		return true;
	}

}

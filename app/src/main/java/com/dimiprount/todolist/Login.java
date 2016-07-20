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

public class Login extends ActionBarActivity implements OnClickListener {

	EditText etlfpassword;
	Button blflogin, blfSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_form);

		etlfpassword = (EditText) findViewById(R.id.etpassword);
		blflogin = (Button) findViewById(R.id.blflogin);

		blflogin.setOnClickListener(this);
		
		first_user_check();
	}

	@Override
	public void onClick(View v) {		
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.blflogin:
			String passl = etlfpassword.getText().toString();
			
			if(passl.length() !=0)
			{
				DbDatabase sp = new DbDatabase(this);
	
				sp.open();
				String password = sp.searchpassword(passl);
				sp.close();
	
				if (passl.equals(password)) {
					startActivity(new Intent(this, MainActivity.class));
				} else
					Toast.makeText(this, "Incorect password!", Toast.LENGTH_SHORT)
							.show();
			}else
				Toast.makeText(this, "Write password!", Toast.LENGTH_SHORT)
				.show();
			
			break;

		case R.id.blfSignUp:
			startActivity(new Intent(this, SignUp.class));
			break;
		}
	}
	public void first_user_check()
    {
        blfSignUp = (Button) findViewById(R.id.blfSignUp);
        blfSignUp.setOnClickListener(this);
        DbDatabase fuc = new DbDatabase(this);
        int count = fuc.getCount();
        fuc.close();
        if(count == 0)
        {
        	blfSignUp.setVisibility(View.VISIBLE);
        }
        else
        {
        	blfSignUp.setVisibility(View.GONE);
        }
    }

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}

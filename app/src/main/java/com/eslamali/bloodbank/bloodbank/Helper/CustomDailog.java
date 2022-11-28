package com.eslamali.bloodbank.bloodbank.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.eslamali.bloodbank.bloodbank.Data.Local.SharedPrefrance;
import com.eslamali.bloodbank.R;
import com.eslamali.bloodbank.bloodbank.ui.activity.UserRecycleActivity;


public class CustomDailog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Button yes, no;

    public CustomDailog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dailog);
        setCancelable(false);
        setCanceledOnTouchOutside(true);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                SharedPrefrance.clean(activity);
                Intent i = new Intent(activity, UserRecycleActivity.class);
                activity.startActivity(i);
                activity.finish();
                break;
            case R.id.btn_no:
                cancel();
                break;
            default:
                break;
        }
        dismiss();
    }
}

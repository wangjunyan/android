package com.example.apidemowjy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ManualLayoutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView myTextView = new TextView(this);
        EditText myEditText = new EditText(this);
        myTextView.setText("Enter Text Below");
        myEditText.setText("Text Goes Here!");
        int lHeight = LinearLayout.LayoutParams.MATCH_PARENT;
        int lWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
        
        ll.addView(myTextView, new LinearLayout.LayoutParams(lHeight, lWidth));
        ll.addView(myEditText, new LinearLayout.LayoutParams(lHeight, lWidth));
        setContentView(ll);
    }
}

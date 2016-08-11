package com.sianav.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sianav.superflipcheckbox.FlipCheckbox;

public class MainActivity extends AppCompatActivity {

    private FlipCheckbox mFlipCheckboxSmall;
    private FlipCheckbox mFlipCheckboxMedium;
    private FlipCheckbox mFlipCheckboxBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }

    private void initViews() {
        mFlipCheckboxSmall = (FlipCheckbox) findViewById(R.id.checkboxSmall);
        mFlipCheckboxMedium = (FlipCheckbox) findViewById(R.id.checkboxMedium);
        mFlipCheckboxBig = (FlipCheckbox) findViewById(R.id.checkboxBig);
    }

    private void initEvents() {
        mFlipCheckboxSmall.setOnCheckedChangeListener(new FlipCheckbox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                if (isChecked) {
                    mFlipCheckboxBig.setUncheckedBackgroundTint(getResources().getColor(R.color.black));
                    mFlipCheckboxBig.setUncheckedTint(getResources().getColor(R.color.white));
                    mFlipCheckboxMedium.setUncheckedBackgroundTint(getResources().getColor(R.color.blue));
                } else {
                    mFlipCheckboxBig.setUncheckedBackgroundTint(0);
                    mFlipCheckboxBig.setUncheckedTint(getResources().getColor(R.color.blue));
                    mFlipCheckboxMedium.setUncheckedBackgroundTint(getResources().getColor(R.color.red));
                }
            }
        });
    }
}

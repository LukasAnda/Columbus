package com.example.columbus;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lukasanda.columbus.Columbus;

abstract class ColumbusSampleActivity extends FragmentActivity {
  private ToggleButton showHide;
  private FrameLayout sampleContent;
  private TextView textView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.columbus_sample_activity);
    textView = findViewById(R.id.text);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Columbus.with().load().stop();
  }


  @Override public void setContentView(int layoutResID) {
    getLayoutInflater().inflate(layoutResID, sampleContent);
  }

  @Override public void setContentView(View view) {
    sampleContent.addView(view);
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    sampleContent.addView(view, params);
  }
}

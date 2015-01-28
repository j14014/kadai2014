package com.example.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CHARA extends Activity{

	boolean p1 = true;
	boolean p2 = true;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chara);
	}
	
	public void onClick(View v){
		if(p1 == true && p2 == true){
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.setImageResource(R.drawable.gsuper);
		}
	}
}

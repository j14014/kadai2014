package com.example.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class GC extends Activity{

	MediaPlayer bgm;

	AudioManager am;;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameclear);
        setTitle("SVB");

		Button b1 = (Button)findViewById(R.id.button1);
		b1.setOnClickListener(new View.OnClickListener(){

			public void onClick(View v){
				Intent intent = new Intent(GC.this, MainActivity.class);
				startActivity(intent);
			}
		});

		am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		int ringVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, AudioManager.FLAG_SHOW_UI);
	}

	public void onResume(){
		super.onResume();
		bgm = MediaPlayer.create(this, R.raw.win);
		bgm.start();
	}

	public void onPause(){
		super.onPause();
		bgm.release();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    // TODO Auto-generated method stub
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	            return false;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
}
package com.example.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GUI1 extends Activity implements OnClickListener, TextToSpeech.OnInitListener, OnCompletionListener
{
    // ダミーの識別子
    private static final int REQUEST_CODE = 0;

    // 音声合成用
    TextToSpeech tts = null;

    ProgressBar bar1 = null;
    ProgressBar bar2 = null;
    int i = 0;
    int j = 100;

    boolean player1 = true;

    TextView tv1;
    TextView tv2;

    float tx;
    float ty;

	MediaPlayer bgm1;
	MediaPlayer bgm2;
	MediaPlayer bgm3;
	MediaPlayer bgm4;

	int c1 = 1;
	int c2 = 1;

	boolean endflag = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gui1);
        setTitle("SVB");
		bar1 = (ProgressBar)findViewById(R.id.progressBar1);
		bar1.setMax(100);
		bar1.setProgress(j);
		bar2 = (ProgressBar)findViewById(R.id.progressBar2);
		bar2.setMax(100);
		bar2.setProgress(i);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setText("攻撃");
        button1.setOnClickListener( this );

        tts = new TextToSpeech(this, this);

        tv1 = (TextView)findViewById(R.id.textView1);
        tv2 = (TextView)findViewById(R.id.textView2);

		String text1 = tv1.getText().toString();
		String text2 = tv2.getText().toString();

		tv1.setTextColor(Color.RED);

        bgm1 = MediaPlayer.create(this, R.raw.db_ki);
        bgm2 = MediaPlayer.create(this, R.raw.db_g);
        bgm3 = MediaPlayer.create(this, R.raw.db_f1);
        bgm4 = MediaPlayer.create(this, R.raw.db_f2);

        bgm1.setOnCompletionListener(this);
        bgm2.setOnCompletionListener(this);
        bgm3.setOnCompletionListener(this);
        bgm4.setOnCompletionListener(this);

    }


    @Override
    public void onClick(View v)
    {
        try {
            // "android.speech.action.RECOGNIZE_SPEECH" を引数にインテント作成
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

            // 「お話しください」の画面で表示される文字列
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声認識中です");

            // 音声入力開始
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // 非対応の場合
            Toast.makeText(this, "音声入力に非対応です。", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // インテントの発行元を限定
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            // 音声入力の結果の最上位のみを取得
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String s = results.get(0);

            // 表示
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();

            // 音声合成して発音
            if(tts.isSpeaking()) {
                tts.stop();
            }
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);

    		int x = 0;
    	    String[] array = {"月", "太陽", "地球"};
    		List<String> list=Arrays.asList(array);
    		Collections.shuffle(list);
    		String[] array2 =(String[])list.toArray(new String[list.size()]);
    		String result = array2[0];

            boolean matched = false;
            if (s.equals(result)) { // Stringと比較
            	if(player1) {
            		j -= 30;
                	bar1.setProgress(j);
            	} else {
            		i += 30;
            		bar2.setProgress(i);
            	}
            } else {
            	if(player1) {
            		j -= 50;
                	bar1.setProgress(j);
            	} else {
            		i += 50;
            		bar2.setProgress(i);
            	}
            }
            player1 = !player1;

            if(player1){
        		tv1.setTextColor(Color.RED);
        		tv2.setTextColor(Color.BLACK);
            }else{
            	tv2.setTextColor(Color.RED);
            	tv1.setTextColor(Color.BLACK);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

        if(i == 50){
			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
	        imageView.setImageResource(R.drawable.gsuper);
        }

        if(j == 50){
			ImageView imageView = (ImageView) findViewById(R.id.imageView2);
	        imageView.setImageResource(R.drawable.fmizuno);
        }

        if(j <= 0){
        	Intent intent1 = new Intent(this, GC.class);
    		startActivity(intent1);
        }

        if(i >= 100){
        	Intent intent2 = new Intent(this, GO.class);
        	startActivity(intent2);
        }

    }
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            // 音声合成の設定を行う

            float pitch = 1.0f; // 音の高低
            float rate = 1.0f; // 話すスピード
            Locale locale = Locale.US; // 対象言語のロケール
                // ※ロケールの一覧表
                //   http://docs.oracle.com/javase/jp/1.5.0/api/java/util/Locale.html

            tts.setPitch(pitch);
            tts.setSpeechRate(rate);
            tts.setLanguage(locale);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( tts != null )
        {
            // 破棄
            tts.shutdown();
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(GUI1.this, ReadMe.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    // TODO Auto-generated method stub
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	            return false;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}*/

	public boolean onTouchEvent(MotionEvent e){
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			tx = e.getX();
			ty = e.getY();
			//System.out.println("x "+tx);
			//System.out.println("y "+ty);

		if(!endflag){
			return true;
		}
			if(tx >= 0 && tx <= 246 && ty >= 219 && ty <= 572){
				if(c1 == 1){
					ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			        imageView.setImageResource(R.drawable.gsuper);
					bgm1.start();
					//System.out.println("ｼｭﾝｼｭﾝｼｭﾝｼｭﾝｼｭﾝｼｭﾝｗｗｗｗｗｗｗｗｗｗｗ");
				}
				if(c1 == 2){
					bgm2.start();
				}
				c1 = ++c1 % 3;
				//System.out.println("OK");
			}else{
				if(tx >= 964 && tx <= 1270 && ty >= 238 && ty <= 568){
					if(c2 == 1){
						ImageView imageView = (ImageView) findViewById(R.id.imageView2);
				        imageView.setImageResource(R.drawable.fmizuno);
						bgm3.start();
						//System.out.println("私の戦闘力は530000です。");
					}
					if(c2 == 2){
						bgm4.start();
						//System.out.println("この星を消す！");
					}
					c2 = ++c2 % 3;
					//System.out.println("OK");
				}
			}
		}
		return true;
	}

	public void onCompletion(MediaPlayer arg0){

	}

}

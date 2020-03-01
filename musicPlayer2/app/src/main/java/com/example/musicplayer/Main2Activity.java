package com.example.musicplayer;

        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.speech.RecognizerIntent;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.content.Intent;
        import android.widget.Toast;

        import java.nio.channels.ClosedSelectorException;
        import java.util.ArrayList;
        import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    MediaPlayer mySong;

    private TextView textOutput;

    private Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textOutput = (TextView) findViewById(R.id.textOutput);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
        //mySong contains mp3
        //mySong=MediaPlayer.create(MainActivity.this, R.raw.dance1);
    }

    // this will open next activity
    public void openActivity3() {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }

    public void getSpeech(View view)
    {
        // get the input of the user from speech
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // check if the feature is supported on the device
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        }
        else
        {
            Toast.makeText(this, "Your Device Does Not Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    // get callback over here
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case 10:
                if(resultCode == RESULT_OK && data != null)
                {
                    // get results from recognizerintent
                    ArrayList<String> output =  data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textOutput.setText(output.get(0)); //display results to text view
                }
                break;
        }
    }

    //    public void playGenre(View view) {
//        mySong.start();
//    }
//
////    @Override
//    protected void onPause() {
//        super.onPause();
//        mySong.release();
//    }
}

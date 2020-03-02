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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textOutput = (TextView) findViewById(R.id.textOutput);
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
}

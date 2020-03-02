package com.example.musicplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.models.Song;
import com.example.musicplayer.persistence.SongRepository;
import com.example.musicplayer.util.Utility;

import java.util.ArrayList;
import java.util.Locale;

public class SongActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher
{
    private static final String TAG = "SongActivity";

    //ui components
    private EditText mEditText;
    private EditText mEditTitle;
    private TextView mViewTitle;
    private RelativeLayout mCheckContainer;
    private RelativeLayout mBackArrowContainer;
    private ImageButton  mCheck;
    private ImageButton mBackArrow;

    //vars
    private boolean mIsNewSong;
    private Song mInitialSong;
    private GestureDetector mGestureDetector;

    //edit and view mode vars
    private static final int EDIT_MODE_ENABLED=1;
    private static final int EDI_MODE_DISABLED=0;
    private int mMode; // keep  track of the  state

    private SongRepository mSongRepository;
    private Song mFinalSong;

    // microphone
    private TextView textOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        mEditText = findViewById(R.id.song_text);
        mEditTitle = findViewById(R.id.song_edit_title);
        mViewTitle = findViewById(R.id.song_text_title);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);
        mSongRepository = new SongRepository(this);
//        textOutput = (TextView) findViewById(R.id.textOutput);

        // this is a new song, (EDIT MODE)
        if(getIncomingIntent())
        {
            setNewSongProperties();
            enableEditMode();
        }
        else  // not a new note (VIEW MODE)
        {
            setSongProperties();
            disableContentInteraction();
        }
        setListeners();
    }

    // microphone stuff
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
                    mFinalSong.setContent(output.get(0));
                    mEditText.setText(output.get(0)); //display results to text view
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            // want to disable  edit mode when you click checkmark
            case R.id.toolbar_check:
            {
                hideSoftKeyboard();
                disableEditMode();
                break;
            }
            case R.id.song_text_title:
            {
                enableEditMode();
                mEditTitle.requestFocus();
                mEditTitle.setSelection(mEditTitle.length()); // sets cursor to end of the string
                break;
            }
            case R.id.toolbar_back_arrow:
            {
                finish(); // destroys  activity
                break;
            }

        }
    }

    private void disableContentInteraction()
    {
        mEditText.setKeyListener(null);
        mEditText.setFocusable(false);
        mEditText.setFocusableInTouchMode(false);
        mEditText.setCursorVisible(false);
        mEditText.clearFocus();
    }

    private void enableContentInteraction()
    {
        mEditText.setKeyListener(new EditText(this).getKeyListener());
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.setCursorVisible(true);
        mEditText.requestFocus();

    }
    @Override
    public void onBackPressed() {
        //  want to register a click to the checkmark
        // instead of doing default behavior
        // instead do  onClick method
        // simulating a click to the checkmark
        if(mMode == EDIT_MODE_ENABLED)
        {
            onClick(mCheck);
        }
        else
        {
            super.onBackPressed();
        }
    }

    private boolean getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent");

        if(getIntent().hasExtra("selected_song"))
        {
            mInitialSong = getIntent().getParcelableExtra("selected_song");

            mFinalSong = new Song();
            mFinalSong.setTitle(mInitialSong.getTitle());
            mFinalSong.setContent(mInitialSong.getContent());
            mFinalSong.setTimestamp(mInitialSong.getTimestamp());
            mFinalSong.setId(mInitialSong.getId());

            mMode = EDI_MODE_DISABLED;
            mIsNewSong = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewSong = true;
        return  true;
    }

    private void saveChanges(){
        if(mIsNewSong)
        {
            saveNewSong();
        }
        else
        {
            updateSong();
        }
    }

    private void updateSong()
    {
        mSongRepository.updateSong(mFinalSong);
    }


    private void saveNewSong()
    {
        mSongRepository.insertSongTask(mFinalSong);
    }

    // want to  hide back arrow, show check mark
    // hide the title and show edit title
    private void enableEditMode()
    {
        Log.d(TAG, "IN EDIT MODE");
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);
        mViewTitle.setVisibility(View.GONE);
        mEditTitle.setVisibility(View.VISIBLE);
        mMode  =   EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void  disableEditMode()
    {
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);
        mViewTitle.setVisibility(View.VISIBLE);
        mEditTitle.setVisibility(View.GONE);
        mMode  =   EDI_MODE_DISABLED;
        disableContentInteraction();

        // Want to see if final song is different than initial song
        String temp = mEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if(temp.length() > 0)
        {
            mFinalSong.setTitle(mEditTitle.getText().toString());
            mFinalSong.setContent(mEditText.getText().toString());
            String timestamp = Utility.getCurrentTimestamp();
            mFinalSong.setTimestamp(timestamp);

            if(!mFinalSong.getContent().equals(mInitialSong.getContent()) || !mFinalSong.getTitle().equals(mInitialSong.getTitle()))
            {
                saveChanges();
            }
        }
    }

    private void hideSoftKeyboard()
    {
        InputMethodManager imm =  (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view  == null)
        {
            view  = new View(this);

        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setSongProperties()
    {
        Log.d("SetSongProperties", "song:"+mInitialSong.getContent());
        mViewTitle.setText(mInitialSong.getTitle());
        mEditTitle.setText(mInitialSong.getTitle());
        mEditText.setText(mInitialSong.getContent());

    }

    private void setNewSongProperties()
    {
        Log.d("SetNewwwwSongProperties", "song:");
        mViewTitle.setText("Song Title");
        mEditTitle.setText("Song Title");

        mInitialSong = new Song();
        mFinalSong = new Song();
        mInitialSong.setTitle("Song Title");
        mFinalSong.setTitle("Song Title");
    }

    private void setListeners()
    {
        mEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditTitle.addTextChangedListener(this);

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap: double tapped");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

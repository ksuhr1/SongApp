package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
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

import com.example.musicplayer.models.Song;

public class SongActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener{

    private static final String TAG = "SongActivity";

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

    private boolean getIncomingIntent()
    {
        Log.d(TAG, "getIncomingIntent");

        if(getIntent().hasExtra("selected_song"))
        {
            mInitialSong = getIntent().getParcelableExtra("selected_song");
            mIsNewSong = false;
            mMode = EDI_MODE_DISABLED;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewSong = true;
        return  true;
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
    }

    private void setListeners()
    {
        mEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);

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
}

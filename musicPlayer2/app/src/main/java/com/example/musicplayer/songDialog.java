package com.example.musicplayer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class songDialog extends AppCompatDialogFragment {
    private EditText  editSongTitle ;
    private songDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater  = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_main3, null);

        builder.setView(view)
                .setTitle("Add Song")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        String song = editSongTitle.getText().toString();
//                        listener.applyText(song);
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String song = editSongTitle.getText().toString();
                        Log.d("OnclickDialog", song );
                        listener.applyText(song);

                    }
                });
        editSongTitle = view.findViewById(R.id.editText);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (songDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "must implement songDialogListener");
        }
    }

    public interface songDialogListener {
        void applyText(String song);
    }
}

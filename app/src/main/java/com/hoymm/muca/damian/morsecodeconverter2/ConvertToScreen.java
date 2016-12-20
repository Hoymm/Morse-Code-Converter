package com.hoymm.muca.damian.morsecodeconverter2;

import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertToScreen extends Activity {
    Button myConvertScreenButton;
    MediaPlayer beepSound;
    ImageButton soundEnableButton;
    private int currentColor,screenSPEED,currentStringIndexMorsShowing;
    //private boolean ifConvertToScreenButtonClicked;
    private String currentConverterText;
    private TextView  screenFlashOutputMors;
    private EditText screenEditTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_to_screen);
        initalizateObjects();
    }

    //... initializate objects function
    void initalizateObjects(){

        myConvertScreenButton = (Button) findViewById(R.id.myConvertScreenButton);
        beepSound = MediaPlayer.create(this, R.raw.beep);
        screenFlashOutputMors = (TextView) findViewById(R.id.convertet_morse_edit_view_output_screen);
        screenEditTextInput = (EditText) findViewById(R.id.convert_morse_edit_text_input_screen);
        soundEnableButton = (ImageButton) findViewById(R.id.soundEnableButtonId);

        Spinner screenSpinner;
        ArrayAdapter myAdapterForSpeedSpinner;
        myAdapterForSpeedSpinner = ArrayAdapter.createFromResource(this, R.array.spinner_screen_speeds,android.R.layout.simple_spinner_dropdown_item);
        screenSpinner = (Spinner) findViewById(R.id.spinnerScreen);
        screenSpinner.setAdapter(myAdapterForSpeedSpinner);
        screenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                screenSPEED = Integer.parseInt(String.valueOf(parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                screenSPEED = 8;
            }
        });
        screenSpinner.setSelection(7);
    }


    public void makeSoundScreenEnable(View view) {
        if(soundEnableButton.getTag().equals("enable")){
            soundEnableButton.setTag("disable");
            soundEnableButton.setImageResource(R.drawable.sound_off);
        }
        else{
            soundEnableButton.setTag("enable");
            soundEnableButton.setImageResource(R.drawable.sound_on);
        }
    }
    public void convertToScreenButton(View view) {
        // if NOT WORKING AT THE TIME TIME (if it's not empty, and button NAME IS CONVERT ((NOT STOP)))
        if (!String.valueOf(screenEditTextInput.getText()).equals("") && String.valueOf(myConvertScreenButton.getText()).equals(getResources().getString(R.string.convert))) {
            //... set it name now to STOP
            myConvertScreenButton.setText(R.string.stop);
            String myTextToConvert = String.valueOf(screenEditTextInput.getText());
            currentConverterText = MainActivity.convertToMorse(myTextToConvert);

            currentStringIndexMorsShowing = 0;
            currentColor = Color.BLACK;

            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // if BUTTONS has text STOP then means CONVERTING IS RUNNING
                    if (String.valueOf(myConvertScreenButton.getText()).equals(getResources().getString(R.string.stop))) {
                        // when SCREEN converting button was clicked
                        int theDelayTime = 0;
                        if (currentStringIndexMorsShowing < currentConverterText.length()) {
                            //  if SCREEN converting is ON, then show BLACK SCREEN, or if we're in SPACE of between words space, then also show BLACK SCREEN !
                            if (currentColor == Color.YELLOW || currentConverterText.charAt(currentStringIndexMorsShowing) == ' ' || currentConverterText.charAt(currentStringIndexMorsShowing) == '/') {
                                // pause BEEP
                                if (beepSound.isPlaying() && soundEnableButton.getTag().equals("enable"))
                                    beepSound.pause();
                                currentColor = Color.BLACK;
                                screenFlashOutputMors.setBackgroundColor(currentColor);
                                // when want to show SPACE
                                if (currentConverterText.charAt(currentStringIndexMorsShowing) == '/') {
                                    theDelayTime = MainActivity.betweenWordsDelay;
                                    currentStringIndexMorsShowing++;
                                }
                                // when between letters PAUSE
                                else if (currentConverterText.charAt(currentStringIndexMorsShowing) == ' ') {
                                    theDelayTime = MainActivity.betweenLettersDelay;
                                    currentStringIndexMorsShowing++;
                                    // when no space
                                } else
                                    theDelayTime = MainActivity.betweenLineDotDelay;
                            }
                            // if SCREEN converting if OFF then we want to send SIGNAL
                            else {
                                // if button sound is ENABLE then make that sound !!!
                                if (soundEnableButton.getTag().equals("enable")) {
                                    if (!beepSound.isPlaying()) {
                                        beepSound.start();
                                    }
                                    if (!beepSound.isLooping())
                                        beepSound.setLooping(true);
                                }
                                currentColor = Color.YELLOW;
                                screenFlashOutputMors.setBackgroundColor(currentColor);
                                // if we're in progress SENDING SIGNAL
                                if (currentStringIndexMorsShowing < currentConverterText.length()) {
                                    switch (currentConverterText.charAt(currentStringIndexMorsShowing)) {
                                        case '.':
                                            screenFlashOutputMors.setBackgroundColor(currentColor);
                                            theDelayTime = MainActivity.dotTiming;
                                            break;
                                        case '-':
                                            screenFlashOutputMors.setBackgroundColor(currentColor);
                                            theDelayTime = MainActivity.lineTiming;
                                            break;
                                    }
                                    ++currentStringIndexMorsShowing;
                                }
                            }
                        }
                        // showing TEXT signal has come to an END
                        else {
                            theDelayTime = 0;
                            screenFlashOutputMors.setBackgroundColor(Color.BLACK);
                            myConvertScreenButton.setText(getResources().getString(R.string.convert));
                            if (beepSound.isPlaying())
                                beepSound.pause();
                        }
                        handler.postDelayed(this, theDelayTime / screenSPEED);
                    }
                }
            };
            handler.post(runnable);
        }
        else if (String.valueOf(screenEditTextInput.getText()).equals("")){
            Toast.makeText(this, R.string.insert_text, Toast.LENGTH_SHORT).show();
        }
        // if USER wants to STOP converting
        else if (String.valueOf(myConvertScreenButton.getText()).equals(getResources().getString(R.string.stop))){
            myConvertScreenButton.setText(getResources().getString(R.string.convert));
            screenFlashOutputMors.setBackgroundColor(Color.BLACK);
            if (beepSound.isPlaying())
                beepSound.pause();
        }
    }

    public void backToMenuButton(View view) {
        myConvertScreenButton.setText(getResources().getString(R.string.convert));
        screenFlashOutputMors.setBackgroundColor(Color.BLACK);
        //... relase playing sound
        beepSound.release();
        /*Intent IntentbackToMenuButton = new Intent(this, MainActivity.class);
        startActivity(IntentbackToMenuButton);*/
        finish();
    }
}

package com.hoymm.muca.damian.morsecodeconverter2;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertToFlashlight extends Activity {


    Button myConvertFlashlightButton;
    private Camera camera;
    private boolean isFlashOn;
    Camera.Parameters params;

    MediaPlayer beepSound;
    ImageButton soundEnableButton;
    private int currentColor, currentStringIndexMorsShowing, flashlightSPEED;
    private String currentConverterText;
    private TextView flashTextViewOutput;
    private EditText flashlightEditTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_to_flashlight);
        initalizateObjects();
        // get the camera
        getCamera();
    }



    //... initializate objects function
    void initalizateObjects(){
        myConvertFlashlightButton = (Button) findViewById(R.id.myConvertFlashlightButton);
        beepSound = MediaPlayer.create(this, R.raw.beep);
        soundEnableButton = (ImageButton) findViewById(R.id.soundEnableFlashlightButtonId);
        flashTextViewOutput = (TextView) findViewById(R.id.showCurrentlyTranslatedTextFlashlight);
        flashlightEditTextInput = (EditText) findViewById(R.id.convert_morse_edit_text_input_flashlight);

        Spinner flashlightSpinner;
        ArrayAdapter myAdapterForSpeedSpinner;
        myAdapterForSpeedSpinner = ArrayAdapter.createFromResource(this, R.array.spinner_flashlight_speeds,android.R.layout.simple_spinner_dropdown_item);
        flashlightSpinner = (Spinner) findViewById(R.id.spinnerFlashlight);
        assert flashlightSpinner != null;
        flashlightSpinner.setAdapter(myAdapterForSpeedSpinner);
        flashlightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flashlightSPEED = Integer.parseInt(String.valueOf(parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                flashlightSPEED = 8;
            }
        });
        flashlightSpinner.setSelection(7);
    }
    //... Check if I can use CAMERA FLASHLIGHT


    //                      ---------------------- CAMERA FUNCTIONS

    // Get the camera
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera failed to Open: ", e.getMessage());
            }
        }
    }
    // Turning On flash
    private void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }
    // Turning Off flash
    private void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }
    // MAIN PROGRAM CONVERTING TO FLASHLIGHT
    public void convertToFlashlightButton(View view) {

        // get the camera
        getCamera();

        if (!String.valueOf(flashlightEditTextInput.getText()).equals("") && String.valueOf(myConvertFlashlightButton.getText()).equals(getResources().getString(R.string.convert))) {
            myConvertFlashlightButton.setText(R.string.stop);
            String myTextToConvert = String.valueOf(flashlightEditTextInput.getText());
            currentConverterText = MainActivity.convertToMorse(myTextToConvert);

            flashTextViewOutput.setText(currentConverterText);
            currentStringIndexMorsShowing = 0;
            currentColor = Color.BLACK;

            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // when FLASHLIGHT converting button was clicked
                    if (String.valueOf(myConvertFlashlightButton.getText()).equals(getResources().getString(R.string.stop))) {
                        int theDelayTime = 0;
                        if (currentStringIndexMorsShowing < currentConverterText.length()) {
                            //  if FLASHLIGHT converting is ON, then show BLACK FLASHLIGHT, or if we're in SPACE of between words space, then also show BLACK FLASHLIGHT !
                            if (currentColor == Color.YELLOW || currentConverterText.charAt(currentStringIndexMorsShowing) == ' ' || currentConverterText.charAt(currentStringIndexMorsShowing) == '/') {
                                if (beepSound.isPlaying())
                                    beepSound.pause();
                                turnOffFlash();
                                currentColor = Color.BLACK;
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
                            // if FLASHLIGHT converting if OFF then we want to send SIGNAL
                            else {
                                // if button sound is ENABLE then make that sound !!!
                                if (soundEnableButton.getTag().equals("enable")) {
                                    if (!beepSound.isPlaying()) {
                                        beepSound.start();
                                    }
                                    if(!beepSound.isLooping())
                                        beepSound.setLooping(true);
                                }
                                currentColor = Color.YELLOW;
                                turnOnFlash();
                                // if we're in progress SENDING SIGNAL
                                if (currentStringIndexMorsShowing < currentConverterText.length()) {
                                    switch (currentConverterText.charAt(currentStringIndexMorsShowing)) {
                                        case '.':
                                            theDelayTime = MainActivity.dotTiming;
                                            break;
                                        case '-':
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
                            turnOffFlash();
                            myConvertFlashlightButton.setText(getResources().getString(R.string.convert));
                            if (beepSound.isPlaying())
                                beepSound.pause();
                        }

                        handler.postDelayed(this, theDelayTime / flashlightSPEED);
                    }
                }
            };
            handler.post(runnable);
        }
        else if (String.valueOf(flashlightEditTextInput.getText()).equals("")){
            Toast.makeText(this, R.string.insert_text, Toast.LENGTH_SHORT).show();
        }
        // if USER wants to STOP converting
        else if (String.valueOf(myConvertFlashlightButton.getText()).equals(getResources().getString(R.string.stop))){

            myConvertFlashlightButton.setText(getResources().getString(R.string.convert));
            turnOffFlash();
            if (beepSound.isPlaying())
                beepSound.pause();
        }
        turnOffFlash();
    }
    // back to MAIN MENU function
    public void backToMenuButton(View view) {
        myConvertFlashlightButton.setText(getResources().getString(R.string.convert));
        turnOffFlash();
        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
        //... relase playing sound
        if (beepSound.isPlaying())
            beepSound.pause();
        beepSound.release();
        //... send RESULT NOTHING
        Intent returnIntent = new Intent();
        /*setResult(Activity.RESULT_CANCELED, returnIntent);
        Intent IntentbackToMenuButton = new Intent(this, MainActivity.class);
        startActivity(IntentbackToMenuButton);*/
        finish();
    }
    // Override ORDINARY METHODS

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // on stop release the camera
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        turnOffFlash();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // on starting the app get the camera params
        getCamera();
    }


    public void makeSoundFlashlightEnable(View view) {
        if(soundEnableButton.getTag().equals("enable")){
            soundEnableButton.setTag("disable");
            soundEnableButton.setImageResource(R.drawable.sound_off);
        }
        else{
            soundEnableButton.setTag("enable");
            soundEnableButton.setImageResource(R.drawable.sound_on);
        }
    }
}

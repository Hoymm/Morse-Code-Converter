package com.hoymm.muca.damian.morsecodeconverter2;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    // PERMISSIONS CONSTANTS
    int MY_PERMISSIONS_REQUEST_CAMERA = 0;

    public static int dotTiming = 500, lineTiming = 2400, betweenLineDotDelay = 800,betweenLettersDelay = 2200, betweenWordsDelay = 1000;
    public static boolean ableToUseFlashlight = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // show HOYMM APPLICATIONS logo on start
        startActivity(new Intent(this, HoymmLogoActivity.class));
    }
    /*

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("com.example.hoymm.morsecodeconverter.dotTiming", dotTiming);
            outState.putInt("com.example.hoymm.morsecodeconverter.lineTiming", lineTiming);
            outState.putInt("com.example.hoymm.morsecodeconverter.betweenLineDotDelay", betweenLineDotDelay);
            outState.putInt("com.example.hoymm.morsecodeconverter.betweenLettersDelay", betweenLettersDelay);
            outState.putInt("com.example.hoymm.morsecodeconverter.currentColor", currentColor);
            outState.putInt("com.example.hoymm.morsecodeconverter.currentStringIndexMorsShowing", currentStringIndexMorsShowing);
            outState.putInt("com.example.hoymm.morsecodeconverter.screenSPEED", screenSPEED);
            outState.putInt("com.example.hoymm.morsecodeconverter.flashlightSPEED", flashlightSPEED);
            outState.putBoolean("com.example.hoymm.morsecodeconverter.ifConvertToScreenButtonClicked", ifConvertToScreenButtonClicked);
            outState.putBoolean("com.example.hoymm.morsecodeconverter.ifConvertToFlashlightButtonClicked", ifConvertToFlashlightButtonClicked);
            outState.putString("com.example.hoymm.morsecodeconverter.currentMenuActive", currentMenuActive);
            outState.putString("com.example.hoymm.morsecodeconverter.currentConverterText", currentConverterText);
            outState.putString("com.example.hoymm.morsecodeconverter.flashTextViewOutput", String.valueOf(flashTextViewOutput.getText()));
            outState.putString("com.example.hoymm.morsecodeconverter.textEditTextOutput", String.valueOf(textEditTextOutput.getText()));
            outState.putString("com.example.hoymm.morsecodeconverter.screenFlashOutputMors", String.valueOf(screenFlashOutputMors.getText()));
            outState.putString("com.example.hoymm.morsecodeconverter.textEditTextInput", String.valueOf(textEditTextInput.getText()));
            outState.putString("com.example.hoymm.morsecodeconverter.flashlightEditTextInput", String.valueOf(flashlightEditTextInput.getText()));
            outState.putString("com.example.hoymm.morsecodeconverter.screenEditTextInput", String.valueOf(screenEditTextInput.getText()));
            //... send TAGS
            outState.putString("com.example.hoymm.morsecodeconverter.screenLinearLayout", String.valueOf(screenLinearLayout.getTag()));
            outState.putString("com.example.hoymm.morsecodeconverter.flashlightLinearLayout", String.valueOf(flashlightLinearLayout.getTag()));
            outState.putString("com.example.hoymm.morsecodeconverter.textLinearLayout", String.valueOf(textLinearLayout.getTag()));
            //... send VISIBILITY
            outState.putBoolean("com.example.hoymm.morsecodeconverter.highestMainMenuTextViewAppName", highestMainMenuTextViewAppName.getVisibility() == View.VISIBLE);
            outState.putBoolean("com.example.hoymm.morsecodeconverter.textLinearLayout", textLinearLayout.getVisibility() == View.VISIBLE);
            outState.putBoolean("com.example.hoymm.morsecodeconverter.flashlightLinearLayout", flashlightLinearLayout.getVisibility() == View.VISIBLE);
            outState.putBoolean("com.example.hoymm.morsecodeconverter.screenLinearLayout", screenLinearLayout.getVisibility() == View.VISIBLE);
            outState.putBoolean("com.example.hoymm.morsecodeconverter.secondMenuText", secondMenuText.getVisibility() == View.VISIBLE);
            outState.putBoolean("com.example.hoymm.morsecodeconverter.secondMenuScreen", secondMenuScreen.getVisibility() == View.VISIBLE);
            outState.putBoolean("com.example.hoymm.morsecodeconverter.secondMenuFlashlight", secondMenuFlashlight.getVisibility() == View.VISIBLE);
        }

        @Override
        protected void onRestoreInstanceState(Bundle savedInstanceState) {
            super.onRestoreInstanceState(savedInstanceState);
            dotTiming = savedInstanceState.getInt("com.example.hoymm.morsecodeconverter.dotTiming");
            lineTiming = savedInstanceState.getInt("com.example.hoymm.morsecodeconverter.lineTiming");
            betweenLineDotDelay = savedInstanceState.getInt("com.example.hoymm.morsecodeconverter.betweenLineDotDelay");
            betweenLettersDelay = savedInstanceState.getInt("com.example.hoymm.morsecodeconverter.betweenLettersDelay");
            currentColor = savedInstanceState.getInt("com.example.hoymm.morsecodeconverter.currentColor");
            currentStringIndexMorsShowing = savedInstanceState.getInt("com.example.hoymm.morsecodeconverter.currentStringIndexMorsShowing");
            screenSPEED = savedInstanceState.getInt("com.example.hoymm.morsecodeconverter.screenSPEED");
            flashlightSPEED = savedInstanceState.getInt("com.example.hoymm.morsecodeconverter.flashlightSPEED");
            ifConvertToScreenButtonClicked = savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.ifConvertToScreenButtonClicked");
            ifConvertToFlashlightButtonClicked = savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.ifConvertToFlashlightButtonClicked");
            currentMenuActive = savedInstanceState.getString("com.example.hoymm.morsecodeconverter.currentMenuActive");
            currentConverterText = savedInstanceState.getString("com.example.hoymm.morsecodeconverter.currentConverterText");
            flashTextViewOutput.setText(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.flashTextViewOutput"));
            textEditTextOutput.setText(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.textEditTextOutput"));
            screenFlashOutputMors.setText(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.screenFlashOutputMors"));
            textEditTextInput.setText(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.textEditTextInput"));
            flashlightEditTextInput.setText(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.flashlightEditTextInput"));
            screenEditTextInput.setText(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.screenEditTextInput"));
            //... GET TAGS
            screenLinearLayout.setTag(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.screenLinearLayout"));
            textLinearLayout.setTag(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.textLinearLayout"));
            flashlightLinearLayout.setTag(savedInstanceState.getString("com.example.hoymm.morsecodeconverter.flashlightLinearLayout"));
            //... GET VISIBILITIES
            highestMainMenuTextViewAppName.setVisibility(savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.highestMainMenuTextViewAppName") ? View.VISIBLE : View.GONE);
            textLinearLayout.setVisibility(savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.textLinearLayout") ? View.VISIBLE : View.GONE);
            flashlightLinearLayout.setVisibility(savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.flashlightLinearLayout") ? View.VISIBLE : View.GONE);
            screenLinearLayout.setVisibility(savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.screenLinearLayout") ? View.VISIBLE : View.GONE);
            secondMenuText.setVisibility(savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.secondMenuText") ? View.VISIBLE : View.GONE);
            secondMenuScreen.setVisibility(savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.secondMenuScreen") ? View.VISIBLE : View.GONE);
            secondMenuFlashlight.setVisibility(savedInstanceState.getBoolean("com.example.hoymm.morsecodeconverter.secondMenuFlashlight") ? View.VISIBLE : View.GONE);
        }
    */
    public void textConvertClicked(View view) {
        Intent myIntentToOpenTextConvertMenu = new Intent(this, ConvertToText.class);
        startActivity(myIntentToOpenTextConvertMenu);
    }
    public void flashlightConvertClicked(View view) {
        ableToUseFlashlight = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (ableToUseFlashlight) {
            // OK, open new activity
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent myIntentToOpenTextConvertMenu = new Intent(this, ConvertToFlashlight.class);
                startActivity(myIntentToOpenTextConvertMenu);
            }
            // no CAMERA PERMISSION
            else{
                getPermissionsCamera();
            }
        }
        // no FLASHLIGHT
        else{
            Toast.makeText(this, getResources().getString(R.string.i_cannot_use_flashlight), Toast.LENGTH_LONG).show();
        }
    }
    //... onActivityResult if I cannout use FLASHLIGHT

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
            Toast.makeText(this,data.getStringExtra("result"),Toast.LENGTH_SHORT).show();
    }

    public void screenConvertClicked(View view) {
        Intent myIntentToOpenTextConvertMenu = new Intent(this, ConvertToScreen.class);
        startActivity(myIntentToOpenTextConvertMenu);
    }

    // ... convert STRING to MORSE CODE
    public static String convertToMorse(String textToConvert){
        // make whole string UPPERCASE STRING
        textToConvert = textToConvert.toUpperCase();
        // convert polish LETTERS to non-polish
        textToConvert = textToConvert.replace("Ą","A");
        textToConvert = textToConvert.replace("Ć","C");
        textToConvert = textToConvert.replace("Ę","E");
        textToConvert = textToConvert.replace("Ł","L");
        textToConvert = textToConvert.replace("Ń","N");
        textToConvert = textToConvert.replace("Ó","O");
        textToConvert = textToConvert.replace("Ś","S");
        textToConvert = textToConvert.replace("Ź","Z");
        textToConvert = textToConvert.replace("Ż","Z");
        // letters
        String [][] morseCoding = new String[][]{
                {" ","/"},
                {".",".-.-.-"},
                {"A",".-"},
                {"B","-..."},
                {"C","-.-."},
                {"D","-.."},
                {"E","."},
                {"F","..-."},
                {"G","--."},
                {"H","...."},
                {"I",".."},
                {"J",".---"},
                {"K","-.-"},
                {"L",".-.."},
                {"M","--"},
                {"N","-."},
                {"O","---"},
                {"P",".--."},
                {"Q","--.-"},
                {"R",".-."},
                {"S","..."},
                {"T","-"},
                {"U","..-"},
                {"V","...-"},
                {"W",".--"},
                {"X","-..-"},
                {"Y","-.--"},
                {"Z","--.."},
                {"0","-----"},
                {"1",".----"},
                {"2","..---"},
                {"3","...--"},
                {"4","....-"},
                {"5","....."},
                {"6","-...."},
                {"7","--..."},
                {"8","---.."},
                {"9","----."},
                {",","--..--"},
                {":","---..."},
                {"?","..--.."},
                {"'",".----."},
                {"(","-.--.-"},
                {")","-.--.-"},
                {"\"",".-..-."},
                {"@",".--.-."},
                {"=","-...-"},
                {"\n",".-.-"}
        };
        // we must delete from textToConvert all CHARACTERS that are not possible to convert to MORSE
        String tempText_unnecessarySignsToDelete = textToConvert;
        // we insert to tempText_unnecessarySignsToDelete all letters from INPUT that cannot be converted
        for(int i = 0;i < morseCoding.length; ++i){
            tempText_unnecessarySignsToDelete = tempText_unnecessarySignsToDelete.replace(morseCoding[i][0], "");
        }
        //...
        System.out.println(tempText_unnecessarySignsToDelete);
        for (int i = 0; i < tempText_unnecessarySignsToDelete.length();++i){
            textToConvert = textToConvert.replace(tempText_unnecessarySignsToDelete.substring(i,i+1),"");
        }
        // here we have our INPUT without wrong characters
        String textConverted = textToConvert;
        for (int i = 0 ; i < morseCoding.length; ++i){
            textConverted = textConverted.replace(morseCoding[i][0],morseCoding[i][1]+" ");
        }
        // return MORSE without THE LAST SPACE
        return textConverted.substring(0, textConverted.length());
    }



    // get NEEDED PERMISSIONS WHEN APP STARTS
    private boolean getPermissionsCamera() {
        // Here, thisActivity is the current activity -- CAMERA PERMISSION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                // Camera Permission Explanation
                Toast.makeText(this, R.string.camera_permission_explanation, Toast.LENGTH_LONG).show();

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
            return false;
        }
        else
            return true;
    }

    /// ============================================================================== OVERRIDE METHODS FUNCTIONALITY OF APP ====================================

}

package com.hoymm.muca.damian.morsecodeconverter2;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertToText extends Activity {

    private TextView textEditTextOutput;
    private EditText textEditTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_to_text);
        initalizateObjects();
    }
    //... initializate objects function
    void initalizateObjects(){
        textEditTextInput = (EditText) findViewById(R.id.convert_morse_edit_text_input_text);
        textEditTextOutput = (TextView) findViewById(R.id.convertet_morse_edit_view_output_text);
    }


    public void backToMenuButton(View view) {
        /*Intent IntentbackToMenuButton = new Intent(this, MainActivity.class);
        startActivity(IntentbackToMenuButton);*/
        finish();
    }
    // converting button in Convert to text menu
    public void convertToTextButton(View view) {
        String currentConverterText;
        if (!String.valueOf(textEditTextInput.getText()).equals("")) {
            String myTextToConvert = String.valueOf(textEditTextInput.getText());
            currentConverterText = MainActivity.convertToMorse(myTextToConvert);
            textEditTextOutput.setText(currentConverterText);
        }
        else{
            Toast.makeText(this, R.string.insert_text, Toast.LENGTH_LONG).show();
        }
    }

}

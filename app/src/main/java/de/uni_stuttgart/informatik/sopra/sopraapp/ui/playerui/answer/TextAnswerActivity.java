package de.uni_stuttgart.informatik.sopra.sopraapp.ui.playerui.answer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;

public class TextAnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_answer);
        EditText editText = findViewById(R.id.edittext);

        Button submitbutton = findViewById(R.id.submitbutton);

        ImageButton backbutton = findViewById(R.id.button_back);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("text", editText.getText().toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });


    }
}

package de.uni_stuttgart.informatik.sopra.sopraapp.ui.adminui.group;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import de.uni_stuttgart.informatik.sopra.sopraapp.R;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.entitys.User;
import de.uni_stuttgart.informatik.sopra.sopraapp.db.repositories.UserRepository;

/**
 * Activity for Editing A Group
 *
 * @author Dominik Dec
 */
public class EditGroupActivity extends AppCompatActivity {
    EditText nameedittext, pwedittext;
    private UserRepository repository;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        repository = new UserRepository(this);
        String msg = getIntent().getStringExtra("name");
        String pw = getIntent().getStringExtra("pw");
        id = getIntent().getStringExtra("id");

        nameedittext = findViewById(R.id.editgroup_name);
        pwedittext = findViewById(R.id.editgroup_pw);

        nameedittext.setText(msg);
        pwedittext.setText(pw);

        //backbutton
        ImageButton backbutton = findViewById(R.id.button_close);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public void onClickSave(View v) {
        User user = repository.getById(id).get();
        user.setName(nameedittext.getText().toString());
        user.setPassword(pwedittext.getText().toString());
        repository.update(user);
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onClickDelete(View v) {
        repository.delete(repository.getById(id).get());
        Toast.makeText(this, "Deleted", Toast.LENGTH_LONG).show();
        finish();
    }
}

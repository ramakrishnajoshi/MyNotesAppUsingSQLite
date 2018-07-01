package compassites.com.mynotesappusingsqlite;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

public class CreatePasswordActivity extends AppCompatActivity {

    EditText createPasswordCharacter1, createPasswordCharacter2,createPasswordCharacter3,createPasswordCharacter4;
    String enteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity_password);

        Stetho.initializeWithDefaults(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        createPasswordCharacter1 = findViewById(R.id.password_Character_1);
        createPasswordCharacter2 = findViewById(R.id.password_Character_2);
        createPasswordCharacter3 = findViewById(R.id.password_Character_3);
        createPasswordCharacter4 = findViewById(R.id.password_Character_4);

        createPasswordCharacter1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    createPasswordCharacter1.requestFocus();
                 //this is used the handle the following situation
                /* user types 3 and is moved to next EditText but user tries to change
                 * first character i.e 3 so he deletes 3 but as soon as he deletes 3
                  * he is taken to next character*/
                else
                createPasswordCharacter2.requestFocus();
                Toast.makeText(CreatePasswordActivity.this, "onText", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    createPasswordCharacter1.requestFocus();
                else
                createPasswordCharacter2.requestFocus();
                Toast.makeText(CreatePasswordActivity.this, "afterText", Toast.LENGTH_SHORT).show();
            }
        });

        createPasswordCharacter2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    createPasswordCharacter1.requestFocus();
                else
                createPasswordCharacter3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    createPasswordCharacter1.requestFocus();
                else
                createPasswordCharacter3.requestFocus();
            }
        });

        createPasswordCharacter3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    createPasswordCharacter2.requestFocus();
                else
                    createPasswordCharacter4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    createPasswordCharacter2.requestFocus();
                else
                createPasswordCharacter4.requestFocus();
            }
        });

        createPasswordCharacter4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() == 0)
                    createPasswordCharacter3.requestFocus();
                else {
                    enteredPassword = null;
                    enteredPassword = createPasswordCharacter1.getText().toString() +
                            createPasswordCharacter2.getText().toString() +
                            createPasswordCharacter3.getText().toString() +
                            createPasswordCharacter4.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    createPasswordCharacter3.requestFocus();
                else {
                    enteredPassword = null;
                    enteredPassword = createPasswordCharacter1.getText().toString() +
                            createPasswordCharacter2.getText().toString() +
                            createPasswordCharacter3.getText().toString() +
                            createPasswordCharacter4.getText().toString();
                }
            }
        });

     //   final EditText createPassword = findViewById(R.id.create_password_edit_text);

        final DataBaseHelper dataBaseHelper = DataBaseHelper.getDatabaseHelperInstance(this);
        if (dataBaseHelper.getPassword() == -9)
            ; //create new password
        else
            startActivity(new Intent(this,PasswordActivity.class));


        findViewById(R.id.create_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enteredPassword != null) {
                    // DataBaseHelper dataBaseHelper = new DataBaseHelper(CreatePasswordActivity.this);
                    dataBaseHelper.setPassword(enteredPassword);

                    Intent intent = new Intent(CreatePasswordActivity.this, PasswordActivity.class);





                    /*findViewById(R.id.create_password_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (createPassword.getText().toString() != null) {
                   // DataBaseHelper dataBaseHelper = new DataBaseHelper(CreatePasswordActivity.this);
                    dataBaseHelper.setPassword(createPassword.getText().toString());

                    Intent intent = new Intent(CreatePasswordActivity.this, PasswordActivity.class);
                */    /**
                     * If Intent.FLAG_ACTIVITY_CLEAR_TASK set in an Intent passed to {@link Context#startActivity Context.startActivity()},
                     * this flag will cause any existing task that would be associated with the
                     * activity to be cleared before the activity is started.  That is, the activity
                     * becomes the new root of an otherwise empty task, and any old activities
                     * are finished.  This can only be used in conjunction with {@link #FLAG_ACTIVITY_NEW_TASK}.
                     */
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    finishAndRemoveTask();

                 //   intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);


                   /* intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();*/

                    startActivity(intent);
                }
                else
                    Toast.makeText(CreatePasswordActivity.this,"Empty password",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

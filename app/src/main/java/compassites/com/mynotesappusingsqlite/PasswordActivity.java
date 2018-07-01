package compassites.com.mynotesappusingsqlite;

import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    EditText createPasswordCharacter1, createPasswordCharacter2,createPasswordCharacter3,createPasswordCharacter4;
    String enteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

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
                Toast.makeText(PasswordActivity.this, "onText", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0)
                    createPasswordCharacter1.requestFocus();
                else
                    createPasswordCharacter2.requestFocus();
                Toast.makeText(PasswordActivity.this, "afterText", Toast.LENGTH_SHORT).show();


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

        findViewById(R.id.password_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DataBaseHelper dataBaseHelper = new DataBaseHelper(PasswordActivity.this);
                DataBaseHelper dataBaseHelper = DataBaseHelper.getDatabaseHelperInstance(PasswordActivity.this);
                if (enteredPassword != null) {
                  if(Integer.toString(dataBaseHelper.getPassword()).equals(enteredPassword)){
                      Intent intent = new Intent(PasswordActivity.this, MainActivity.class);
                    //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                      finishAndRemoveTask();
                   //   intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                      /*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                      intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                      startActivity(intent);
                      finish();*/

                    startActivity(intent);

                  }else{
                      Toast.makeText(PasswordActivity.this, "Wrong Password...Try Again",Toast.LENGTH_SHORT).show();
                  }

                }
                else{
                    Toast.makeText(PasswordActivity.this, "Please enter password to proceed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

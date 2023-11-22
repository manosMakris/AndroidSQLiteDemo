package gr.hua.dit.it2021052.sqlite_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText fNameETInsert;
    private EditText lNameETInsert;
    private EditText fNameETDelete;
    private EditText lNameETDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fNameETInsert = findViewById(R.id.fNameETInsert);
        lNameETInsert = findViewById(R.id.lNameETInsert);
        fNameETDelete = findViewById(R.id.fNameETDelete);
        lNameETDelete = findViewById(R.id.lNameETDelete);

        SqliteDBHelper sqliteDBHelper = new SqliteDBHelper(getApplicationContext());
        findViewById(R.id.insertButton).setOnClickListener(view -> {
            String firstName = fNameETInsert.getText().toString();
            String lastName = lNameETInsert.getText().toString();

            UserModel userToInsert = new UserModel(firstName, lastName);

            sqliteDBHelper.insertUser(userToInsert);
        });

        findViewById(R.id.retriveButton).setOnClickListener(view -> {
            List<UserModel> users = sqliteDBHelper.getAllUsers();

            TextView outputTextView = (TextView)findViewById(R.id.outputTV);
            String text = String.format("Result: %s", users.toString());
            outputTextView.setText(text);
        });

        findViewById(R.id.deleteButton).setOnClickListener(view -> {
            String firstName = fNameETDelete.getText().toString();
            String lastName = lNameETDelete.getText().toString();

            UserModel userToDelete = new UserModel(firstName, lastName);
            sqliteDBHelper.deleteUser(userToDelete);
        });

        Toast.makeText(this, "This is a toast", Toast.LENGTH_LONG).show();
    }
}
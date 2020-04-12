package ro.pub.cs.systems.eim.Colocviul1_245;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Colocviu1_245MainActivity extends AppCompatActivity {

    private Button addButton;
    private Button computeButton;
    private EditText nextTerm;
    private EditText allTerms;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            switch(view.getId()) {
                case R.id.add_button:
                    if (!nextTerm.getText().toString().equals("")) {
                        int term = Integer.valueOf((nextTerm.getText().toString()));
                        if (allTerms.getText().toString().equals("")) {
                            allTerms.setText(String.valueOf(term));
                        } else {
                            allTerms.setText(allTerms.getText().toString() + "+" + String.valueOf(term));
                        }
                        nextTerm.setText("");
                    }
                    break;
                case R.id.compute_button:
                    if (!allTerms.getText().toString().equals("")) {
                        Intent intent = new Intent(getApplicationContext(), Colocviu1_245SecondaryActivity.class);
                        intent.putExtra(Constants.INTENT_KEY, allTerms.getText().toString());
                        startActivityForResult(intent, Constants.REQUEST_CODE);

                    } else {
                        Toast.makeText(getApplication(), getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_245_main);


        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(buttonClickListener);
        computeButton = findViewById(R.id.compute_button);
        computeButton.setOnClickListener(buttonClickListener);
        nextTerm = findViewById(R.id.edit_text);
        allTerms = findViewById(R.id.text_view);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.SUM)) {
                allTerms.setText(savedInstanceState.getString(Constants.SUM));
            } else {
                allTerms.setText(String.valueOf(0));
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK) {
            allTerms.setText(intent.getStringExtra(Constants.SUM_KEY));
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
            if (Integer.parseInt(allTerms.getText().toString()) > 10) {
                Intent serviceIntent = new Intent(getApplicationContext(), Colocviu1_245Service.class);
                serviceIntent.putExtra(Constants.ServiceSUM, allTerms.getText().toString());
                getApplicationContext().startService(intent);
            }
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle savedInstaceState) {
        super.onSaveInstanceState(savedInstaceState);
        savedInstaceState.putString(Constants.SUM, allTerms.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstance) {
        if (savedInstance.containsKey(Constants.SUM)) {
            allTerms.setText(savedInstance.getString(Constants.SUM));
        } else {
            allTerms.setText(String.valueOf(0));
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_245Service.class);
        stopService(intent);
        super.onDestroy();
    }
}

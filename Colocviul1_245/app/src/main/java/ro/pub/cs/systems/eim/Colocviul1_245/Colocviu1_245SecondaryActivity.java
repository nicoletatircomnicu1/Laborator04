package ro.pub.cs.systems.eim.Colocviul1_245;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Colocviu1_245SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String sum = intent.getStringExtra(Constants.INTENT_KEY);
            if (sum != null) {
                String[] terms = sum.split("\\+");
                int s = 0;
                for (String t : terms) {
                    s = s + Integer.parseInt(t);
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra(Constants.SUM_KEY, String.valueOf(s));
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

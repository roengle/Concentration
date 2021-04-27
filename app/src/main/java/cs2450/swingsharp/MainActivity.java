package cs2450.swingsharp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinner code
        String[] arraySpinner = new String[] {
                "2 Cards", "4 Cards", "6 Cards", "8 Cards", "10 Cards",
                "12 Cards", "14 Cards", "16 Cards", "18 Cards", "20 Cards"};
        Spinner s = (Spinner) findViewById(R.id.difficultySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

    }
}
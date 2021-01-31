package com.example.jo.playground;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.jo.playground.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<String> defs = new ArrayList<>();
        defs.add("hello");
        defs.add("world");
        final ArrayAdapter<String> defsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, defs);
        ListView codeLearnLessons = (ListView)findViewById(R.id.definitions);
        codeLearnLessons.setAdapter(defsAdapter);

        List<String> lines = new ArrayList<>();
        final Map<Character, List<String>> dict = new HashMap<>(2997);
//        final Map<Character, String> dict = new HashMap<>(2997);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("defs.txt")));
            String line = br.readLine();
            while (line != null) {
                Character key = line.charAt(0);
                if (!dict.containsKey(key)) {
                    dict.put(key, new ArrayList<String>());
//                    dict.put(key, "");
//                } else {
//                    dict.put(key, dict.get(key) + "\n");
                }
                dict.get(key).add(line);
//                dict.put(key, dict.get(key) + line);
                line = br.readLine();
            }
        } catch (IOException e) {
           Log.d("MainActivity", "dict creation error" + e.toString());
        }
        // simp trad jyutping examples explanation


        EditText et = (EditText)findViewById(R.id.edit_message);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                defsAdapter.clear();
                for (Character key : s.toString().toCharArray()) {
                    if (dict.containsKey(key)) {
                        for (String str : dict.get(key)) {
                            defsAdapter.add(str);
                        }
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

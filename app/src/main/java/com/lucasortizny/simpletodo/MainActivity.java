package com.lucasortizny.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    List<String> items;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    itemsAdapter ia;
    Button buttonclear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd); //Convert Add Button to Java Object
        etItem = findViewById(R.id.etItem); //Convert TextView to Java Object
        rvItems = findViewById(R.id.rvItems); //Convert RecyclerView as Java Obj
        buttonclear = findViewById(R.id.btnClr); //This is the button to clear it all completely.
        loadItems();



        etItem.setText("I am doing this from java");
        itemsAdapter.OnLongClickListener onLongClickListener = new itemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                Toast.makeText(getApplicationContext(), String.format("Item '%s' was removed", items.get(position)), Toast.LENGTH_SHORT).show();
                items.remove(position);

                //Must notify Adapter
                ia.notifyItemRemoved(position);
                saveItems();

            }
        };
        ia = new itemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(ia);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = etItem.getText().toString();
                items.add(text);
                ia.notifyItemInserted(items.size() - 1); //This sends notification to our adapter

                etItem.setText(""); //clear item
                //Toast to reply back the part of the list that was added.
                Toast.makeText(getApplicationContext(), String.format("Item '%s' was added", items.get(items.size()-1)), Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

        buttonclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentsize = items.size();

                for (int i = currentsize-1; i > -1; i--){
                    items.remove(i);
                    ia.notifyItemRemoved(i);
                }

                Toast.makeText(getApplicationContext(), String.format("All %d items removed", currentsize), Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });



    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");

    }
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Unable to read file data correctly");
            items = new ArrayList<>();
        }
        Toast.makeText(getApplicationContext(), String.format("Total number of items retrieved: %d", items.size()), Toast.LENGTH_LONG).show();

    }
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "error saving items to local storage");
        }
    }

}
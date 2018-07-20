package com.example.vivianbabiryekulumba.poe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MyWorkActivity extends AppCompatActivity {

//    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_work);
//        recyclerView = findViewById(R.id.my_work_recycler);

        ///Data must come from data base set up save draft button in
        //Theme Practice Activity.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.inspiration) {
            Intent intent = new Intent(MyWorkActivity.this, PoemActivity.class);
            startActivity(intent);
        }

        if(id == R.id.my_work){
            Intent intent = new Intent(MyWorkActivity.this, MyWorkActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}

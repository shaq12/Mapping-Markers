package com.example.a1sters06.mapping;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by 1sters06 on 09/02/2017.
 */
public class SetLocation extends Activity
{
    public boolean onOptionsItemSelected(MenuItem item)
{
    if(item.getItemId() == R.id.choosemap)
    {
        Intent intent = new Intent(this,HelloMap.class);
        startActivity(intent);
        return true;
    }
    return false;
}
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_hello_map, menu);
        return true;
    }

}

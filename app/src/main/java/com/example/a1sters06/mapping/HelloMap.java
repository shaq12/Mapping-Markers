package com.example.a1sters06.mapping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HelloMap extends Activity
{
    String dir_path = Environment.getExternalStorageDirectory().getAbsolutePath();
    EditText et;
    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;




    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // this line tells OpenStreetMap about our app.
        // If you miss this out, you might get banned from OSM servers
        Configuration.getInstance().load
                (this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);
        mv = (MapView)findViewById(R.id.map1);

        mv.setBuiltInZoomControls(false);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(50.9, -1.4));


        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>()
        {
            public boolean onItemLongPress(int i, OverlayItem item)
            {
                Toast.makeText(HelloMap.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
            public boolean onItemSingleTapUp(int i, OverlayItem item)
            {
                Toast.makeText(HelloMap.this, item.getSnippet(), Toast.LENGTH_LONG).show();
                return true;
            }
        };

        items = new ItemizedIconOverlay<OverlayItem>(this,new ArrayList<OverlayItem>(),null);
        OverlayItem bestkebab = new OverlayItem("WestQuay", "shopping centre in southampton",new GeoPoint(50.9037, -1.4067));
        OverlayItem holyinnocents = new OverlayItem ("Asda", "supermarket in southampton", new GeoPoint(50.9059, -1.4074));
        items.addItem(bestkebab);
        items.addItem(holyinnocents);
        mv.getOverlays().add(items);

        ArrayList<Overlay> overlay = new ArrayList<Overlay>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath()+
                    "/poi.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] components = line.split(",");
                if (components.length == 5) {
                    OverlayItem marker = new OverlayItem(components[0], components[2], new GeoPoint(Double.parseDouble(components[4]),
                            Double.parseDouble(components[3])));
                    items.addItem(marker);
                }
            }
        }
        catch(IOException e)
        {
            new AlertDialog.Builder(this).setMessage(e.toString()).setPositiveButton("OK", null).show();
        }



    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.choosemap)
        {
            Intent intent = new Intent(this,HelloMap.class);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.menu_save) {

            try {
                PrintWriter pw =
                        new PrintWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/save.txt"));

                for (int i = 0; i < items.size(); i++) {
                    OverlayItem marker = items.getItem(i);
                    String saveString = marker.getTitle() + "," + marker.getSnippet() + "," + marker.getPoint();

                    pw.write(saveString);
                }
                pw.flush();
                pw.close();
                return true;


            } catch (IOException e) {
                System.out.println("Error! " + e.getMessage());
            }

            return true;
        }

                return false;
    }


}









package com.example.a1sters06.mapping;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by 1sters06 on 09/02/2017.
 */
public class MapChooseActivity extends Activity implements View.OnClickListener
{
    public void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView(R.layout.mca);

        Button regular = (Button) findViewById(R.id.btnRegular);
        regular.setOnClickListener (this);
        Button cyclemap = (Button)findViewById (R.id.btnCyclemap);
        cyclemap.setOnClickListener(this);

    }

    public void onClick (View view)
    {
        boolean cyclemap = false;
        if (view.getId() == R.id.btnCyclemap)
        {
            cyclemap = true;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean ("Cyclemap", cyclemap);
        intent.putExtras (bundle);
        setResult (RESULT_OK, intent);
        finish();
    }
}

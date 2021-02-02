package dsna19.particle036;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Main036 extends AppCompatActivity
        implements RanGenFragment.OnFragmentInteractionListener,
        MinPQFragment.OnFragmentInteractionListener,
        Particle036Fragment.OnFragmentInteractionListener {
    RanGenFragment mRandGen;
    MinPQFragment mMinPQ;
    Particle036Fragment mParticle;
    MinPQ<Double> mDouble;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main036, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.random:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, mRandGen).commit();
                break;
            case R.id.min_pq:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, mMinPQ).commit();
                break;
            case R.id.particle:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, mParticle).commit();
                break;
            case R.id.toggle:
                mParticle.toggleAnimate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main036);
        RanGenFragment randGen = new RanGenFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, randGen).commit();
    }
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main036);
        mRandGen = new RanGenFragment();
        mMinPQ = new MinPQFragment();
        mParticle = new Particle036Fragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, mParticle).commit();
        mDouble = new MinPQ<Double>();
    }
    */
    @Override
    public void onFragmentInteraction(Uri uri){}

    public void onGenerateClick(View view) {
        EditText startText = (EditText) findViewById(R.id.start);
        EditText endText   = (EditText) findViewById(R.id.end);
        String   startString = startText.getText().toString().trim();
        String   endString   = endText.getText().toString().trim();

        if (!startString.isEmpty()){
            if (!endString.isEmpty()){
                double start = Double.valueOf(startString);
                double end = Double.valueOf(endString);
                String number = Double.toString(RandGen.uniform(start, end));
                TextView messages = (TextView)findViewById(R.id.message1);
                messages.append(number+"\n");
            } else{
                int end = Integer.valueOf(startString);
                String number = Integer.toString(RandGen.uniform(end));
                TextView messages = (TextView)findViewById(R.id.message1);
                messages.append(number+"\n");
            }
        }
    }

    public void onDeleteClick(View view) {
        EditText entryText = (EditText) findViewById(R.id.entry);
        String entryString = entryText.getText().toString().trim();
        entryText.setText("");
        if (!entryString.isEmpty()){
            double entry = Double.valueOf(entryString);
            mDouble.insert(entry);
        }
    }

    public void onInsertClick(View view) {
        if (!mDouble.isEmpty()){
            String entry = Double.toString(mDouble.delMin());
            TextView messages = (TextView) findViewById(R.id.message2);
            messages.append(entry + "\n");
        }
    }
}

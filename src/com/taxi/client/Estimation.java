package com.taxi.client;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
 
public class Estimation extends Activity {
 /**
  * Gere et affiche une liste d estimation de cout et de distance relative à la course
  * @author Clement Bizeau & Yves Szymezak & Matthieu Bertrand
  */
	private ListView ListView ;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estimation);

        ListView = (ListView) findViewById(R.id.listview);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        map = new HashMap<String, String>();
        map.put("titre", "Temps d'arrivée du taxi estimé");
        map.put("description", "00h19min50sec");
        map.put("img", String.valueOf(R.drawable.taxi));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "Temps d'arrivée à destination");
        map.put("description", "25h01min02sec");
        map.put("img", String.valueOf(R.drawable.arrivee));
        listItem.add(map);
 
        map = new HashMap<String, String>();
        map.put("titre", "Estimation du prix de la course");
        map.put("description", "12,05€");
        map.put("img", String.valueOf(R.drawable.euro));
        listItem.add(map);
 
        SimpleAdapter mSchedule = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.item, 
        		new String[] {"img", "titre", "description"}, new int[] {R.id.img, R.id.titre, R.id.description});

        ListView.setAdapter(mSchedule);
        
        
    }}
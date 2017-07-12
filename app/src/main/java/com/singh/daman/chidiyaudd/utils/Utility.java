package com.singh.daman.chidiyaudd.utils;

import android.content.Context;

import com.singh.daman.chidiyaudd.model.Creature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by Daman on 7/10/2017.
 */

public class Utility {

    private Creature creature;
    private Context context;

    public Utility(Context context, Creature creature) {
        this.creature = creature;
        this.context = context;
    }

    public void getRandomData(JSONObject jsonObject) {
        Random r = new Random();
        int random = r.nextInt(7 + 1);
        try {
            JSONArray jarray = jsonObject.getJSONArray("items");
            JSONObject jb = (JSONObject) jarray.get(random);
            creature.setName(jb.getString("name"));
            creature.setFlag(jb.getInt("flag"));
            creature.setImage(jb.getString("image"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

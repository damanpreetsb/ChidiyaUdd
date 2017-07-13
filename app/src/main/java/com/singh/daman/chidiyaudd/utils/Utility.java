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
        int random = r.nextInt(8);
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

    public static final String getRandomDare() {
        Random r = new Random();
        int random = r.nextInt(8);
        if (random == 0) {
            return "Do a headstand";
        } else if (random == 1) {
            return "Treat Your Opponent(s) with a chocolate";
        } else if (random == 2) {
            return "Talk Like a baby for next 2 rounds";
        } else if (random == 3) {
            return "Sing a song of opponents' Choice";
        } else if (random == 4) {
            return "Balance a spoon on your Nose for 20 seconds";
        } else if (random == 5) {
            return "Using only your elbows, make a Facebook status and post it.";
        } else if (random == 6) {
            return "The Person Sitting Right Next to you, is your Profile Picture for 1 day.";
        } else if (random == 7) {
            return "Ice cream Treat";
        } else if (random == 8) {
            return "Share a secret";
        } else if (random == 9) {
            return "Talk continuously for 2 minutes.";
        } else if (random == 10) {
            return "Make every person in the group smile, keep going until everyone has cracked a smile.";
        } else if (random == 11) {
            return "Dance without music for a minute.";
        } else if (random == 12) {
            return "Remain silent till the game ends.";
        } else if (random == 13) {
            return "Do 5 sit-ups";
        } else
            return "Recite Tongue twister as given by any opponent.";
    }
}

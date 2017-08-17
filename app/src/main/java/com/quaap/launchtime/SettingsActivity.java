package com.quaap.launchtime;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import android.preference.PreferenceManager;
import android.view.KeyEvent;

import com.quaap.launchtime.components.IconsHandler;

import java.util.Collection;
import java.util.Map;

/**
 * Copyright (C) 2017   Tom Kliethermes
 *
 * This file is part of LaunchTime and is is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 *  Some portions derived from KISS.
 *  https://github.com/Neamar/KISS
 *
 */


public class SettingsActivity extends PreferenceActivity {


    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        getListView().setBackgroundColor(Color.DKGRAY);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        if (fragmentName.startsWith("com.quaap.launchtime.SettingsActivity$")) {
            return true;
        }
        return super.isValidFragment(fragmentName);
    }




    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);



        }

        @Override
        public void onResume() {
            super.onResume();
            ListPreference iconsPack = (ListPreference) findPreference("icons-pack");
            setListPreferenceIconsPacksData(iconsPack, this.getActivity());
        }
    }


    @Override
    public void onBackPressed() {

        Intent main = new Intent(this, MainActivity.class);
        //setResult(RESULT_OK);
        finish();
        startActivity(main);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_HOME || keyCode==KeyEvent.KEYCODE_MENU) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            setResult(RESULT_OK);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onResume() {
        super.onResume();
      //  prefs.registerOnSharedPreferenceChangeListener(this);
    }

//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//
//        if (key.equalsIgnoreCase("icons-pack")) {
//            GlobState.getIconsHandler(this).loadIconsPack(sharedPreferences.getString(key, "default"));
//        }
//    }
    
    protected static void setListPreferenceIconsPacksData(ListPreference lp, Context context) {
        IconsHandler iph = GlobState.getIconsHandler(context);

        iph.loadAvailableIconsPacks();

        Map<String, String> iconsPacks = iph.getAllIconsThemes();

        CharSequence[] entries = new CharSequence[iconsPacks.size()];
        CharSequence[] entryValues = new CharSequence[iconsPacks.size()];

        int i = 0;
        for (String packageIconsPack : iconsPacks.keySet()) {
            entries[i] = iconsPacks.get(packageIconsPack);
            entryValues[i] = packageIconsPack;
            i++;
        }

        lp.setEntries(entries);
        lp.setDefaultValue(IconsHandler.DEFAULT_PACK);
        lp.setEntryValues(entryValues);
    }
}

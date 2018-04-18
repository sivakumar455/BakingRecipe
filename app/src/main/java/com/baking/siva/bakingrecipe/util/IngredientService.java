package com.baking.siva.bakingrecipe.util;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baking.siva.bakingrecipe.BakingWidgetProvider;
import com.baking.siva.bakingrecipe.R;

import java.util.ArrayList;

/**
 * Created by sivakumarpadala on 17/04/18.
 */

public class IngredientService extends IntentService {

    public IngredientService() {
        super("IngredientService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.v("Testing","IntentService");
        if (intent != null) {
            final String action = intent.getAction();
            if ("IngList".equals(action)) {
                final ArrayList<String> IngListDet = intent.getStringArrayListExtra("IngList");
                handleIngList();

                Log.v("IngList",String.valueOf(IngListDet));
            }
        }
    }

    private void handleIngList(){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ing_list_widget);
        //Now update all widgets
    }

}

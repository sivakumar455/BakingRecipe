package com.baking.siva.bakingrecipe;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sivakumarpadala on 08/04/18.
 */

public class GridWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(),intent);
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    Context mContext;
    Intent mIntent;
    List<String> collection = new ArrayList<>();

    private void initData(){
        collection.clear();
        for (int idx= 0; idx<10;idx++){
            collection.add("List"+idx);
        }

    }

    public GridRemoteViewsFactory(Context applicationContext,Intent intent){
        mContext= applicationContext;
        mIntent = intent;
    }

    @Override
    public void onCreate() {
        initData();

    }

    @Override
    public void onDataSetChanged() {
        initData();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return collection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, collection.get(position));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

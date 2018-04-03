package com.baking.siva.bakingrecipe;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sivakumarpadala on 31/03/18.
 */

public class RecipeDetailFragment extends Fragment {
    HashMap<String, HashMap<String, String>> hashIng;
    HashMap<String, String> hashStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    public RecipeDetailFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getArguments();
        if(b.getSerializable("hashIngredients") != null) {
            hashIng = (HashMap<String, HashMap<String, String>>) b.getSerializable("hashIngredients");
            Log.v("hashIngredients", String.valueOf(Arrays.asList(hashIng)));

        }else if (b.getSerializable("hashSteps") != null){
            hashStep = (HashMap<String, String>) b.getSerializable("hashSteps");
            Log.v("hashSteps", String.valueOf(Arrays.asList(hashStep)));
        }else {
            Log.v("TAB","Neither Steps nor Ingredients");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = null;
        Log.v("TAB","Checking tab2");
        if (hashStep != null) {
            rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
            TextView textView = rootView.findViewById(R.id.recipe_detail_text_view);
            textView.setText(hashStep.get("description"));
            // Initialize the player view.
            mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.exo_controls_play));
            if(!hashStep.get("videoURL").isEmpty() ) {
                initializePlayer(Uri.parse(hashStep.get("videoURL")));
            }

        }else if(hashIng != null){
            rootView = inflater.inflate(R.layout.fragment_ingredients_list, container, false);

            //LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
            LinearLayout linearLayout = rootView.findViewById(R.id.fragment_ingredients);
            for (int idx=0; idx<Integer.parseInt(hashIng.get("Length").get("ingredientLength")); idx++) {
                TextView textView = new TextView(getActivity().getApplicationContext());
                textView.setText(hashIng.get("ingredients"+idx).get("ingredient")+ "\t\t\t"+
                        hashIng.get("ingredients"+idx).get("quantity")+"\t\t\t"+
                        hashIng.get("ingredients"+idx).get("measure"));
                textView.setTextSize(18);
                textView.setPadding(12,12,12,12);
                linearLayout.addView(textView);
            }
            Toast.makeText(getActivity().getApplicationContext(),"HashTag",Toast.LENGTH_SHORT).show();

        }
        else {
            Log.v("TAB","Checking tab3");
        }
        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector,loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "RecipeDetail");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}

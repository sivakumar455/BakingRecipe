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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author Siva Kumar Padala
 * @version 1.0
 * @since 04/02/18
 */

public class RecipeDetailFragment extends Fragment implements  ExoPlayer.EventListener{
    private HashMap<String, HashMap<String, String>> hashIng;
    private HashMap<String, String> hashStep;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private long playbackPosition;
    private Boolean playWhenReady;
    private int currentWindow;


    public RecipeDetailFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getArguments();

        if(savedInstanceState != null){
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            currentWindow = savedInstanceState.getInt("currentWindow");

            Log.v("PLAY",String.valueOf(playbackPosition)+String.valueOf(playWhenReady)+String.valueOf(currentWindow));
        }else{
            playWhenReady = true;
            playbackPosition = 0;
            currentWindow = 0;
            Log.v("PLAY DEF",String.valueOf(playbackPosition)+String.valueOf(playWhenReady)+String.valueOf(currentWindow));
        }


        if(b.getSerializable("hashIngredients") != null) {
            hashIng = (HashMap<String, HashMap<String, String>>) b.getSerializable("hashIngredients");
            Log.v("hashIngredients", String.valueOf(Collections.singletonList(hashIng)));

        }else if (b.getSerializable("hashSteps") != null){
            hashStep = (HashMap<String, String>) b.getSerializable("hashSteps");
            Log.v("hashSteps", String.valueOf(Collections.singletonList(hashStep)));
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
                //Adding recipe image
                if(!hashStep.get("thumbnailURL").isEmpty()) {
                    Log.v("thumbnailURL", hashStep.get("thumbnailURL"));
                    ImageView recipeImage = rootView.findViewById(R.id.recipe_image);
                    /*Picasso.with(getContext()).load(hashStep.get("thumbnailURL"))
                            .placeholder(R.drawable.ic_launcher_background).into(recipeImage);*/

                    String filePath = hashStep.get("thumbnailURL");

                    Glide.with( getContext() )
                            .load(filePath)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.exo_controls_rewind)
                            .override(200, 200)
                            .into( recipeImage );
                    Log.v("thumbnailURL", "Done Loading");

                }else{
                    Log.v("thumbnailURL", "No URL found");
                }
                // Initialize the player view.
                mPlayerView = rootView.findViewById(R.id.playerView);
                mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                        (getResources(), R.drawable.exo_controls_play));
                if (!hashStep.get("videoURL").isEmpty()) {
                    Log.v("videourl", hashStep.get("videoURL"));
                   // initializePlayer(Uri.parse(hashStep.get("videoURL")));
                } else {
                    //View myView = rootView.findViewById(R.id.playerView);
                    //mPlayerView.onViewRemoved(myView);
                    Log.v("no videourl", hashStep.get("videoURL"));
                    mPlayerView.removeAllViews();
                }

            } else if (hashIng != null) {
                rootView = inflater.inflate(R.layout.fragment_ingredients_list, container, false);

                //LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
                LinearLayout linearLayout = rootView.findViewById(R.id.fragment_ingredients);
                for (int idx = 0; idx < Integer.parseInt(hashIng.get("Length").get("ingredientLength")); idx++) {
                    TextView textView = new TextView(getActivity().getApplicationContext());
                    textView.setText(getString(R.string.ingredients, hashIng.get("ingredients" + idx).get("ingredient"),
                            hashIng.get("ingredients" + idx).get("quantity"),
                            hashIng.get("ingredients" + idx).get("measure")));
                    textView.setTextSize(18);
                    textView.setPadding(12, 12, 12, 12);
                    linearLayout.addView(textView);
                }
                //Toast.makeText(getActivity().getApplicationContext(),"HashTag",Toast.LENGTH_SHORT).show();

            } else {
                Log.v("TAB", "Checking tab3");
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
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(currentWindow, playbackPosition);
        }
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        if(mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

   /* @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("playbackPosition",playbackPosition);
        outState.putBoolean("playWhenReady",playWhenReady);
        outState.putInt("currentWindow",currentWindow);
        Log.v("PLAY",String.valueOf(playbackPosition)+String.valueOf(playWhenReady)+String.valueOf(currentWindow));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if(hashStep != null && mExoPlayer == null ) {
                Log.v("videoURL ",hashStep.get("videoURL"));
                initializePlayer(Uri.parse(hashStep.get("videoURL")));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            if(hashStep != null ) {
                Log.v("videourl",hashStep.get("videoURL"));
                initializePlayer(Uri.parse(hashStep.get("videoURL")));
            }
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            Log.v("ExoPlayer", "onPlayerStateChanged: PLAYING");
        } else if((playbackState == ExoPlayer.STATE_READY)){
            Log.v("ExoPlayer", "onPlayerStateChanged: PAUSED");
        }

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}

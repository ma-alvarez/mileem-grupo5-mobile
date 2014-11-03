package com.mileem.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.mileem.ConfigManager;
import com.mileem.R;

public class PublicationVideoFragment extends Fragment {

	private String url_video;
	private String youtube_id;
	private YouTubePlayerSupportFragment youTubePlayerFragment;

	public static final PublicationVideoFragment newInstance(String url){
		PublicationVideoFragment fragment = new PublicationVideoFragment();
	    Bundle bundle = new Bundle(1);
	    bundle.putString("url", url);
	    fragment.setArguments(bundle);
	    return fragment ;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		url_video = getArguments().getString("url");
		youtube_id = url_video.substring(url_video.indexOf("v=")+2, url_video.length());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            			 Bundle savedInstanceState) {
		

        View itemView = inflater.inflate(R.layout.fragment_video_view, container, false);
	    
//        View detailView = inflater.inflate(R.layout.fragment_detailpublication,
//				 container, false);
//        CirclePageIndicator indicator = (CirclePageIndicator)detailView.findViewById(R.id.indicator);
//        indicator.setVisibility(View.GONE);
        youTubePlayerFragment = new YouTubePlayerSupportFragment();
        //youTubePlayerFragment = (YouTubePlayerSupportFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        youTubePlayerFragment.initialize(ConfigManager.API_KEY,new OnInitializedListener() {

            @Override
            public void onInitializationFailure(Provider arg0, YouTubeInitializationResult arg1) { }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                if (!wasRestored) { 

//                	player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                	player.setPlayerStyle(PlayerStyle.MINIMAL);
//                	player.setOnFullscreenListener(new OnFullscreenListener(){
//
//						@Override
//						public void onFullscreen(boolean arg0) {
//							Log.d("video","fullscreen");
//							FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//							fragmentManager.beginTransaction().
//							replace(R.id.container,FullScreenVideoFragment.newInstance(url_video)).
//							addToBackStack("detalle").commit(); 	
//						}
                		
//                	});
                	player.cueVideo(youtube_id);
                }
            }
            protected YouTubePlayer.Provider getYouTubePlayerProvider() {
              return (YouTubePlayerFragment) getActivity().getFragmentManager().findFragmentById(R.id.youtube_fragment);
            }
        });
	    
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.youtube_fragment, youTubePlayerFragment);
        fragmentTransaction.commit();
	    
	    return itemView;
	}

}

package com.altice_crt_b.rssreader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebFragment extends Fragment {
    /**
     * ----------------------------
     * ------ Class Variables------
     * ----------------------------
     * WebView articleView                          The Web View on which we'll load the URLs for the articles.
     * ProgressBar webProgressBar                   The progress bar we'll show when the site is loading.
     * OnFragmentInteractionListener mListener      This is required by the Fragment, we don't use it.
     */
    private WebView articleView;
    private ProgressBar webProgressBar;

    private OnFragmentInteractionListener mListener;

    public WebFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment WebFragment.
     */
    public static WebFragment newInstance() {
        WebFragment fragment = new WebFragment();
        return fragment;
    }

    /**
     * onCreate                     a function intended to initialize the fragment.
     *                              This function gets called along the onCreate for the Activity to which
     *                              this webFragment will be attached.
     * @param savedInstanceState    A Bundle containing the saved instance state (things such as old data, orientation, etc..)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * onCreateView                 a function intended to initialize and inflate the VIEW of the fragment.
     *                              while the onCreate function above can be used to initialize the variable,
     *                              it still doesn't contain the view elements (TextViews, ImageViews, etc..)
     *                              of the Layout that is applied to the fragment.
     *                              Because of this, we use a secondary function to inflate (which basically
     *                              translates to convert the XML Layout we want to a map or data structure)
     *                              and return the final view.
     * @param inflater              The system inflater
     * @param container             The parent to which the created view will be attached
     * @param savedInstanceState    A Bundle containing the saved instance state (things such as old data, orientation, etc..)
     * @return View                 The final view object contining all the underlying elements on the XML.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //First step is to inflate the view.
        //This results in a View element with all the XML elements as objects inside it
        //allowing us to properly call findViewById ( id ) on the view.
        View webFragmentView = inflater.inflate(R.layout.activity_web, container, false);
        //Variable Initialization
        webProgressBar = webFragmentView.findViewById(R.id.web_progress_bar);
        articleView = (WebView) webFragmentView.findViewById(R.id.article_view);
        //We need a webClient to manage and decode the HTTP response, so we set a generic
        //web client to the WebView.
        articleView.setWebViewClient(new WebViewClient());
        //Along the generic webClient, we also set a Chrome Client. This client allows us
        //to handle more stuff, including the events regarding loading, maps, javascript, etc..
        //While it may be possible to work with only one, tests have proved that both are required
        //If the setWebViewClient is removed, the app will look for a different way to open the url.
        articleView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //The chrome Web Client has an onProgressChanged event
                //We can use this to manage the progress bar, showing the user how far his
                //article has loaded.
                if(progress < 100 && webProgressBar.getVisibility() == ProgressBar.INVISIBLE){
                    webProgressBar.setVisibility(ProgressBar.VISIBLE);
                }

                webProgressBar.setProgress(progress);
                if(progress == 100) {
                    webProgressBar.setVisibility(ProgressBar.INVISIBLE);
                }
            }
        });

        // Inflate the layout for this fragment
        return webFragmentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //----------------------------------- Custom Methods -------------------------------------------

    /**
     * loadUrl      A method intended to load the URL into the article View when changing articles.
     * @param url   The url of the new article to load.
     */
    public void loadUrl(String url) {
        articleView.loadUrl(url);
    }
}

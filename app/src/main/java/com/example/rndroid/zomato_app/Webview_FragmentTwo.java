package com.example.rndroid.zomato_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class Webview_FragmentTwo extends Fragment {

    WebView webView;
    public Webview_FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_webview__fragment_two, container, false);
        webView = (WebView) v.findViewById(R.id.my_webview);
        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.co.in/?gfe_rd=cr&ei=ADeKWJicC5CAoAO-97XwAQ#q="+name);
        return v;
    }
}

package oms.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import oms.ability.UIAbility;


public class UIWebView extends LinearLayoutCompat {
    ProgressBar progressBar;
    WebView webView;
    public UIWebView(@NonNull UIAbility context) {
        this(context,null);
    }
    public UIWebView(UIAbility ability, AttributeSet set){
        this(ability,set,0);
    }
    public UIWebView(UIAbility ability, AttributeSet set,int t){
        super(ability,set,t);
        this.setOrientation(VERTICAL);
        webView = new WebView(ability);
        progressBar = new ProgressBar(ability);
        this.addView(progressBar);
        LayoutParams layoutParams = (LayoutParams) progressBar.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = ability.dip2px(1);
        progressBar.setLayoutParams(layoutParams);
        this.addView(webView);
        LayoutParams layoutParams2 = (LayoutParams) progressBar.getLayoutParams();
        layoutParams.width = LayoutParams.MATCH_PARENT;
        layoutParams.height = LayoutParams.MATCH_PARENT;
        webView.setLayoutParams(layoutParams);
    }
    public void loadUrl(String string){
        webView.loadUrl(string);
    }
    class WebView extends android.webkit.WebView {
        public WebView(@NonNull Context context) {
            super(context);
        }
    }


}

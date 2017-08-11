package lab.first.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browser.loadUrl("http://www.tutorialspoint.com");
        WebView view=(WebView) this.findViewById(R.id.webView);
    }
}

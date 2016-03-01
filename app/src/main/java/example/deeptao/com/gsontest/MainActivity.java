package example.deeptao.com.gsontest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    static final String JSON_SAMPLE = "[\n" +
            "{\n" +
            "  \"id\":1,\n" +
            "  \"ad_name\":\"Test Ad\",\n" +
            "  \"ad_text\":\"This is my ad text\"\n" +
            "},\n" +
            "{\n" +
            "  \"id\":2,\n" +
            "  \"ad_name\":\"Test Ad\",\n" +
            "  \"ad_text\":\"This is my ad text\"\n" +
            "},\n" +
            "{\n" +
            "  \"id\":3,\n" +
            "  \"ad_name\":\"Test Ad\",\n" +
            "  \"ad_text\":\"This is my ad text\"\n" +
            "},\n" +
            "{\n" +
            "  \"id\":4,\n" +
            "  \"ad_name\":\"Test Ad\",\n" +
            "  \"ad_text\":\"This is my ad text\"\n" +
            "},\n" +
            "{\n" +
            "  \"id\":5,\n" +
            "  \"ad_name\":\"Test Ad\",\n" +
            "  \"ad_text\":\"This is my ad text\"\n" +
            "}\n" +
            "]";

    private TextView mTextView;
    private List<Ad> mList;

    protected SpiceManager spiceManager = new SpiceManager(CustomSpiceService.class);
    private String lastRequestCacheKey;


    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextView = (TextView) findViewById(R.id.text_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void performRequest(String user) {
        MainActivity.this.setProgressBarIndeterminateVisibility(true);

        FollowersRequest request = new FollowersRequest(user);
        lastRequestCacheKey = request.createCacheKey();

        spiceManager.execute(request, lastRequestCacheKey, DurationInMillis.ONE_MINUTE, new ListFollowersRequestListener());
    }

    @Override
    public void onClick(View view) {
        performRequest("google");
    }

    //inner class of your spiced Activity
    private class ListFollowersRequestListener implements RequestListener<FollowerList> {

        @Override
        public void onRequestFailure(SpiceException e) {
            //update your UI
            String text = e.getMessage();
            mTextView.setText(text);
        }

        @Override
        public void onRequestSuccess(FollowerList listFollowers) {
            String text = "Nothing";

            for (Follower listFollower : listFollowers) {
                text += listFollower.getLogin();
                text += "\n";
            }

            mTextView.setText(text);
        }
    }

    //We use ArrayList as base class since the root element is a JSON array
    public class FollowerList extends ArrayList<Follower> {
        private static final long serialVersionUID = 8192333539004718470L;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Follower {

        @JsonProperty("login")
        private String login;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

}

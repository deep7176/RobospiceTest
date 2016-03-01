package example.deeptao.com.gsontest;

import android.net.Uri;
import android.os.Build;

import com.octo.android.robospice.request.SpiceRequest;

import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by deeptaco on 2/29/16.
 */
public class ReverseStringRequest extends SpiceRequest<String> {

    private String word;

    public ReverseStringRequest(String word) {
        super(String.class);
        this.word = word;
    }

    @Override
    public String loadDataFromNetwork() throws Exception {

        // With Uri.Builder class we can build our url is a safe manner
        Uri.Builder uriBuilder = Uri.parse(
                "http://robospice-sample.appspot.com/reverse").buildUpon();
        uriBuilder.appendQueryParameter("word", word);

        String url = uriBuilder.build().toString();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }

        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url)
                .openConnection();
        String result = IOUtils.toString(urlConnection.getInputStream());
        urlConnection.disconnect();

        return result;
    }

}
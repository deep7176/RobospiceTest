package example.deeptao.com.gsontest;

import android.app.Application;
import android.util.Log;

import com.octo.android.robospice.SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by deeptaco on 2/29/16.
 */
public class CustomSpiceService extends SpringAndroidSpiceService {
    private static final String TAG = "CustomSpiceService";

    @Override
    public CacheManager createCacheManager (Application application) {
        CacheManager cacheManager = new CacheManager();
        JacksonObjectPersisterFactory jacksonObjectPersisterFactory = null;
        try {
            jacksonObjectPersisterFactory = new JacksonObjectPersisterFactory(application);
        } catch (CacheCreationException e) {
            e.printStackTrace();
            Log.e(TAG, "createCacheManager: Error making jacksonObjectPersisterFactory");
            return null;
        }
        cacheManager.addPersister(jacksonObjectPersisterFactory);
        return cacheManager;
    }

    @Override
    public RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //find more complete examples in RoboSpice Motivation app
        //to enable Gzip compression and setting request timeouts.

        // web services support json responses
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        final List<HttpMessageConverter< ? >> listHttpMessageConverters = restTemplate.getMessageConverters();

        listHttpMessageConverters.add(jsonConverter);
        listHttpMessageConverters.add(formHttpMessageConverter);
        listHttpMessageConverters.add(stringHttpMessageConverter);
        restTemplate.setMessageConverters(listHttpMessageConverters);
        return restTemplate;
    }
}
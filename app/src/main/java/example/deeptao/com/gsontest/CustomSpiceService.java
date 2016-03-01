package example.deeptao.com.gsontest;

import android.app.Application;
import android.util.Log;

import com.octo.android.robospice.SpringAndroidSpiceService;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.springandroid.json.jackson.JacksonObjectPersisterFactory;

import org.springframework.web.client.RestTemplate;

/**
 * Created by deeptaco on 2/29/16.
 */
public class CustomSpiceService extends SpringAndroidSpiceService {
    private static final String TAG = "CustomSpiceService";

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
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
        return null;
    }
}
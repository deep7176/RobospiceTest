package example.deeptao.com.gsontest;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

/**
 * Created by deeptaco on 2/29/16.
 */
public class FollowersRequest extends SpringAndroidSpiceRequest<MainActivity.FollowerList> {

    private String user;

    public FollowersRequest(String user) {
        super(MainActivity.FollowerList.class);
        this.user = user;
    }

    @Override
    public MainActivity.FollowerList loadDataFromNetwork() throws Exception {

        String url = String.format("https://api.github.com/users/%s/followers", user);

        return getRestTemplate().getForObject(url, MainActivity.FollowerList.class);
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "followers." + user;
    }
}
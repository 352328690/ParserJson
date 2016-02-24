package parserjson.com.example.json;

/**
 * Created by lenovo on 2016/2/23.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}

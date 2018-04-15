package viktorsever.viktorshkuratov;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiController {

    private AsyncHttpClient httpClient;
    private static ApiController self;

    private ApiController() {
        if (self != null) {
            throw new IllegalStateException("Instance of ApiController was already created");
        }
        httpClient = new AsyncHttpClient();
    }

    public static ApiController self() {
        if (self == null) {
            self = new ApiController();
        }
        return self;
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        httpClient.get(url, params, responseHandler);
    }

}

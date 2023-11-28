package utils;

import java.util.HashMap;

public class Headers {

    public static HashMap<String, String> getHeadersMap() {
        HashMap<String, String> reqHeaders = new HashMap<>();
        reqHeaders.put("Content-Type", "application/json");
        return reqHeaders;
    }
}

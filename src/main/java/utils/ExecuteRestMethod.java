package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;

public class ExecuteRestMethod {

    public static Response executeGET(String baseURL, String pathParam, String queryParam, HashMap<String, String> reqHeaders) {
        try {
            Response res = RestAssured.given().headers(reqHeaders).get(baseURL + pathParam + queryParam);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Response executePOST(String baseURL, String pathParam, String reqPayload, HashMap<String, String> reqHeaders) throws InterruptedException {
        try {
            Response res = RestAssured.given().headers(reqHeaders).body(reqPayload).post(baseURL + pathParam);
            int retryCount = 2;
            while (res.getStatusCode() == 500 && retryCount > 0) {
                res = RestAssured.given().headers(reqHeaders).body(reqPayload).post(baseURL + pathParam);
                retryCount--;
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getClass().getSimpleName().equalsIgnoreCase("SocketException")) {
                Thread.sleep(1000);
                Response res = RestAssured.given().headers(reqHeaders).body(reqPayload).put(baseURL + pathParam);
                System.out.println("Retry done:" + res.asString());
                if (res.getStatusCode() != 200 && res.getStatusCode() != 400) {
                    return null;
                }
                return res;
            }
        }
        return null;
    }

    public static Response executePUT(String baseURL, String pathParam, String reqPayload, HashMap<String, String> reqHeaders) throws InterruptedException {
        Response res = null;
        try {
            if (reqPayload != null) {
                res = RestAssured.given().headers(reqHeaders).body(reqPayload).put(baseURL + pathParam);
            } else {
                res = RestAssured.given().headers(reqHeaders).put(baseURL + pathParam);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getClass().getSimpleName().equalsIgnoreCase("SocketException")) {
                Thread.sleep(1000);
                if (reqPayload != null) {
                    res = RestAssured.given().headers(reqHeaders).body(reqPayload).put(baseURL + pathParam);
                } else {
                    res = RestAssured.given().headers(reqHeaders).put(baseURL + pathParam);
                }
                System.out.println("Retry done:" + res.asString());
                if (res.getStatusCode() != 400 && res.getStatusCode() != 200) {
                    return null;
                }
                return res;
            }
        }
        return res;
    }

    public static Response executeDelete(String baseURL, String pathParam, String reqPayload, HashMap<String, String> reqHeaders) throws InterruptedException {
        try {
            Response res = RestAssured.given().headers(reqHeaders).body(reqPayload).delete(baseURL + pathParam);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getClass().getSimpleName().equalsIgnoreCase("SocketException")) {
                Thread.sleep(1000);
                Response res = RestAssured.given().headers(reqHeaders).body(reqPayload).put(baseURL + pathParam);
                System.out.println("Retry done:" + res.asString());
                if (res.getStatusCode() != 400 && res.getStatusCode() != 200) {
                    return null;
                }
                return res;
            }
        }
        return null;
    }
}

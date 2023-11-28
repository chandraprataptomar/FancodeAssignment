import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.ExecuteRestMethod;
import utils.Headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class taskStatusTest extends BaseTest {
    public static ExecuteRestMethod method = new ExecuteRestMethod();
   public static Headers header = new Headers();
    HashMap<String, String> reqHeaders = header.getHeadersMap();
    public static String baseURL= BaseTest.baseUrl;
    @Test(description = "User Completed task percentage should be greater than 50%")
    public void taskStatusTest() {
        Response userResponse = method.executeGET(baseURL, "users", "", reqHeaders);
        ResponseBody userResponseBody = userResponse.getBody();
        System.out.println(userResponseBody.asString());
        List<Integer> userID = new ArrayList<>();


        JSONArray usersArray = new JSONArray(userResponseBody.asString());

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject user = usersArray.getJSONObject(i);
            JSONObject geo = user.getJSONObject("address").getJSONObject("geo");

            double lat = Double.parseDouble(geo.getString("lat"));
            double lng = Double.parseDouble(geo.getString("lng"));

            if (lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100) {
                userID.add(user.getInt("id"));
                System.out.println("User ID: " + user.getInt("id"));
            }
        }

        Response todosResponse = method.executeGET(baseURL, "todos", "", reqHeaders);
        JSONArray todoArrayUserList = new JSONArray(todosResponse.getBody().asString());

        for(int userId : userID ){
            int taskCompleted=0;
            int taskIncomplete=0;
            for (int i = 0; i < todoArrayUserList.length(); i++) {

                int currentUserId=todoArrayUserList.getJSONObject(i).getInt("userId");
                if(userId == currentUserId){
                    JSONObject user = todoArrayUserList.getJSONObject(i);
                    Boolean taskStatus = user.getBoolean("completed");
                    if(taskStatus){
                        taskCompleted++;
                    }else{
                        taskIncomplete++;
                    }
                }
        }
            if(taskCompleted>taskIncomplete && taskCompleted!=0){
                System.out.println(taskCompleted);
                System.out.println(taskIncomplete);
                System.out.println(userId);
            }
        }
    }
}

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.CONS;
import utils.ExecuteRestMethod;
import utils.Headers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskStatusTest extends BaseTest {
    public static ExecuteRestMethod method = new ExecuteRestMethod();
   public static Headers header = new Headers();
    HashMap<String, String> reqHeaders = header.getHeadersMap();
    public static String baseURL= BaseTest.baseUrl;
    @Test(description = "User Completed task percentage should be greater than 50%")
    public void taskStatusTest() {
        Response userResponse = method.executeGET(baseURL, CONS.endpointUser, "", reqHeaders);
        ResponseBody userResponseBody = userResponse.getBody();
        List<Integer> userID = new ArrayList<>();


        JSONArray usersArray = new JSONArray(userResponseBody.asString());

        for (int i = 0; i < usersArray.length(); i++) {
            JSONObject user = usersArray.getJSONObject(i);
            JSONObject geo = user.getJSONObject(CONS.address).getJSONObject(CONS.geo);

            double lat = Double.parseDouble(geo.getString(CONS.lat));
            double lng = Double.parseDouble(geo.getString(CONS.longitude));
       // Getting userID of fancode city according to given condition
            if (lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100) {
                userID.add(user.getInt("id"));
            }
        }

        Response todosResponse = method.executeGET(baseURL, CONS.endpointTodos, "", reqHeaders);
        JSONArray todoArrayUserList = new JSONArray(todosResponse.getBody().asString());

        for(int userId : userID ){
            int taskCompleted=0;
            int taskIncomplete=0;
            for (int i = 0; i < todoArrayUserList.length(); i++) {
              // getting total completed task and incompleted task
                int currentUserId=todoArrayUserList.getJSONObject(i).getInt(CONS.userId);
                if(userId == currentUserId){
                    JSONObject user = todoArrayUserList.getJSONObject(i);
                    Boolean taskStatus = user.getBoolean(CONS.completed);
                    if(taskStatus){
                        taskCompleted++;
                    }else{
                        taskIncomplete++;
                    }
                }
        }
            // Returning Users from fancode city having task percentage greater than 50%
            if(taskCompleted>taskIncomplete && taskCompleted!=0){
                System.out.println("UserId: "+userId+" has task percentage greater than 50%");
            }
        }
    }
}

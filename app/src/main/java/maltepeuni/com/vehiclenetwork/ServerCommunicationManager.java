package maltepeuni.com.vehiclenetwork;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import maltepeuni.com.vehiclenetwork.database.AuthorizedUser;
import maltepeuni.com.vehiclenetwork.database.Comment;
import maltepeuni.com.vehiclenetwork.database.DBObject;
import maltepeuni.com.vehiclenetwork.database.Denounce;
import maltepeuni.com.vehiclenetwork.database.StandartUser;
import maltepeuni.com.vehiclenetwork.database.User;
import maltepeuni.com.vehiclenetwork.database.Vehicle;
import maltepeuni.com.vehiclenetwork.database.VehicleDetail;

/**
 * Created by LaptopPc on 2.5.2017.
 */

public class ServerCommunicationManager {
    public static ServerCommunicationManager M = new ServerCommunicationManager();

    public WelcomeActivity welcomeActivity;
    public LoginActivity loginActivity;
    public RegisterActivity registerActivity;
    public PassForgotActivity passForgotActivity;
    public InstructionsActivity instructionsActivity;
    public InstructionsAuthActivity instructionsAuthActivity;
    public MainActivity mainActivity;
    public MainAuthActivity mainAuthActivity;

    public String waiting;
    public MongoLabHelper helper;

    public void TryLogin(boolean auth,User u){
        Log.i("JSON","ARANIYOR...");
        helper = new MongoLabHelper();
        if(!auth){
            waiting = "Login";
            helper.setCollection("standart_users");
            helper.findOne("{ \"email\":\""+u.email+"\",\"passHash\":\"" + u.passHash + "\" }");
        }else{
            waiting = "AuthLogin";
            helper.setCollection("authorized_users");
            helper.findOne("{ \"email\":\""+u.email+"\",\"passHash\":\"" + u.passHash + "\" }");
        }

    }

    public void Register(StandartUser su){
        Log.i("JSON","KAYID...");
        helper = new MongoLabHelper();
        helper.setCollection("standart_users");
        waiting = "Register";
        Log.i("JSON","KAYID..."+JsonClassConverter.toJSON(su));
        helper.insert(JsonClassConverter.toJSON(su));
    }

    public void SendDenounce(Denounce d){
        helper = new MongoLabHelper();
        helper.setCollection("denounces");
        helper.insert(JsonClassConverter.toJSON(d));

    }

    public void GetDenounceList(){
        helper = new MongoLabHelper();
        helper.setCollection("denounces");
        waiting = "GetV";
        helper.getCollection();
    }

    public void AddVehicle(Vehicle v, VehicleDetail vd){
        helper = new MongoLabHelper();
        helper.setCollection("vehicle_summaries");
        helper.insert(JsonClassConverter.toJSON(v));

    }
    public void RemoveVehicle(Vehicle v){
        helper = new MongoLabHelper();
        helper.setCollection("vehicle_summaries");
        helper.delete(v.id);

    }
    public void GetVehicleInfo(Vehicle v){
        helper = new MongoLabHelper();
        helper.setCollection("vehicles");
        waiting = "GetV";
        helper.findOne("{\"_id\":{\"oid\":\""+v.id+"\"}}");
    }

    public void GetNews(){
        helper = new MongoLabHelper();
        helper.setCollection("news_flow");
        waiting = "News";
        helper.getCollection();
    }
    public void SendComment(Comment c){
        helper = new MongoLabHelper();
        helper.setCollection("comments");

    }

    public void SetProfile(StandartUser su){
        helper = new MongoLabHelper();
        helper.setCollection("user_list");
        waiting = "Profile";

    }

    public void Search(Vehicle v,VehicleDetail vd){
        helper = new MongoLabHelper();
        helper.setCollection("vehicle_summaries");
        waiting = "Search";
    }


    public void Finish(String result) {
        if(result == null){
            Log.i("JSON","NULL");
            return;
        }
        else
            Log.i("JSON","#"+result+"#");

        switch (waiting){
            case "Login":
                if(result.equals(" null "))
                    loginActivity.LoginResult(false,false);
                else{
                    loginActivity.LoginResult(true,false);
                    //MainActivity.user = JsonClassConverter.toObject(result,StandartUser.class);
                }
                break;
            case "AuthLogin":
                if(result.equals(" null "))
                    loginActivity.LoginResult(false,true);
                else
                    loginActivity.LoginResult(true,true);
                break;
            case "Register":
                registerActivity.RegisterResult( result == null );
                break;
            case "News":

                break;
            case "Profile":

                break;
            case "GetV":

                break;
            case "GetD":

                break;
            case "Search":

                break;
            case "UpdateD":

                break;
            case "RemoveD":

                break;

        }
    }

}

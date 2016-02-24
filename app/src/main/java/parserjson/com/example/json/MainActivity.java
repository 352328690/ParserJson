package parserjson.com.example.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Button jsonObject;
    public Button gson;
    public ArrayList<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonObject = (Button)findViewById(R.id.jsonObject_parser);
        gson = (Button)findViewById(R.id.GSON_parser);

        jsonObject.setOnClickListener(listener);
        gson.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.jsonObject_parser:
                    readJsonWithJsonObject();
                    break;
                case R.id.GSON_parser:
//                    readJsonWithGson();
                    break;
                default:
                    break;
            }
        }
    };

    private void readJsonWithJsonObject(){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                personList = new ArrayList<Person>();
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = reader.readLine())!=null){
                        sb.append(line);
                    }
                    String jsonStr = sb.toString();
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        Person person = new Person();
                        person.setName(jsonObject.getString("name"));
                        person.setAge(jsonObject.getInt("age"));
                        Log.d("JSON", "解析JSON数据："+person.toString());
                        personList.add(person);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (is()!=null){
                        try {
                            is().close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();*/

        String address = "http://10.0.2.2:8088/data.json";

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

                personList = new ArrayList<Person>();
                String jsonStr = response.toString();
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        Person person = new Person();
                        person.setName(jsonObject.getString("name"));
                        person.setAge(jsonObject.getInt("age"));
                        Log.d("JSON", "解析JSON数据："+person.toString());
                        personList.add(person);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    /*private void readJsonWithGson(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = reader.readLine())!=null){
                        sb.append(line);
                    }
                    String jsonStr = sb.toString();
//                    Gson gson = new Gson();
                    JSONArray jsonArray = new JSONArray(jsonStr);
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        Person person = new Person();
                        person.setName(jsonObject.getString("name"));
                        person.setAge(jsonObject.getInt("age"));
                        Log.d("JSON", "解析JSON数据："+person.toString());
                        personList.add(person);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (is()!=null){
                        try {
                            is().close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    public InputStream is(){
        InputStream is = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://10.0.2.2:8088/data.json");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            is = connection.getInputStream();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return is;
    }*/
}

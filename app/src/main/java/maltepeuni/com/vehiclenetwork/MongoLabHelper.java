package maltepeuni.com.vehiclenetwork;

        import android.os.AsyncTask;
        import android.util.Log;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.io.OutputStreamWriter;
        import java.lang.reflect.InvocationTargetException;
        import java.lang.reflect.Method;
        import java.net.HttpURLConnection;
        import java.net.MalformedURLException;
        import java.net.URI;
        import java.net.URISyntaxException;
        import java.net.URL;
        import java.util.concurrent.ExecutionException;

/**
 * Created by Umut ONUR on 04.03.2016.
 */

public class MongoLabHelper extends AsyncTask<String, Void, String>{

    final String baseUrl = "https://api.mlab.com/api/1/";
    private String apiKey = "CBoCMl4kvi_ZMVEo2m3CBsw7h2AD_9dt";
    private String database = "vehicle_network";
    private String collection = "news_flow";
    private URL url = null;
    private HttpURLConnection httpURLConnection = null;


    private String requestMethod;
    private boolean doOutputMethod;
    private String query;
    private String outputData;

    String response = null;

    private String getApiKey() {
        return "apiKey=" + apiKey;
    }

    private String getBaseURL() {
        return baseUrl;
    }

    private String getDatabaseURL() {
        return getBaseURL() + "databases" + "?" + getApiKey();
    }

    private String getDatabaseURL(String database) {
        return getBaseURL() + "databases/" + database + "?" + getApiKey();
    }

    private String getCollectionURL() {
        return getBaseURL() + "databases/" + database + "/collections" + "?" + getApiKey();
    }

    private String getCollectionURL(String collection) {
        return getBaseURL() + "databases/" + database + "/collections/" + collection + "?"
                + getApiKey();
    }

    private String getCollectionURL(String database, String collection) {
        return getBaseURL() + "databases/" + database + "/collections/" + collection + "?"
                + getApiKey();
    }

    private String getDocumentURL(String documentId) {
        return baseUrl + "databases/" + database + "/collections/" + collection + "/" + documentId
                + "?" + getApiKey();
    }

    private String getFindOneURL(String query) {
        return getBaseURL() + "databases/" + database + "/collections/" + collection + "?q="
                + query + "&fo=true&" + getApiKey();
    }

    private String getFindURL(String query) {
        return getBaseURL() + "databases/" + database + "/collections/" + collection + "?q="
                + query + "&" + getApiKey();
    }

    private String getUpdateManyURL(String query) {
        return getBaseURL() + "databases/" + database + "/collections/" + collection + "?q="
                + query + "&m=true&" + getApiKey();
    }

    private String getUpsertURL(String query) {
        return getBaseURL() + "databases/" + database + "/collections/" + collection + "?q="
                + query + "&u=true&" + getApiKey();
    }

    private String getCountURL(String query) {
        return getBaseURL() + "databases/" + database + "/collections/" + collection + "?q="
                + query + "&c=true&" + getApiKey();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    private URL toURL(String urlString) {
        URL tempUrl = null;
        try {
            tempUrl = new URL(urlString);
            URI uri = new URI(tempUrl.getProtocol(), tempUrl.getUserInfo(), tempUrl.getHost(),
                    tempUrl.getPort(), tempUrl.getPath(), tempUrl.getQuery(), tempUrl.getRef());
            tempUrl = uri.toURL();
        } catch (MalformedURLException e) {
            Log.i("HATA:",e.getMessage());
        } catch (URISyntaxException e) {
            Log.i("HATA:",e.getMessage());
        }
        return tempUrl;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(requestMethod);
            httpURLConnection.setRequestProperty("Content-type", "application/json");

            if (requestMethod.equals("GET")) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                response = bufferedReader.readLine();
                bufferedReader.close();
                Log.e("MongoLab Process", "Getting Data..");
            } else if (requestMethod.equals("POST") || requestMethod.equals("PUT")) {
                OutputStreamWriter outputStreamWriter =
                        new OutputStreamWriter(httpURLConnection.getOutputStream());
                outputStreamWriter.write(outputData);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                Log.e("MongoLab Process", "Sending Data..");
            } else {
                Log.e("MongoLab Process", "Deleting Data..");
            }
            Log.e("MongoLab Status Code", String.valueOf(httpURLConnection.getResponseCode()));
            Log.e("MongoLab Status Message", httpURLConnection.getResponseMessage());
            if (requestMethod.equals("GET")) {
                Log.e("MongoLab Response", response);
            } else if (requestMethod.equals("POST") || requestMethod.equals("PUT")) {
                Log.e("MongoLab Output Data", outputData);
            }
        } catch (MalformedURLException e) {
            Log.i("HATA:",e.getMessage());
        } catch (IOException e) {
            Log.i("HATA:",e.getMessage());
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        ServerCommunicationManager.M.Finish(s);

    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        Log.i("MongoHelper","Iptal Edildi !!!");
    }

    public String getCollection(String database, String collection) {
        try {
            this.requestMethod = "GET";
            this.url = toURL(getCollectionURL(collection));
            response = execute().get();
        } catch (InterruptedException e) {
            Log.i("HATA:",e.getMessage());
        } catch (ExecutionException e) {
            Log.i("HATA:",e.getMessage());
        }
        return response;
    }

    public String getCollection(String collection) {
        try {
            this.requestMethod = "GET";
            this.url = toURL(getCollectionURL(collection));
            response = execute().get();
        } catch (InterruptedException e) {
            Log.i("HATA:",e.getMessage());
        } catch (ExecutionException e) {
            Log.i("HATA:",e.getMessage());
        }
        return response;
    }

    public String getCollection() {
        try {
            this.requestMethod = "GET";
            this.url = toURL(getCollectionURL(collection));
            response = execute().get();
        } catch (InterruptedException e) {
            Log.i("HATA:",e.getMessage());
        } catch (ExecutionException e) {
            Log.i("HATA:",e.getMessage());
        }
        return response;
    }

    public String getCollections() {
        try {
            this.requestMethod = "GET";
            this.url = toURL(getCollectionURL());
            response = execute().get();
        } catch (InterruptedException e) {
            Log.i("HATA:",e.getMessage());
        } catch (ExecutionException e) {
            Log.i("HATA:",e.getMessage());
        }
        return response;
    }

    public String getDocument(String docId) {
        try {
            this.requestMethod = "GET";
            this.url = toURL(getDocumentURL(docId));
            response = execute().get();
        } catch (InterruptedException e) {
            Log.i("HATA:",e.getMessage());
        } catch (ExecutionException e) {
            Log.i("HATA:",e.getMessage());
        }
        return response;
    }

    public String findOne(String query) {
        /*try {
            this.requestMethod = "GET";
            this.url = toURL(getFindOneURL(query));
            response = execute().get();
        } catch (InterruptedException e) {
            Log.i("HATA:",e.getMessage());
        } catch (ExecutionException e) {
            Log.i("HATA:",e.getMessage());
        }*/

        this.requestMethod = "GET";
        this.url = toURL(getFindOneURL(query));
        Log.i("FINDONE",this.url.toString());
        execute();

        return response;
    }

    public String find(String query) {
        /*try {
            this.requestMethod = "GET";
            this.url = toURL(getFindURL(query));
            response = execute().get();
        } catch (InterruptedException e) {
            Log.i("HATA:",e.getMessage());
        } catch (ExecutionException e) {
            Log.i("HATA:",e.getMessage());
        }*/

        this.requestMethod = "GET";
        this.url = toURL(getFindURL(query));
        execute();

        Log.i("###","444");

        return response;
    }

    public void insert(JSONObject newData) {
        this.requestMethod = "POST";
        this.outputData = newData.toString();
        this.url = toURL(getCollectionURL(collection));
        execute();
    }

    public void insert(JSONArray newData) {
        this.requestMethod = "POST";
        this.outputData = newData.toString();
        this.url = toURL(getCollectionURL(collection));
        execute();
    }

    public void update(String docId, JSONObject newData) {
        this.requestMethod = "PUT";
        this.outputData = "{ $set: " + newData.toString() + " }";
        this.url = toURL(getDocumentURL(docId));
        execute();
    }

    public void update(JSONObject newData, String query) {
        this.requestMethod = "PUT";
        this.outputData = "{ $set: " + newData.toString() + " }";
        this.url = toURL(getFindURL(query));
        execute();
    }

    public void updateMany(JSONObject newData, String query) {
        this.requestMethod = "PUT";
        this.outputData = "{ $set: " + newData.toString() + " }";
        this.url = toURL(getUpdateManyURL(query));
        execute();
    }

    public void upsert(JSONObject newData, String query) {
        this.requestMethod = "PUT";
        this.outputData = "{ $set: " + newData.toString() + " }";
        this.url = toURL(getUpdateManyURL(query));
        execute();
    }

    public void delete(String docId) {
        //this.oid = docId;
        this.requestMethod = "DELETE";
        this.url = toURL(getDocumentURL(docId));
        execute();
    }

    public int count(String query) {
        try {
            this.requestMethod = "GET";
            this.url = toURL(getCountURL(query));
            response = execute().get();
        } catch (InterruptedException e) {
            Log.i("HATA:",e.getMessage());
        } catch (ExecutionException e) {
            Log.i("HATA:",e.getMessage());
        }
        return Integer.parseInt(response);
    }


}
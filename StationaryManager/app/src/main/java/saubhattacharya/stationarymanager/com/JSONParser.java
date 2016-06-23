package saubhattacharya.stationarymanager.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONParser {

    StringBuilder jsonresponse;

    public JSONParser()
    {
    }

    public String makeHTTPRequest (URL url, String method, String data)
    {
        try
        {
            HttpURLConnection connection = null;

            connection = (HttpURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method);
            connection.setUseCaches(false);
            connection.setConnectTimeout(720000000);
            connection.setReadTimeout(72000000);
            connection.connect();

            if(data != null) {
                OutputStreamWriter output = new OutputStreamWriter(connection.getOutputStream());
                output.write(data);
                output.flush();
                output.close();
            }

            jsonresponse = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String receiveString = null;

            while ((receiveString = bufferedReader.readLine()) != null) {
                jsonresponse.append(receiveString);
            }

            connection.disconnect();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return jsonresponse.toString();
    }
}

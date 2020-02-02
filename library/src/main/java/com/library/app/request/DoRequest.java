package com.library.app.request;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class DoRequest {

    public static JSONObject getRequest(String path) {
        String jsonResponse = null;
        try {
            HttpResponse<String> httpResponse;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(path))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofMinutes(1))
                    .GET()
                    .build();

            httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            jsonResponse = httpResponse.body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return new JSONObject(jsonResponse);
    }

}

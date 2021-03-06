package com.library.app.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Request {

    public static JSONObject getRequest(String path, String wordSearched) {
        String jsonResponse = null;
        try {
            HttpResponse<String> httpResponse;
            HttpClient client = HttpClient.newHttpClient();
            String wordEncoded = URLEncoder.encode(wordSearched, "UTF-8");
            String fullPath = path + wordEncoded;
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(fullPath))
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

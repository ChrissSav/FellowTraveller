package com.example.fellowtraveller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectToServer {
    private static final String PB_URL = "http://snf-871339.vm.okeanos.grnet.gr:5000";
    private Gson gson = new GsonBuilder().create();

    private String json;


    public boolean getusers() {
        json = this.getJSON("/getusers" );

        // Convert JSON to a List<Store> object if nothing ugly happened
        if (json != null)
            return true;



        // Return List<Store> if no error occurs, null otherwise

        return false;
    }


    public String getJSON(String endpoint) {
        HttpURLConnection conn = null;

        // Create a StringBuilder for the final URL
        StringBuilder finalURL = new StringBuilder();
        // Append API URL
        finalURL.append(PB_URL);
        // Append endpoint
        finalURL.append(endpoint);

        // Create a StringBuilder to store the JSON string
        StringBuilder result = new StringBuilder();
        try {
            // Make a connection with the API
            java.net.URL url = new URL(finalURL.toString());
            conn = (HttpURLConnection) url.openConnection();

            // Begin streaming the JSON
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            // Read the first line
            String line = reader.readLine();

            // If an error is thrown, we're done here
            if (line.contains("error"))
                return null;

            // Append the first line to the builder
            result.append(line);

            // Read the remaining stream until we are done
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            // Usually indicates a lack of Internet connection
            return null;
        } finally {
            // Close connection
            if (conn != null)
                conn.disconnect();
        }

        // Return the JSON string
        return result.toString();
    }
}
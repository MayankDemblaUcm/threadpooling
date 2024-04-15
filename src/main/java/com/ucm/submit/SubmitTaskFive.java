package com.ucm.submit;

import java.net.HttpURLConnection;
import java.net.URL;

public class SubmitTaskFive {

    public static void main(String[] args) throws Exception {
        URL url = new URL("http://localhost:8080");
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            System.out.println("Submit Task Five with iteration " + i);
            long startTimeT = System.currentTimeMillis();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //connection.setDoOutput(true);
            // OutputStream outputStream = connection.getOutputStream();
            // Write Excel file content to outputStream
            // Example: outputStream.write(excelData.getBytes());
            //outputStream.flush();
            //outputStream.close();
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            long endTimeT = System.currentTimeMillis();
            long duration = endTimeT - startTimeT;
            System.out.println(duration + " ms Task " + i + " to execute.\n");
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println(duration + " ms to execute.");
    }
}
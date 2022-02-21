package ru.blueteam.service;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;

public class HttpPostService {

    static void sendJsonByUrl(String url, String json) throws IOException {

        Request.Post(url).bodyString(json, ContentType.APPLICATION_JSON).execute();
    }
}

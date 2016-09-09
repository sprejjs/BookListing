package com.spreys.booklisting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private List<String> authors;
    private String title;

    public Book(JSONObject json) {
        this.authors = new ArrayList<>();

        try {
            JSONObject volumeInfo = json.getJSONObject("volumeInfo");
            this.title = volumeInfo.getString("title");

            //Authors
            JSONArray authors = volumeInfo.getJSONArray("authors");

            for (int i = 0; i < authors.length(); i++) {
                this.authors.add(authors.get(i).toString());
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        if (authors.isEmpty()) {
            return "";
        }

        if (authors.size() == 1) {
            return authors.get(0);
        }

        String returnString = authors.get(0);

        for (int i = 1; i < authors.size(); i++) {
            returnString += ", " + authors.get(i);
        }

        return returnString;
    }
}

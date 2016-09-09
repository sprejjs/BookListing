package com.spreys.booklisting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BooksFactory {

    public static List<Book> GetBooksFromJson(JSONObject json) {

        ArrayList<Book> books = new ArrayList<>();

        try {
            JSONArray booksArray = json.getJSONArray("items");

            for (int i = 0; i < booksArray.length(); i++) {
                books.add(new Book((JSONObject)booksArray.get(i)));
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }

        return books;
    }
}

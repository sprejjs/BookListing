package com.spreys.booklisting;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BookListingActivity extends AppCompatActivity {
    @BindView(R.id.activity_book_listing_edit_text)
    EditText searchEditBox;

    @BindView(R.id.activity_book_listing_search_button)
    Button searchButton;

    @BindView(R.id.activity_book_listing_empty_view)
    TextView emptyView;

    @BindView(R.id.activity_book_listing_recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);

        ButterKnife.bind(this);

        emptyView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.activity_book_listing_search_button)
    public void retrieveBooks() {
        String searchInput = searchEditBox.getText().toString();

        new BooksRetrievalTask().execute(searchInput);
    }

    private class BooksRetrievalTask extends AsyncTask<String, Void, Void> {
        private static final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

        @Override
        protected Void doInBackground(String... strings) {
            String searchString = strings[0];

            URL url = null;

            try {
                url = new URL(GOOGLE_API_URL + searchString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            JSONObject result = NetworkUtils.GetJsonObjectFromUrl(url);
            return null;
        }
    }
}

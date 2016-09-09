package com.spreys.booklisting;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

public class BookListingActivity extends AppCompatActivity {
    @BindView(R.id.activity_book_listing_edit_text)
    EditText searchEditBox;

    @BindView(R.id.activity_book_listing_search_button)
    Button searchButton;

    @BindView(R.id.activity_book_listing_empty_view)
    TextView emptyView;

    @BindView(R.id.activity_book_listing_recycler_view)
    RecyclerView recyclerView;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_listing);

        ButterKnife.bind(this);

        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching...");
    }

    @OnClick(R.id.activity_book_listing_search_button)
    public void retrieveBooks() {
        String searchInput = searchEditBox.getText().toString();

        //Start the search
        new BooksRetrievalTask(this).execute(searchInput);

        //Hide the keyboard
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private class BooksRetrievalTask extends AsyncTask<Object, Object, List<Book>> {
        private static final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
        private final Context context;

        public BooksRetrievalTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected List<Book> doInBackground(Object... strings) {
            Object searchString = strings[0];

            URL url = null;

            try {
                url = new URL((GOOGLE_API_URL + searchString).replaceAll(" ", "%20"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return BooksFactory.GetBooksFromJson(NetworkUtils.GetJsonObjectFromUrl(url));
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            super.onPostExecute(books);
            progressDialog.dismiss();

            if (!books.isEmpty()) {
                emptyView.setVisibility(GONE);
                recyclerView.setAdapter(new BooksAdapter(books, context));
            }

        }
    }

    class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
        private List<Book> books;
        private Context context;

        public BooksAdapter(List<Book> books, Context context) {
            this.books = books;
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.book_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Book book = books.get(position);

            holder.title.setText(book.getTitle());
            holder.authors.setText(book.getAuthors());
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.book_item_authors_textview)
            public TextView authors;

            @BindView(R.id.book_item_title_textview)
            public TextView title;

            public ViewHolder(View view){
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}

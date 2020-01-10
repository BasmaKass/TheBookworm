package projet.version.thebookworm;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncSearch extends AsyncTask<String,Void,String> {

    private EditText Book;

    private TextView Title, Author;

    private AppCompatActivity myActivity;

    AsyncSearch(AppCompatActivity activity) {
        myActivity = activity;
    }


    private static final String LOG_TAG = AsyncSearch.class.getSimpleName();





    @Override
    protected String doInBackground(String... params) {

        // Get the search string
        String queryString = params[0];


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        // Attempt to query the Books API.
        try {
            // Base URI for the Books API.
            final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";

            final String QUERY_PARAM = "q"; // Parameter for the search string.
            final String MAX_RESULTS = "maxResults"; // Parameter that limits search results.
            final String PRINT_TYPE = "printType"; // Parameter to filter by print type.


            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "20")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            // Open the network connection.
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();


            StringBuilder builder = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                builder.append(line + "\n");
            }

            if (builder.length() == 0) {

                return null;
            }
            bookJSONString = builder.toString();

            // Catch errors.
        } catch (IOException e) {
            e.printStackTrace();


        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        // Return the raw response.
        return bookJSONString;
    }

    /**
     * Handles the results on the UI thread. Gets the information from
     * the JSON and updates the Views.
     *
     * @param s Result from the doInBackground method containing the raw JSON response,
     *          or null if it failed.
     */

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);



        ListView list = myActivity.findViewById(R.id.listbook);
        ArrayAdapter<String> fieldsAdapter = new ArrayAdapter<>(
                list.getContext(),
                R.layout.activity_parcourir,
                R.id.textView
        );
        list.setAdapter(fieldsAdapter);
        try {
            JSONObject jsonObject = new JSONObject(s);


            JSONArray itemsArray = jsonObject.getJSONArray("items");


            for (int i = 0; i < itemsArray.length(); i++) {
                try {
                    JSONObject book = itemsArray.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    JSONObject saleInfo = book.getJSONObject("saleInfo");
                    String title = volumeInfo.getString("title");
                    String description = volumeInfo.getString("description");
                    String authors =volumeInfo.getString("authors");
                    String ebook = saleInfo.getString("isEbook");

                    fieldsAdapter.add("\n" +
                            "title : " + title + "\n" +
                            "authors: " + authors +"\n" +
                            "description: " + description + "\n\n" +
                            "Ebook Disponible :" + ebook + "\n"
                            );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

package projet.version.thebookworm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MesLivres extends AppCompatActivity {

    LibraryDatabase mydata;
    private ListView mListView;
    private Button retour, ajouter;
    private final String TAG = "MesLivres";
    private String auteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_livres);

        mydata = new LibraryDatabase(this);
        retour = (Button) findViewById(R.id.button7);
        ajouter = (Button) findViewById(R.id.button8);
        mListView = (ListView) findViewById(R.id.listlivres);

        retour.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                Intent Retour = new Intent(MesLivres.this, MainActivity.class);
                startActivity(Retour);
            }
        });

        ajouter.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                Intent add = new Intent(MesLivres.this, AddBook.class);
                startActivity(add);
            }
        });

        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mydata.getBook();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = mydata.getBookID(name); //get the id associated with that name

                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                    auteur = data.getString(2);
                }
                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(MesLivres.this, bibliotheque.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("title", name);
                    editScreenIntent.putExtra("auteur", auteur);
                    startActivity(editScreenIntent);
                } else {
                    toastMessage("Information introuvable");
                }
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

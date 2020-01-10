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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class bibliotheque extends AppCompatActivity {

    public final String TAG = "bibliotheque";
    LibraryDatabase mydata;
    private Button retour;
    private Button delete;
    private Button addbook;
    private int selectedID;
    private String selectedName;
    private TextView title;
    private ListView listelivre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotheque);
        mydata = new LibraryDatabase(this);

        title = (TextView) findViewById(R.id.textView3);

        delete = (Button) findViewById(R.id.supp);

        retour = (Button) findViewById(R.id.Retour);

        listelivre = (ListView) findViewById(R.id.listelivres);

        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");
        title.setText(selectedName);

        addbook = (Button) findViewById(R.id.button4);

        addbook.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                Intent Ajout = new Intent(bibliotheque.this, AddBook.class);
                startActivity(Ajout);
                                        }
                                    }
        );


        retour.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                Intent Retour = new Intent(bibliotheque.this, Mylibrary.class);
                startActivity(Retour);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydata.deleteName(selectedID, selectedName.toString());

                toastMessage("removed from database");

                Intent suppression = new Intent (bibliotheque.this, Mylibrary.class);
                startActivity(suppression);
            }
        });

        populateListView(selectedName);

    }

    private void populateListView(String Library) {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mydata.getBookLibrary(Library);
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(0));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listelivre.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        listelivre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = mydata.getBookID(name); //get the id associated with that name
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }
                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(bibliotheque.this, mybook.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("name", name);
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

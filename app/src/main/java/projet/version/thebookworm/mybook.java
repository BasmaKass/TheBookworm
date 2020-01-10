package projet.version.thebookworm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class mybook extends AppCompatActivity {

    private Button delete, retour;
    LibraryDatabase mydata;
    private String selectedName, selectedAuthor, selectedLibrary;
    private int selectedID;
    private TextView text, text2;
    private String Auteur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybook);
        mydata = new LibraryDatabase(this);

        delete = (Button) findViewById(R.id.button11);
        retour = (Button) findViewById(R.id.button10);
        text = (TextView) findViewById(R.id.textView9);
        text2 = (TextView) findViewById(R.id.textView10);



        retour.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                Intent revenir = new Intent(mybook.this, MesLivres.class);
                startActivity(revenir);
            }
        });

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1);
        selectedName = receivedIntent.getStringExtra("name");
        Auteur = receivedIntent.getStringExtra("auteur");
        text.setText(selectedName);
        text2.setText(Auteur);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydata.deleteName(selectedID, selectedName.toString());

                toastMessage("removed from database");

                Intent suppression = new Intent (mybook.this, MesLivres.class);
                startActivity(suppression);
            }
        });
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

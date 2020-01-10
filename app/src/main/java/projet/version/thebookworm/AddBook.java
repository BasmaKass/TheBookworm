package projet.version.thebookworm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddBook extends AppCompatActivity {

    private Button valider, annuler;
    private EditText titre, auteur, libraire;
    LibraryDatabase mydata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        valider = (Button) findViewById(R.id.button5);
        annuler = (Button) findViewById(R.id.button6);
        titre = (EditText) findViewById(R.id.editText2);
        auteur = (EditText) findViewById(R.id.editText3);
        libraire = (EditText) findViewById(R.id.editText4);
        mydata = new LibraryDatabase(this);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newtitle = titre.getText().toString();
                String newauteur = auteur.getText().toString();
                String newlibrary = libraire.getText().toString();
                if (titre.length() != 0) {
                    AddBook(newtitle, newauteur, newlibrary);
                    titre.setText("");
                    auteur.setText("");
                    libraire.setText("");

                } else {
                    toastMessage("Il faut d'abord remplir les champs !");
                }

                Intent intent = new Intent(AddBook.this, Mylibrary.class);
                startActivity(intent);

            }
        });



    }

    public void AddBook(String newtitle, String newauteur, String newlibrary) {
        boolean insertData = mydata.addBook(newtitle, newauteur, newlibrary);

        if (insertData) {
            toastMessage("Votre livre a été ajouté");
        } else {
            toastMessage("Oops, Nous avons eu un problème...");
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

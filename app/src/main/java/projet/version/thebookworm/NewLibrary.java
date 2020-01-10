package projet.version.thebookworm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewLibrary extends AppCompatActivity {

    EditText editName;
    Button validbtn;
    Button cancelbtn;
    LibraryDatabase mydata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_library);
        mydata = new LibraryDatabase(this);

        editName = (EditText) findViewById(R.id.Nametext);
        validbtn = (Button) findViewById(R.id.valide);
        cancelbtn = (Button) findViewById(R.id.cancel);

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent annuler = new Intent(NewLibrary.this, Mylibrary.class);
            }
        });

        validbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editName.getText().toString();
                if (editName.length() != 0) {
                    AddData(newEntry);
                    editName.setText("");
                } else {
                    toastMessage("Il faut d'abord remplir les champs !");
                }

                Intent intent = new Intent(NewLibrary.this, Mylibrary.class);
                startActivity(intent);

            }
        });

    }

    public void AddData(String newEntry) {
        boolean insertData = mydata.addData(newEntry);

        if (insertData) {
            toastMessage("Votre bibliothèque a été ajoutée");
        } else {
            toastMessage("Oops, Nous avons eu un problème...");
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

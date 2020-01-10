package projet.version.thebookworm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mParcourir;

    private Button mlibrary;

    private Button mbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mParcourir = (Button) findViewById(R.id.button2);
        mlibrary = (Button) findViewById(R.id.button3);
        mbook = (Button) findViewById(R.id.button9);

        mParcourir.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent Parcourir = new Intent(MainActivity.this, Parcourir.class);
                startActivity(Parcourir);
            }
        });

        mlibrary.setOnClickListener( new View.OnClickListener() {

            public void onClick(View v) {
                Intent library = new Intent(MainActivity.this, Mylibrary.class);
                startActivity(library); }
                                     }

        );

        mbook.setOnClickListener( new View.OnClickListener() {

                                         public void onClick(View v) { Intent books = new Intent(MainActivity.this, MesLivres.class);
                                             startActivity(books); }
                                     }

        );


    }
}

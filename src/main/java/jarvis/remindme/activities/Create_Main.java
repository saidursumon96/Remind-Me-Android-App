package jarvis.remindme.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jarvis.remindme.R;
import jarvis.remindme.database.DbHelper;

public class Create_Main extends AppCompatActivity {

    DbHelper myDB;
    Button btn_create;
    EditText edittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__main);

        myDB = new DbHelper(this); //*** Remember my database - DbHelper class.

        edittext = (EditText)findViewById(R.id.editText);
        btn_create = (Button)findViewById(R.id.button_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = edittext.getText().toString(); //*** getText method of EditText return a object of editable but not string
                                                                 //*** To retrieve the text of Edittext toString() of editable can be used.

                if (edittext.length() != 0){ //*** Check point for not null input.
                    AddData(newEntry); //*** Call AddData method and pass the newEntry string from edittext.
                    edittext.setText(""); //*** Set edittext to blank.
                    finishActivityFromChild(getParent(),1); //*** Back from activity and restart MainActivity.
                }
                else {
                    Toast.makeText(getApplicationContext(),"Input Something !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void AddData(String newEntry){
        boolean insertData = myDB.addData(newEntry); //*** Call addData method from DbHelper class.

        if (insertData == true){
            Toast.makeText(getApplicationContext(),"Data Inserted.",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),"Data Not Inserted !",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

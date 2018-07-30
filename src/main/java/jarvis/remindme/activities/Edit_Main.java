package jarvis.remindme.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jarvis.remindme.R;
import jarvis.remindme.database.DbHelper;

public class Edit_Main extends AppCompatActivity {

    DbHelper myDB;
    private Button btn_update,btn_delete;
    private EditText edittext_item;
    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__main);

        btn_update =(Button)findViewById(R.id.button_update);
        btn_delete =(Button)findViewById(R.id.button_delete);
        edittext_item =(EditText) findViewById(R.id.edittext_update);

        myDB = new DbHelper(this); //*** Remember my database - DbHelper class.

        Intent receivedIntent = getIntent(); //*** Get the intent extra from the MainActivity.
                                            //*** Intent can be used with startActivity to launch an Activity.
                                             //*** getIntent method return the intent that started this activity.
        selectedID = receivedIntent.getIntExtra("id",-1); //*** Get the itemID that was passed as an extra.
                                                        //*** -1 for default value.
                                                        //*** getIntExtra - retrieve extended data from the intent.
        selectedName = receivedIntent.getStringExtra("ITEM1"); //*** Get the item1 that was passed as an extra.
        edittext_item.setText(selectedName); //*** Set the text to show current selected item1.

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = edittext_item.getText().toString(); //Using getText-toString to get the value.
                if(!item.equals("")){
                    myDB.update(item,selectedID,selectedName); //Call DbHelper update method & update data using receiveIntent.
                    finishActivityFromChild(getParent(),1); ////Return to MainActivity after insert an item.
                }else{
                    Toast.makeText(getApplicationContext(),"Input Data !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDB.delete(selectedID,selectedName); //Call DbHelper delete method and pass the name & id
                edittext_item.setText(""); //Reset text to blank(blank means nothing)
                finishActivityFromChild(getParent(),1); //Return to MainActivity after delete an item.
                Toast.makeText(getApplicationContext(),"Data Deleted.",Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

package jarvis.remindme.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import jarvis.remindme.R;

public class Feedback_Main extends AppCompatActivity { //*** AppCompatActivity is from the appcompat-v7 library.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //*** Call the super class onCreate to complete the creation of activity.
        setContentView(R.layout.activity_feedback__main); //*** call setContentView(int) with a layout resource defining my UI.

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void btnClick(View v){
        Intent i = new Intent(Intent.ACTION_SEND); //*** String ACTION_SEND - Deliver some data to someone else.
        i.setData(Uri.parse("email")); //*** setData () - sets the value for the target member on the Message.
        //i.setPackage("com.google.android.gm");
        //i.setPackage("com.android.email");
        String[] s = {"saidursumon96@gmail.com"}; // Recipients

        i.putExtra(Intent.EXTRA_EMAIL, s); //*** A String[] holding e-mail addresses that should be delivered to.
        i.putExtra(Intent.EXTRA_SUBJECT,"Remind Me : Feedback"); //*** A constant string holding the desired subject line of a message.
        i.putExtra(Intent.EXTRA_TEXT,"Hi, i am ....");  //*** A constant CharSequence that is associated with the Intent
                                                        //         used with ACTION_SEND to supply the literal data to be sent.
        i.setType("message/rfc822"); // The intent does not have a URI, so declare the "text/plain" MIME type.
        Intent chosser = Intent.createChooser(i,"Open Email"); //*** Intent.createChooser(), passing Intent object and it returns
                                                               //        a version of intent that will always display the chooser.
        startActivity(chosser);
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

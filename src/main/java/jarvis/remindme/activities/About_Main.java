package jarvis.remindme.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import jarvis.remindme.R;

public class About_Main extends AppCompatActivity { //*** AppCompatActivity is from the appcompat-v7 library.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //*** Call the super class onCreate to complete the creation of activity.
        setContentView(R.layout.activity_about__main); //*** call setContentView(int) with a layout resource defining my UI.

        getSupportActionBar().setDisplayShowHomeEnabled(true); //*** setDisplayShowHomeEnabled() - This method just controls whether
                                                               //                               to show the Activity icon/logo or not.
                                                               //*** getSupportActionBar() - Retrieve an instance of ActionBar object
                                                               //               by calling getSupportActionBar() method
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //*** setDisplayHomeAsUpEnabled() - This method makes the icon and title
                                                               //                                           in the action bar clickable.
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){  //*** When the user selects an item from the options menu ,
                                                          //        the system calls my activity's onOptionsItemSelected() method.
        int id = item.getItemId();      //*** getItemId - Return the identifier for this menu item.

        if (id == android.R.id.home){
            this.finish(); //*** When calling finish() on an activity, the method onDestroy() is executed and
                           // this method can do things like - dismiss a dialog.
        }
        return super.onOptionsItemSelected(item); //*** When successfully handle a menu item, return true
                                                  //       If don't handle the menu item,should be call the super class
                                                  //          implementation of onOptionsItemSelected().
    }

    public void facebook(View v){
        Intent i = new Intent(Intent.ACTION_VIEW); //*** An intent allows to start an activity in
                                                   //       another app by describing a simple Action: ACTION_VIEW.
        i.setData(Uri.parse("https://www.facebook.com/saidur.r.s")); //*** setData () - sets the value for the target member on the Message.
        //i.setData(Uri.parse("fb://saidur.r.s"));
        startActivity(i);
    }

    public void twitter(View v){ //*** twitter method worked for onClick when a view has been clicked.
        Intent i = new Intent(Intent.ACTION_VIEW); //*** An intent is an abstract description of an operation to be performed.
                                                   //                   It can be used with startActivity to launch an Activity.
        i.setData(Uri.parse("https://twitter.com/saidursumon96"));
        startActivity(i);
    }

    public void googleplus(View v){ //*** Parameters - v, View : The view that was clicked.
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://plus.google.com/113986730825346701955"));
        startActivity(i); //***
    }

    public void youtube(View v){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://youtube.com/user/saidursumon96")); //*** A URI reference includes a URI and a fragment.
        startActivity(i); //*** The startActivity() method starts an instance of the DisplayMessageActivity specified by the Intent.
    }
}
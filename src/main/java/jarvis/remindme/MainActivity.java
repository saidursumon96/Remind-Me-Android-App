package jarvis.remindme;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import jarvis.remindme.activities.About_Main;
import jarvis.remindme.activities.Alarm_Main;
import jarvis.remindme.activities.Create_Main;
import jarvis.remindme.activities.Edit_Main;
import jarvis.remindme.activities.Feedback_Main;
import jarvis.remindme.activities.Settings_Main;
import jarvis.remindme.database.DbHelper;

public class MainActivity extends AppCompatActivity {

    DbHelper myDB;
    Button btn_feedback,search_item;
    FloatingActionButton btn_fab;
    private String selectedName;
    private int selectedID;
    Cursor data;
    ArrayAdapter<String> adapter;
    ArrayList<String> theList;
    private boolean isUserClickedBackButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DbHelper(this);
        final ListView listView = (ListView)findViewById(R.id.listview_main); //*** Using findViewById(int) to retrieve the widgets from UI
        search_item = (Button)findViewById(R.id.search);

        myPreference();
        registerForContextMenu(listView);

        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1);
        selectedName = receivedIntent.getStringExtra("item");

        theList = new ArrayList<>(); //** Get the data and append to a list.
        data = myDB.getListContents(); //*** Cursor can get all content from database.

        if (data.getCount() == 0){ //*** Returns the numbers of rows in the cursor.
            Toast.makeText(getApplicationContext(),"Data Empty !",Toast.LENGTH_SHORT).show();
        }
        else {
            while (data.moveToNext()){ //*** Get the value from the database in column 1 and add it to the ArrayList.
                theList.add(data.getString(1)); // 1 reference the column number that is ITEM1 in database.
                adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,theList); //*** Create list adapter and set adapter.
                listView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                        String item1 = parent.getItemAtPosition(position).toString();  //*** getItemAtPosition will return an object.
                        //Toast.makeText(getApplicationContext(),"You Select - " + theList.get(position),Toast.LENGTH_SHORT).show();

                        Cursor data = myDB.getItemID(item1); //*** Declare Cursor object & return the data and pass the item1 string.
                                                            //*** Get the id associated with that item1.
                        int itemID = -1;
                        while(data.moveToNext()){ //*** moveToNext method perform to pass the name & return the ID.
                            itemID = data.getInt(0);
                        }
                        if(itemID > -1){ //*** Handle the error if item already deleted. // not needed
                            Intent editScreenIntent = new Intent(getApplicationContext(), Edit_Main.class); //*** Take edit_update activity.
                            editScreenIntent.putExtra("id",itemID);
                            editScreenIntent.putExtra("ITEM1",item1);
                            startActivity(editScreenIntent);
                            //adapter.notifyDataSetChanged();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Item Already Deleted !",Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        }

        btn_fab = (FloatingActionButton)findViewById(R.id.fab);
        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Create_Main.class));
            }
        });
    }

    protected void myPreference(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean dark_background = preferences.getBoolean("background_color", false);

        if (dark_background){
            RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.content_main_id);
            relativeLayout.setBackgroundColor(Color.parseColor("#FFC6C4C4"));
        }
        String head_title = preferences.getString("example_text","Remind Me");
        setTitle(head_title);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);

        super.onCreateContextMenu(menu,v,menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.context_open:
                return true;

            case R.id.context_edit:
                Intent editScreenIntent2 = new Intent(getApplicationContext(), Edit_Main.class);
                startActivity(editScreenIntent2);
                return true;

            case R.id.context_delete:
                myDB.delete(selectedID,selectedName);
                theList.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;

            default:
                super.onContextItemSelected(item);
        }
        return true;
    }

    public void onBackPressed() { //*** Called when the activity has detected the user's press of the back key.

        if (!isUserClickedBackButton){
            Toast.makeText(this,"Press Back Again To Exit.", Toast.LENGTH_SHORT).show();
            isUserClickedBackButton = true;
        }
        else {
            super.onBackPressed();
        }
        new CountDownTimer(3000, 1000) { //*** Schedule a countdown until a time in the future with
                                        // regular notifications on intervals along the way.
            @Override
            public void onTick(long millisUntilFinished) { //*** Callback fired on regular interval.
            }
            @Override
            public void onFinish() {
                isUserClickedBackButton = false;
            }
        }.start(); //*** Start the countdown.
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(final Menu menu) { //*** This method can inflate the menu resource (defined in XML)
                                                        // into the Menu provided in the callback.
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu); //*** Inflate a menu resource providing context menu items.

        final MenuItem item = menu.findItem(R.id.search); //*** Return the menu item with a particular identifier.
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnSearchClickListener(new View.OnClickListener() { //*** Sets a listener to inform when the search button is pressed.
            @Override
            public void onClick(View v) {
                setItemVisibility(menu, item, false); //*** Hide the option menu.
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() { //*** Sets a listener to inform when the user closes the SearchView.
            @Override
            public boolean onClose() {
                setItemVisibility(menu, item, true); //*** Show the option menu.
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { //*** Sets a listener for user actions within the SearchView.
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { //*** Called when the query text is changed by the user.

                if (data.getCount() == 0){ //*** Returns the numbers of rows in the cursor.
                    Toast.makeText(getApplicationContext(),"List Empty !",Toast.LENGTH_SHORT).show();
                }
                else {
                    adapter.getFilter().filter(newText); //*** Returns a filter that can be used to constrain data with a filtering pattern.
                }
                return false;
            }
        });
        return true;
    }

    private  void setItemVisibility(Menu menu, MenuItem exception, boolean visible){
        for (int i=0; i<menu.size(); ++i){
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //*** When the user selects an item from the options menu.
                                                            //*** An Item is returned by calling one of the add(int) methods.
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId(); //getItemId method return the identifier for this menu item.

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, Settings_Main.class));
            return true;
        }
        else if (id == R.id.action_feedback) {
            startActivity(new Intent(MainActivity.this, Feedback_Main.class));
            return true;
        }
        else if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, About_Main.class));
            return true;
        }
        else if (id == R.id.action_alarm) {
            startActivity(new Intent(MainActivity.this, Alarm_Main.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
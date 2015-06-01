package ebook.ken.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ebook.ken.dao.Database;
import ebook.ken.fragment.BookStoreFragment;
import ebook.ken.fragment.FavoritesFragment;
import ebook.ken.fragment.HomeFragment;
import ebook.ken.utils.FileHandler;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;


public class MainActivity extends MaterialNavigationDrawer {


    private SharedPreferences prefs = null;
    private Database database;


    ////////////////////////////////////////////////////////////////////////////////
    // Todo activity life cycle

    @Override
    public void init(Bundle bundle) {

        try {
            // set the header image
            this.setDrawerHeaderImage(R.drawable.mat3);

            // set section
            this.addSection(newSection("Home", R.drawable.ic_action_back_white, new HomeFragment()));
            this.addSection(newSection("Favorites", R.drawable.ic_action_back_white, new FavoritesFragment()));
            this.addSection(newSection("Book Store", R.drawable.ic_action_back_white, new BookStoreFragment()));

            // get shared preferences to create app folder
            prefs = getSharedPreferences("ebook.ken.activity", MODE_PRIVATE);

        } catch (Exception ex){
            Log.d(">>> ken <<<", Log.getStackTraceString(ex));
        }
    }// end-func init


    @Override
    protected void onStart() {
        super.onStart();
        try {
            this.setHomeAsUpIndicator(R.drawable.ic_action_back_white);

            // check is first run app to create app folder
            if (prefs.getBoolean("firstRun", true)) {

                // create root folder
                FileHandler.createRootFolder();

                // create database
                database = new Database(this);

                // put first run
                prefs.edit().putBoolean("firstRun", false).commit();
            }// end-if

        } catch (Exception ex){
            Log.d(">>> ken <<<", Log.getStackTraceString(ex));
        }
    }// end-func onStart


    @Override
    public void onHomeAsUpSelected() {

        // when the back arrow is selected this method is called

    }// end-func onHomeAsUpSelected


    ////////////////////////////////////////////////////////////////////////////////
    // Todo function inner



    ////////////////////////////////////////////////////////////////////////////////
    // Todo option menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }// end-func onCreateOptionsMenu


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }// end-if

        return super.onOptionsItemSelected(item);

    }// end-func onOptionsItemSelected


    ////////////////////////////////////////////////////////////////////////////////
    // Todo anything else



}

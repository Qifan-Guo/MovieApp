package com.qifan.movieapp;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import com.qifan.movieapp.Beans.MovieObj;
import com.qifan.movieapp.Fragment.MainFragment;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private ArrayList<MovieObj> list = new ArrayList<>();
    private MainFragment MainFragment;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    public final String popularity="popular";
    public final String topRate="top_rated";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment =new MainFragment();
       fragmentTransaction =getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.mainFragment, MainFragment,"").commit();



    }

    public static URL sortURL(String sortBy){
        URL url=null;
        if(sortBy== MovieInfo.popularity_desc){
            url= MovieInfo.buildURL(MovieInfo.popularity_desc);

        }else
            if(sortBy== MovieInfo.topRate){
            url= MovieInfo.buildURL(MovieInfo.topRate);
            }
            return url;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.movie_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String sortBy="";
        Bundle bundle=new Bundle();
        android.support.v4.app.FragmentTransaction fragmentTransaction1=getSupportFragmentManager().beginTransaction();



        switch (id){
            case R.id.mostPopularSort:
                sortBy=popularity;
                bundle.putString("sortBy",sortBy);
                fragmentTransaction1.detach(MainFragment);
                MainFragment.setArguments(bundle);
                fragmentTransaction1.attach(MainFragment);
                fragmentTransaction1.commit();



                Log.d("debugg","popularity option selected");
                break;
            case R.id.topRatedSort:
                sortBy=topRate;
                bundle.putString("sortBy",sortBy);
                fragmentTransaction1.detach(MainFragment);
                MainFragment.setArguments(bundle);
                fragmentTransaction1.attach(MainFragment);
                fragmentTransaction1.commit();

                Log.d("debugg","TopRate option selected");
               break;
        }
        return true;
    }
}

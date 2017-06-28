package exemplo.restcliente;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import exemplo.restcliente.fragments.MinhasProvasFragment;
import exemplo.restcliente.fragments.SobreFragment;
import exemplo.restcliente.fragments.Opcao3Fragment;
import exemplo.restcliente.fragments.WelcomeFragment;

public class MenuProvaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_desliz);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //fixa o layout vertical
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //seta o fragment inicial
        replaceFragment(new WelcomeFragment());
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        replaceFragment(new WelcomeFragment());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //evento do menu  no  canto superiror direito
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case (R.id.action_sobre):
                SobreFragment op2= new SobreFragment();
                replaceFragment(op2);
                break;
            case (R.id.action_ajuda):
                Toast.makeText(getApplicationContext(), "Teste!", Toast.LENGTH_LONG).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickZero(View v){
            Intent ii = new Intent(getBaseContext(),ManipularProvaActivity.class);
            ii.putExtra("ID",0);
            startActivity(ii);
        }
    public void onClickUm(View v){
        Intent ii = new Intent(getBaseContext(),ManipularProvaActivity.class);
        ii.putExtra("ID",1);
        startActivity(ii);
    }
    public void onClickDois(View v){
        Intent ii = new Intent(getBaseContext(),ManipularProvaActivity.class);
        ii.putExtra("ID",2);
        startActivity(ii);
    }
    public void onClickTres(View v){
        Intent ii = new Intent(getBaseContext(),ManipularProvaActivity.class);
        ii.putExtra("ID",3);
        startActivity(ii);
    }
    public void onClickQuatro(View v){
        Intent ii = new Intent(getBaseContext(),ManipularProvaActivity.class);
        ii.putExtra("ID",4);
        startActivity(ii);
    }
    public void onClickCinco(View v){
        Intent ii = new Intent(getBaseContext(),ManipularProvaActivity.class);
        ii.putExtra("ID",5);
        startActivity(ii);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_opcao1:
                MinhasProvasFragment op1= new MinhasProvasFragment();
                replaceFragment(op1);
                //Toast.makeText(getApplicationContext(), "Teste1!", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_opcao3:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com")));
               break;
            case R.id.nav_sair:
                startActivity(new Intent(getBaseContext(),PrincipalActivity.class));
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragments, fragment, "TAG").addToBackStack(null).commit();
    }
}

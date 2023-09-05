package com.prem.pavikaa;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prem.pavikaa.Ads.Admob;

import java.util.ArrayList;
import java.util.List;

import eu.dkaratzas.android.inapp.update.Constants;
import eu.dkaratzas.android.inapp.update.InAppUpdateManager;
import eu.dkaratzas.android.inapp.update.InAppUpdateStatus;

public class MainActivity extends AppCompatActivity implements InAppUpdateManager.InAppUpdateHandler {
    private RecyclerView subjectRecycler;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private InAppUpdateManager inAppUpdateManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //  Here load the Google Ads and anywhere showing
        Admob.loadInterstitialsAds(MainActivity.this);

        // DrawerLayout Method
        DrawerLayoutMethod();

        // Android 13 Permission
        NotificationPerm13();

        // Navigation Item with Click Listener
        NavigationItem();

        // In App Update
        AppUpdateMethod();

        Admob.setBanner(findViewById(R.id.banner_ads), MainActivity.this);
        FirebaseMessaging.getInstance().subscribeToTopic("notification");

        subjectRecycler = findViewById(R.id.subjectRecycler);
        List<SubjectModel> subjectModelList = new ArrayList<>();

        subjectModelList.add(new SubjectModel(R.drawable.geography, "Geography", "Geography.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.history, "History", "History.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.artandculture, "Art and Culture", "Art and Culture.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.polity, "Polity", "Polity.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.economic, "Economy", "Economy.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.environandecology, "Environment and Ecology", "Environment and Ecology.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.generalscience, "General Science", "General Science.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.scinceandtech, "Science and Technology", "Science and Technology.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.constitution, "Constitution of India", "constitution.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.history_backgroun, "Historical Background", "Historical Background.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.making_constituation, "Making of the Constitution", "Making of the Constitution.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.bhudha, "Buddhism Dharma", "buddha.pdf"));
        subjectModelList.add(new SubjectModel(R.drawable.jain, "Jain Dharma", "jain.pdf"));

        subjectRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        subjectRecycler.setAdapter(new SubjectRecyclerviewAdapter(MainActivity.this, subjectModelList));

    }
    public void DrawerLayoutMethod(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_bar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void NotificationPerm13() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            int permissionNotif = ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS);
            if(permissionNotif != PackageManager.PERMISSION_GRANTED){
                String[] NOTIF_PERM = {POST_NOTIFICATIONS};
                ActivityCompat.requestPermissions(this, NOTIF_PERM,100);
            }
        }
    }
    // Navigation Items ClickListener
    public void NavigationItem(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.theme){
                    Toast.makeText(MainActivity.this, "No theme available!!!", Toast.LENGTH_SHORT).show();
                } else if (id== R.id.share_app) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Share this app");
                        intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                        startActivity(Intent.createChooser(intent, "Share with"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (id == R.id.rateUs) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    try {
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, "Unable to open\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else if (id == R.id.linkedin) {
                    Uri uri = Uri.parse("https://www.linkedin.com/in/priyanshu-gond-488178287/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Unable to open\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else if (id == R.id.youtube) {
                    Uri uri = Uri.parse("https://www.youtube.com/channel/UCwXjagAf23OSzU-yCypzuJQ");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Unable to open\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else if (id == R.id.privacy_policy) {
                    Uri uri = Uri.parse("https://sites.google.com/d/1xEAEN_mSW8aXIOEzzjtPxd5GW4ZTFDrp/p/1Aiu_rqH2fm3AZdpTauRw4gY6PtolGilU/edit");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Unable to open\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                return false;
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // App Update Method
    public void AppUpdateMethod(){
        inAppUpdateManager = InAppUpdateManager.Builder(this, 101)
                .resumeUpdates(true)
                .mode(Constants.UpdateMode.FLEXIBLE)
                .snackBarAction("An update has just been downloaded!!!")
                .snackBarAction("RESTART")
                .handler(this);
        inAppUpdateManager.checkForAppUpdate();
    }
    @Override
    public void onInAppUpdateError(int code, Throwable error) {

    }

    @Override
    public void onInAppUpdateStatus(InAppUpdateStatus status) {
        if(status.isDownloaded()){
            View view =getWindow().getDecorView().findViewById(android.R.id.content);
            Snackbar snackbar= Snackbar.make(view,
                    "An updates just been downloaded!!!",
                    Snackbar.LENGTH_INDEFINITE);

            snackbar.setAction("", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inAppUpdateManager.completeUpdate();
                }
            });
            snackbar.show();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit")
                    .setMessage("Are you sure you want to Exit?")
                    .setNegativeButton("No",null)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
                        }
                    }).show();
        }
    }

}
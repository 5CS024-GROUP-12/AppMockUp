package uk.ac.wlv.nhs;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class RoomDisplay extends AppCompatActivity {

    private static final String TAG = "RoomDisplay";

    SQLiteDatabase SQLDB;
    DatabaseHelper db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_display);
        Log.d(TAG, "onCreate: Room Display Started");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intent");
        if(getIntent().hasExtra("image_url")&&getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intets extra");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");

            setPage(imageUrl,imageName);
        }
    }

    private void setPage(String imageUrl, String imageName){
        Log.d(TAG, "setImage: setting image and name");

        TextView name = findViewById(R.id.RoomName);
        name.setText(imageName);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void confirmBooking(View view){

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String today = dateFormat.format(date);

        TextView textView = findViewById(R.id.RoomName);
        String name = (String) textView.getText();

        db.addBooking(db.getRoomId(name),today);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}

package lab.fk.anappoficeandfire.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarRecord;

import lab.fk.anappoficeandfire.R;
import lab.fk.anappoficeandfire.client.IceAndFireClient;
import lab.fk.anappoficeandfire.database.DataBaseActivity;
import lab.fk.anappoficeandfire.model.Book;
import lab.fk.anappoficeandfire.model.Character;
import lab.fk.anappoficeandfire.model.House;
import lab.fk.anappoficeandfire.utils.ViewUtils;

public class HomeActivity extends AppCompatActivity {

    private HomeDataHandler dataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setViewListeners();
        init();
    }

    private void init() {
        dataHandler = new HomeDataHandler(this);
        setFooterValue();
    }

    private void setFooterValue() {
        TextView view = ViewUtils.getViewById(this, R.id.lbl_footer_home);
        view.setText(this.getString(R.string.lbl_last_update_home, dataHandler.getFormattedLastUpdateDate()));
    }

    private void setViewListeners() {
        Button btnDB = ViewUtils.getViewById(this, R.id.btn_database_home);
        btnDB.setOnClickListener(x -> {
            goToDB();
        });
        Button btnDisplay = ViewUtils.getViewById(this, R.id.btn_display_home);
        btnDisplay.setOnClickListener(x -> {
            Toast.makeText(HomeActivity.this,
                    String.format(
                            "Houses: %d\nBooks: %d\nCharacters: %d\n",
                            SugarRecord.count(House.class),
                            SugarRecord.count(Book.class),
                            SugarRecord.count(Character.class)),
                    Toast.LENGTH_SHORT).show();
        });
    }

    private void goToDB() {
        Intent intent = new Intent(this, DataBaseActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        init();
    }
}

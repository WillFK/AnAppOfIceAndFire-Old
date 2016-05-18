package lab.fk.anappoficeandfire.database;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import lab.fk.anappoficeandfire.R;
import lab.fk.anappoficeandfire.utils.ViewUtils;

public class DataBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setViewListeners();
    }


    private void setViewListeners() {
        //erase
        Button btnErase = ViewUtils.getViewById(this, R.id.btn_erase_db);
        btnErase.setOnClickListener(v -> DBHandler.erase());
        //populate mocks
        Button btnMocks = ViewUtils.getViewById(this, R.id.btn_populate_mocks_db);
        btnMocks.setOnClickListener(v -> DBHandler.populateWithMocks());
        //erase
        Button btnData = ViewUtils.getViewById(this, R.id.btn_populate_server_db);
        btnData.setOnClickListener(v -> DBHandler.populateWithData(null)); //TODO
    }

}

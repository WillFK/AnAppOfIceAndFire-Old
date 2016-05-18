package lab.fk.anappoficeandfire.database;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.orm.SugarRecord;

import lab.fk.anappoficeandfire.R;
import lab.fk.anappoficeandfire.client.IceAndFireClient;
import lab.fk.anappoficeandfire.model.Character;
import lab.fk.anappoficeandfire.model.House;
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
        btnData.setOnClickListener(v -> syncDataClient()); //TODO
    }

    private void syncDataClient() {
        new SyncQueryTask(ProgressDialog.show(this,
                this.getString(R.string.title_progress_sync),
                this.getString(R.string.txt_progress_sync))).execute();
    }

    private class SyncQueryTask extends AsyncTask<Void, Void, Exception> {

        private final ProgressDialog progressDialog;

        public SyncQueryTask(ProgressDialog progressDialog) {
            this.progressDialog = progressDialog;
        }

        @Override
        protected Exception doInBackground(Void... params) {
            Exception e = null;
            try {
                IceAndFireClient.loadEntities();
            } catch (Exception ex) {
                e = ex;
            }

            return e;
        }

        @Override
        protected void onPostExecute(Exception e) {
            if (e == null) {
                Toast.makeText(DataBaseActivity.this,
                        String.format("Houses: %d\nCharacters: %d\n",
                                SugarRecord.count(House.class),
                                SugarRecord.count(Character.class)),
                        Toast.LENGTH_SHORT).show();
            } else {
                Log.e("AOIF", e.getMessage(), e);
            }

            progressDialog.hide();
        }
    }

}

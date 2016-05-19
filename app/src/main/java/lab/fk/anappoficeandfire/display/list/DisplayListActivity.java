package lab.fk.anappoficeandfire.display.list;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.orm.SugarRecord;

import java.util.List;

import lab.fk.anappoficeandfire.R;
import lab.fk.anappoficeandfire.display.DisplayActivity;
import lab.fk.anappoficeandfire.display.FilterDisplayVO;
import lab.fk.anappoficeandfire.model.AbstractModel;
import lab.fk.anappoficeandfire.model.Book;
import lab.fk.anappoficeandfire.utils.ViewUtils;
import rx.Subscriber;

@SuppressWarnings("unchecked")
public class DisplayListActivity extends AppCompatActivity {

    private List<? extends AbstractModel> dataSet;
    private FilterDisplayVO filterDisplayVO;
    private ProgressDialog progressDialog;

    private FilterDisplayVO getFilterDisplayVO() {
        if (filterDisplayVO == null) {
            filterDisplayVO = (FilterDisplayVO) getIntent().getSerializableExtra("filter");
        }
        return filterDisplayVO;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list);
        setTitle(getFilterDisplayVO().dataType.toString());
        new LoadDataTask().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private class LoadDataTask extends AsyncTask<Void, Void, Exception> {

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(DisplayListActivity.this,
                    getString(R.string.txt_loading_data_display),
                    getString(R.string.txt_waiting_display));
        }

        @Override
        protected Exception doInBackground(Void... params) {
            Exception e = null;
            try {
                loadData();
            } catch (Exception ex) {
                e = ex;
            }
            return e;
        }

        @Override
        protected void onPostExecute(Exception e) {
            progressDialog.hide();

            if (e == null) {
                setupList();
            } else {
                Log.e("AOIF", e.getMessage(), e);
            }
        }
    }

    private void loadData() {

        //AbstractModel model = SugarRecord.findAll(getFilterDisplayVO().dataType.getModelClass()).next();
        Stream<AbstractModel> stream = Stream.of(SugarRecord.findAll(getFilterDisplayVO().dataType.getModelClass()));

        List<Predicate<AbstractModel>>
                filters = getFilterDisplayVO().dataType.getDisplayListViewHandler().getFilters(getFilterDisplayVO());

        //Predicate<AbstractModel> pred = model -> model.hashCode() == 0;

        for (Predicate<AbstractModel> filter  : filters) {
            stream = stream.filter(filter);
        }

        dataSet = stream.collect(Collectors.toList());
    }

    private void setupList() {
        RecyclerView recyclerView = ViewUtils.getViewById(this, R.id.rcy_container_display_list);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        recyclerView.setAdapter(new DisplayListAdapter(dataSet, getFilterDisplayVO().dataType));
    }
}

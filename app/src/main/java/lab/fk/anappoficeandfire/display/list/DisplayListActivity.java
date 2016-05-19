package lab.fk.anappoficeandfire.display.list;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lab.fk.anappoficeandfire.R;
import lab.fk.anappoficeandfire.display.FilterDisplayVO;
import lab.fk.anappoficeandfire.model.AbstractModel;
import lab.fk.anappoficeandfire.utils.ViewUtils;
import rx.Observable;
import rx.Subscriber;

@SuppressWarnings("unchecked")
public class DisplayListActivity extends AppCompatActivity {

    private List<AbstractModel> dataSet;
    private FilterDisplayVO filterDisplayVO;
    private ProgressDialog progressDialog;
    private DisplayListAdapter dataDisplayListAdapter;

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
        //loadDataRX();
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
            displayProgress();
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

    private void displayProgress() {
        progressDialog = ProgressDialog.show(DisplayListActivity.this,
                getString(R.string.txt_loading_data_display),
                getString(R.string.txt_waiting_display));
    }

    private void loadDataRX() {
        final Iterator<? extends AbstractModel> itModel = SugarRecord.findAll(getFilterDisplayVO().dataType.getModelClass());
        dataSet = new ArrayList<>();
        setupList();
        final Subscriber<AbstractModel> subs = new Subscriber<AbstractModel>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("AOIF", e.getMessage(), e);
            }

            @Override
            public void onNext(AbstractModel abstractModel) {
                dataSet.add(abstractModel);
                dataDisplayListAdapter.notifyDataSetChanged();
            }
        };


        Observable.create(new Observable.OnSubscribe<AbstractModel>() {
            @Override
            public void call(Subscriber<? super AbstractModel> subscriber) {
                Stream.of(itModel).forEach(model -> subscriber.onNext(model));
                subscriber.onCompleted();
            }
        }).subscribe(subs);
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

        recyclerView.setAdapter(dataDisplayListAdapter = new DisplayListAdapter(dataSet, getFilterDisplayVO().dataType));
    }
}

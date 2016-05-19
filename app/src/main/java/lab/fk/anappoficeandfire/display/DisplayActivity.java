package lab.fk.anappoficeandfire.display;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import lab.fk.anappoficeandfire.R;
import lab.fk.anappoficeandfire.datatype.EnumDataType;
import lab.fk.anappoficeandfire.display.list.DisplayListActivity;
import lab.fk.anappoficeandfire.display.viewhandler.AbstractFilterViewHandler;
import lab.fk.anappoficeandfire.utils.ViewUtils;

@SuppressWarnings("unchecked")
public class DisplayActivity extends AppCompatActivity {

    private FilterDisplayVO filterDisplayVO;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setViewConfig();
        //setupViewDisplay();
    }

    private Spinner getSpinnerDataType() {
        return ViewUtils.getViewById(this, R.id.spn_data_type_display);
    }
    private void setViewConfig() {
        getSpinnerDataType().setAdapter(new ArrayAdapter<>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                EnumDataType.values()));

        getSpinnerDataType().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setupViewDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });
        Button btnChooseDate = ViewUtils.getViewById(this, R.id.btn_choose_date_filter_display);
        btnChooseDate.setOnClickListener(v -> {
            DatePickerDialog dpd = getDatePickerDialog();
            dpd.show();
        });
        Button btnQueryData = ViewUtils.getViewById(this, R.id.btn_query_data_display);
        btnQueryData.setOnClickListener(v -> {
            //Copy the filter; lets pass ahead only the necessary!
            FilterDisplayVO filterDisplayVO = new FilterDisplayVO();
            loadToFilter(filterDisplayVO);
            if (filterDisplayVO.dataType.getViewHandler().useViewReleased() && getFilterDisplayVO().release != null) {
                filterDisplayVO.release = getFilterDisplayVO().release;
            }
            Intent intent = new Intent(this, DisplayListActivity.class);
            intent.putExtra("filter", filterDisplayVO);
            startActivity(intent);
        });
    }

    private DatePickerDialog getDatePickerDialog() {
        Calendar rel = getFilterDisplayVO().release;
        if (rel == null) {
            rel = Calendar.getInstance();
        }
        Calendar release = rel;
        datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            release.set(Calendar.YEAR, year);
            release.set(Calendar.MONTH, monthOfYear);
            release.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            getFilterDisplayVO().release = release;
            updateTextReleased();
        }, release.get(Calendar.YEAR), release.get(Calendar.MONTH), release.get(Calendar.DAY_OF_MONTH));

        return datePickerDialog;
    }

    private EnumDataType getSelectedDataType() {
        return ((EnumDataType) getSpinnerDataType().getSelectedItem());
    }

    private void setVisibility(int id, boolean visible) {
        this.findViewById(id).setVisibility(visible ? View.VISIBLE:View.GONE);
    }

    private void setupViewDisplay() {
        AbstractFilterViewHandler viewHandler = getSelectedDataType().getViewHandler();
        setVisibility(R.id.lyt_wrapper_name_display, viewHandler.useViewName());
        setVisibility(R.id.lyt_wrapper_release_display, viewHandler.useViewReleased());
        setVisibility(R.id.lyt_wrapper_title_display, viewHandler.useViewTitles());
        setVisibility(R.id.lyt_wrapper_alias_display, viewHandler.useViewAlias());
        setVisibility(R.id.lyt_wrapper_words_display, viewHandler.useViewWords());
        setVisibility(R.id.lyt_wrapper_region_display, viewHandler.useViewRegion());
    }

    public FilterDisplayVO getFilterDisplayVO() {
        if (filterDisplayVO == null) {
            filterDisplayVO = new FilterDisplayVO();
        }

        return filterDisplayVO;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        loadToFilter();
        outState.putSerializable("filter", getFilterDisplayVO());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        filterDisplayVO = (FilterDisplayVO) savedInstanceState.getSerializable("filter");
        loadFromFilter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (datePickerDialog != null) {
            datePickerDialog.dismiss();
        }
    }

    private String checkExtractValueTextView(int idContainer, boolean use) {
        if (use) {
            return extractValueTextView(idContainer);
        }
        return null;
    }

    private String extractValueTextView(int idContainer) {
        TextView view = ViewUtils.getViewByTag(this, idContainer, "controller");
        return view.getText().toString();
    }

    private void insertValueTextView(int idContainer, String value) {
        TextView view = ViewUtils.getViewByTag(this, idContainer, "controller");
        view.setText(value);
    }

    private void updateTextReleased() {
        String text;
        if (getFilterDisplayVO().release == null) {
            text = "---";
        } else {
            text = new SimpleDateFormat("yyyy-MM-dd").format(
                    getFilterDisplayVO().release.getTime());
        }
        insertValueTextView(R.id.lyt_wrapper_release_display, text);
    }

    private void loadToFilter() {
        loadToFilter(getFilterDisplayVO());
    }

    private void loadToFilter(FilterDisplayVO filterDisplayVO) {
        filterDisplayVO.dataType = getSelectedDataType();
        filterDisplayVO.name = checkExtractValueTextView(R.id.lyt_wrapper_name_display,
                filterDisplayVO.dataType.getViewHandler().useViewName());
        filterDisplayVO.title = checkExtractValueTextView(R.id.lyt_wrapper_title_display,
                filterDisplayVO.dataType.getViewHandler().useViewTitles());
        filterDisplayVO.alias = checkExtractValueTextView(R.id.lyt_wrapper_alias_display,
                filterDisplayVO.dataType.getViewHandler().useViewAlias());
        filterDisplayVO.words = checkExtractValueTextView(R.id.lyt_wrapper_words_display,
                filterDisplayVO.dataType.getViewHandler().useViewWords());
        filterDisplayVO.region= checkExtractValueTextView(R.id.lyt_wrapper_region_display,
                filterDisplayVO.dataType.getViewHandler().useViewRegion());
    }

    private void loadFromFilter() {
        getSpinnerDataType().setSelection(getFilterDisplayVO().dataType.ordinal());
        insertValueTextView(R.id.lyt_wrapper_name_display, getFilterDisplayVO().name);
        updateTextReleased();
        insertValueTextView(R.id.lyt_wrapper_title_display, getFilterDisplayVO().title);
        insertValueTextView(R.id.lyt_wrapper_alias_display, getFilterDisplayVO().alias);
        insertValueTextView(R.id.lyt_wrapper_words_display, getFilterDisplayVO().words);
        insertValueTextView(R.id.lyt_wrapper_region_display, getFilterDisplayVO().region);
    }
}

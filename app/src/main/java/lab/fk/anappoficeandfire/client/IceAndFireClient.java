package lab.fk.anappoficeandfire.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.Gson;
import com.orm.SugarRecord;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import lab.fk.anappoficeandfire.client.RawHouse.RawBook;
import lab.fk.anappoficeandfire.client.RawHouse.RawCharacter;
import lab.fk.anappoficeandfire.client.RawHouse.RawHouse;
import lab.fk.anappoficeandfire.client.RawHouse.RawModel;
import lab.fk.anappoficeandfire.database.DBHandler;
import lab.fk.anappoficeandfire.model.AbstractModel;
import lab.fk.anappoficeandfire.model.Meta;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * Consumes the API of Ice and Fire
 *
 * Created by will on 5/17/16.
 */
@SuppressWarnings("unchecked")
public class IceAndFireClient {

    private static final int PAGE_SIZE = 50; // maximum supported by API. Anything beyond that will be replacd by 50

    /*private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");*/


    private static String requestData(EnumRequestDir requestDir, int page) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestDir.getRequestDir(page, PAGE_SIZE)).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private static <Model extends AbstractModel, Raw extends RawModel<Model>> void getModel(Class<Raw> raw, EnumRequestDir requestDir) throws IOException {
        int page = 0;
        int count;
        do {
            List<Model> chunk = getModel(raw, requestDir, ++page);
            count = chunk.size();
            DBHandler.populateWithData(chunk);
        } while (count == PAGE_SIZE);
    }

    @SuppressLint("DefaultLocale")
    private static <Model, Raw extends RawModel<Model>> List<Model> getModel(Class<Raw> raw, EnumRequestDir requestDir, int page) throws IOException {
        Log.d("AOIF", String.format("Loading '%s' (page %d)", requestDir.name(), page));
        String json = requestData(requestDir, page);
        Gson gson = new Gson();
        Raw[] result = (Raw[]) gson.fromJson(json,
                //creating a array of Raw type to extract class....yeah....
                Array.newInstance(raw, 0).getClass());
        return Stream.of(result).map(r -> {
            try {
                Log.d("AOIF", String.format("modeling '%s'", r.toString()));
                return r.toModel();
            } catch (Exception e) {
                throw new RuntimeException(e); //TODO handle Exception
            }
        }).collect(Collectors.toList());
    }

    /**
     *
     * @throws Exception
     */
    public static void loadEntities() throws Exception {
        getModel(RawBook.class, EnumRequestDir.BOOK);
        getModel(RawHouse.class, EnumRequestDir.HOUSE);
        getModel(RawCharacter.class, EnumRequestDir.CHARACTER);
        DBHandler.updateMeta();
    }

}

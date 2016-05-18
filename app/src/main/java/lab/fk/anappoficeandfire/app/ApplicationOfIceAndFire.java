package lab.fk.anappoficeandfire.app;

import android.app.Application;

import com.orm.SugarContext;

/**
 *
 * Master aplication
 *
 * Created by will on 5/17/16.
 */
public class ApplicationOfIceAndFire extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}

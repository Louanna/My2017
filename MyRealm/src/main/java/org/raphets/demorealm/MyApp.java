package org.raphets.demorealm;

import android.app.Application;

import org.raphets.demorealm.util.RealmHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by matou0289 on 2016/10/21.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration=new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .encryptionKey(new byte[64])//加密用字段,不是64位会报错
                .schemaVersion(2)//版本号
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}

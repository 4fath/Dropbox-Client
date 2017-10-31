package org.fath.domain;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

abstract class BaseAuth {

    DbxClientV2 getDBClient(String accessToken, String locale) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(accessToken).withUserLocale(locale).build();
        return new DbxClientV2(config, accessToken);
    }

}

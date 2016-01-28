/*
 * Copyright 2016 nbonnec
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nbonnec.mediaseb.di.modules;

import android.app.Application;
import android.util.Log;

import com.nbonnec.mediaseb.data.interpreters.MSSInterpreter;
import com.nbonnec.mediaseb.data.interpreters.MSSInterpreterImpl;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {
    public static final String TAG = ApiModule.class.getSimpleName();

    public static final int DISK_CACHE_SIZE = 50 * 1024 * 1024;
    public static final int PULL_TOLERANCE = 10;

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(Application app) {
        return createOkHttpClient(app);
    }

    @Provides
    @Singleton
    public static MSSInterpreter provideMSSInterpreter() {
        return new MSSInterpreterImpl();
    }

    private static OkHttpClient createOkHttpClient(Application app) {
        OkHttpClient client = new OkHttpClient();

        try {
            File cacheDir = new File(app.getCacheDir(), "http");
            Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (NullPointerException e) {
            Log.e(TAG, "Unable to initialize OkHttpclient with disk cache", e);
        }

        return client;
    }

}

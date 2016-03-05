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

package com.nbonnec.mediaseb.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nbonnec.mediaseb.MediasebApp;
import com.nbonnec.mediaseb.ui.event.BusProvider;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseFragment extends Fragment {

    private CompositeSubscription subscriptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inject dependencies
        MediasebApp app = MediasebApp.get(getActivity());
        app.inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        subscriptions = new CompositeSubscription();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        BusProvider.getInstance().unregister(this);
        subscriptions.unsubscribe();
    }

    protected void addSubscription(Subscription s) {
        subscriptions.add(s);
    }
}

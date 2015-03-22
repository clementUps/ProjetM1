package com.projet.M1.main;

import android.app.Activity;
import android.app.ListFragment;

/**
 * Created by VinkHiker on 22/03/2015.
 */
public class HeadlinesFragment extends ListFragment {
    OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected (int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " doit implémenter OnHeadlineSelectedListener.");
        }
    }
}

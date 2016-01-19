package co.justbeta.justbeta;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import android.widget.TextView;

import com.github.florent37.hollyviewpager.HollyViewPagerBus;

/**
 * Created by barnabas on 10/20/15
 */
public class ScrollViewFragment extends Fragment {
    private TextView title;
    private ObservableScrollView scrollView;

    public static ScrollViewFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        ScrollViewFragment fragment = new ScrollViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scroll, container, false);
        scrollView = (ObservableScrollView) view.findViewById(R.id.scrollView);
        title = (TextView) view.findViewById(R.id.title);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        title.setText(getArguments().getString("title"));

        HollyViewPagerBus.registerScrollView(getActivity(), scrollView);
    }
}

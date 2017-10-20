package com.amit.autoscrollviewpager.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amit.autoscrollviewpager.R;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment which shows the image along with its index
 *
 * Created by Amit Barjatya on 10/20/17.
 */
public class ImageElementFragment extends Fragment {

    @BindView(R.id.fragment_image_element_imageview)
    ImageView iv;

    @BindView(R.id.fragment_image_element_textview)
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_element,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = getArguments().getString("url");
        int index = getArguments().getInt("index");
        Picasso.with(getContext()).load(url).into(iv);
        tv.setText(String.format(Locale.US,"Showing Index %d",index));
    }
}

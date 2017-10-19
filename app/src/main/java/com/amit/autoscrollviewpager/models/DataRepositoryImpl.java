package com.amit.autoscrollviewpager.models;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A datarepository implementation which provides the ImageElement data
 * It maintains the ImageElement data as an arraylist of image elements
 *
 * Created by Amit Barjatya on 10/19/17.
 */

public class DataRepositoryImpl implements DataRepository {

    ArrayList<ImageElement> mElements;

    public DataRepositoryImpl() {
        mElements = new ArrayList<>();
        initElements();
    }

    private void initElements(){
        mElements.add(new ImageElement(0,"https://i.imgur.com/C9Wz6G8.jpg"));
        mElements.add(new ImageElement(1,"https://i.imgur.com/bdh4Qpn.jpg"));
        mElements.add(new ImageElement(2,"https://i.imgur.com/UMKNd1b.jpg"));
        mElements.add(new ImageElement(3,"https://i.imgur.com/mG7rmUW.jpg"));
        mElements.add(new ImageElement(4,"https://i.imgur.com/IGGBxys.jpg"));
        mElements.add(new ImageElement(5,"https://i.imgur.com/MF6hysE.jpg"));
        mElements.add(new ImageElement(6,"https://i.imgur.com/wC3u4rM.jpg"));
        mElements.add(new ImageElement(7,"https://i.imgur.com/MPwlRPe.jpg"));
        mElements.add(new ImageElement(8,"https://i.imgur.com/TvGOeJ6.jpg"));
        mElements.add(new ImageElement(9,"https://i.imgur.com/ud8DGlY.jpg"));
    }

    @Override
    public ImageElement getElement(int index) throws ArrayIndexOutOfBoundsException{
        if (index<0 || index>=mElements.size())
            throw new ArrayIndexOutOfBoundsException(
                    String.format(Locale.US,"cannot get element at index %d. Elements size is %d",index,mElements.size())
            );
        return mElements.get(index);
    }
}

package com.frackowiak.befit.ipm.utils;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by PFRACKOW on 09.12.2015.
 */
public class ArrayAdapterDecorator<T extends Comparable<T>> extends ArrayAdapter<T> {

    private List<T> elements;

    public ArrayAdapterDecorator(Context context, int resource, List<T> elements) {
        super(context, resource, elements);
        this.elements = elements;
    }

    public List<T> getList() {
        return elements;
    }

    @Override
    public void add(T object) {
        if (elements.contains(object)) {
            super.remove(object);
        }
        super.add(object);
        Collections.sort(elements);
    }


}

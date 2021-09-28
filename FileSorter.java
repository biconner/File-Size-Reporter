package com.company;

import java.util.Comparator;

//custom comparator to sort the arrayList of files
public class FileSorter implements Comparator<MyFile>
{
    @Override
    public int compare(MyFile o1, MyFile o2) {
        if(o2.getSize() > o1.getSize()){
            return 1;
        } else {
            return -1;
        }
    }
}

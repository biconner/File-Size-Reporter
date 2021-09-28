package com.company;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class MyFile {

// contains information of a File

    private Path fileName;
    private long size;
    private FileTime creationTime;

    // Constructor for MyFile Object
    public MyFile(long size, Path fileName, FileTime creationTime) {
        this.fileName = fileName;
        this.size = size;
        this.creationTime = creationTime;
    }
    //getter for file size
    public long getSize() {
        return (int) size;
    }


    @Override // override toString for this app
    public String toString() {
        String cnvtSize = null;
        if(size > 0 && size < 1024) {
            cnvtSize = size + " B";
        } else if(size > 1024 && size < (1024*1024)) {
            cnvtSize = String.valueOf(size/1024) + " KB";
        }else if(size > (1024*1024)) {
            cnvtSize = String.valueOf(size/1048576) + " MB";
        }
        String s = cnvtSize + "  " + String.valueOf(fileName) + "  " + creationTime;
        return s;
    }
}

package com.company;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.*;

class FileUtils {

    /* Iterative function to traverse given
       directory in Java using BFS*/
    public static void main(String[] args) throws Exception {
        //ask user to select a directory. pop up GUI to choose directory from
        System.out.println("Please select a directory");
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("."));
        fc.setDialogTitle("Please select a directory");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        File file = null;// variable to hold file chosen by user
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();// variable for file chosen by user
        } else {
            System.out.println("No Selection ");
        }// ENDS ELSE
        String inputDir = file.getPath(); // set directory chosen by the user to String
        printDirsFiles(inputDir); // pass chosen directory to method to traverse directory and print the files
    }// ENDS MAIN METHOD

    public static void printDirsFiles(String inputDir) throws Exception {
        /* make a queue to store files and directories */
        Scanner keyboard = new Scanner(System.in);
        Queue<File> queue = new LinkedList<>();
        //TreeSet<MyFile> files = new TreeSet<MyFile>(new FileSorter());
        List<MyFile> files = new ArrayList<>();// arrayList to hold file objects
        FileTime time;// variable to hold file creation date
        long size; // variable to hold file size
        Path file; // variable to hold file name
        queue.add(new File(inputDir));// adds chosen directory to the queue

        /* loop until queue is empty -*/
        while (!queue.isEmpty()) {
            /* get next file/directory from the queue */
            File current = queue.poll();
            File[] fileDirList = current.listFiles();

            if (fileDirList != null) {
                /* Enqueue all directories and print
                   all files. */
                for (File fd : fileDirList) {
                    if (fd.isDirectory())
                        queue.add(fd);
                    else {
                        // read attributes from each file and create new MyFile object with the data received
                        BasicFileAttributes attr = Files.readAttributes(fd.toPath(), BasicFileAttributes.class);
                        file = fd.toPath().getFileName();
                        time = attr.creationTime();
                        size = attr.size();
                        files.add(new MyFile(size, file, time));
                    }// ENDS ELSE BLOCK
                }// ENDS FOR LOOP
            }// ENDS IF STATEMENT
        }// ENDS WHILE LOOP

        // Sort the array list of objects in descending order with custom Comparator class
        Collections.sort(files, new FileSorter());

        // Loop through the arrayList and print to screen
        for (MyFile f : files) {
            System.out.println(f.toString());
        }// ENDS FOR LOOP

        //Ask user if they would like to save the file
        System.out.println(" Would you like to save the file? enter YES or NO");
        String answer = keyboard.nextLine().toUpperCase();

        //If user answers yes. pop up GUI to choose where to save the file
        if (answer.equals("YES")) {
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a file to save");
            int userSelection = fileChooser.showSaveDialog(parentFrame);

            File fileToSave = null;// Variable to hold location to save file
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                fileToSave = fileChooser.getSelectedFile();
                System.out.println("Saved as file: " + fileToSave.getAbsolutePath());
            }// ENDS USER SELECTION IF STATEMENT

            //Print writer to write array list to the chosen file. Each object is printed on a new line
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileToSave))) {
                for (MyFile f : files) { // loop through arrayList
                    writer.println(f.toString());// write new line for each object
                }// ENDS FOR LOOP
            } // ENDS TRY BLOCK
        } else {
            System.exit(0);
        } // ENDS ELSE
        System.exit(0);// SYSTEM SHUTDOWN
    }// ENDS PRINTDIRSFILES METHOD
}// ENDS CLASS
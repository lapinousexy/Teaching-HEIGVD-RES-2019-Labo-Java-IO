package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 *
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

    @Override
    public void explore(File rootDirectory, IFileVisitor vistor) {
        /* So basically I begin by visiting the actual "object" and then I put the list of all "object"
         * (directory or file) inside an array, before sorting it (I saw that we need to sort on the Telegram discussion)
         * I check if it's null (which is the case when the directory is empty), and then I need to check whether it's a
         * folder or a file, the case is different because I need to visit files (and not explore them) and I need to
         * call the function recursively for folder.
         */
        vistor.visit(rootDirectory);
        File[] fileList = rootDirectory.listFiles();

        if (fileList != null) {
            Arrays.sort(fileList);

            for (File file : fileList) {
                if (file.isDirectory()) {
                    explore(file, vistor);
                } else {
                    vistor.visit(file);
                }
            }
        }

    }

}

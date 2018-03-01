package uk.ac.man.cs.segreganalysis.utilities;

import org.graphstream.graph.Graph;
import org.graphstream.stream.GraphParseException;
import org.graphstream.stream.file.FileSinkGML;
import uk.ac.man.cs.segreganalysis.view.LinLogLayout;

import java.io.IOException;

public class GraphUtilities {

    public static void saveGraph(Graph graph, String filename) {
        String filePath = filename;
        FileSinkGML fs = new FileSinkGML();

        try {
            fs.writeAll(graph, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static int displayLinLogLayout(String filename) {
        try {
            (new LinLogLayout()).findCommunities("/Users/martinacatizone/git/segreganalysis/"+filename);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        } catch (GraphParseException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static int[] calculateApproximateSize(int total, int[] relativeSizes) {

        int[] actualSizes = new int[relativeSizes.length];

        // sum of the relative sizes (e.g.: 1,1,1,2 = 5)
        int relativeTotal = 0;
        for (int i = 0; i < relativeSizes.length; i++) {
            relativeTotal += relativeSizes[i];
        }

        for (int i = 0; i < actualSizes.length; i++) {
            actualSizes[i] = ((total * relativeSizes[i]) / relativeTotal);
        }

        int checkTotal = 0;
        for (int i = 0; i < relativeSizes.length; i++) {
            checkTotal += actualSizes[i];
        }

        int difference = total - checkTotal;

        for (int i = 0; i < difference; i++) {
            actualSizes[i]++;
        }

        return actualSizes;
    }
}

package uk.ac.man.cs.segreganalysis.utilities;

import java.util.Arrays;

public class RelativeSizeSplitter {

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

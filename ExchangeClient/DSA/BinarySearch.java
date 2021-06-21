package DSA;

import java.util.ArrayList;

public class BinarySearch<T extends Comparable<T>> {

    public T binarySearch(ArrayList<T> list, T search)
    {
        int lowBound = 0;
        int highBound = list.size() - 1;

        while (lowBound <= highBound)
        {
            int mid = (lowBound + highBound) / 2;

            int compare = list.get(mid).compareTo(search);

            if (compare == 0)
            {
                search = list.get(mid);
                return search;
            }
            else if (compare > 0)
            {
                highBound = mid - 1;
            }
            else
            {
                lowBound = mid + 1;
            }
        }

        return null;
    }

}
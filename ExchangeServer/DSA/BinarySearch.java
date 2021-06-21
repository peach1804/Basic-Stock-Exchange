package DSA;

import java.util.ArrayList;

public class BinarySearch<T extends Comparable<T>> {

    public T binarySearch(ArrayList<T> list, T search)
    {
        int low = 0;
        int high = list.size()-1;

        while (low <= high)
        {
            int mid = low + (high - low) / 2;

            int compare = list.get(mid).compareTo(search);

            if (compare == 0)
            {
                search = list.get(mid);
                return search;
            }
            else if (compare < 0)
            {
                low = mid + 1;
            }
            else
            {
                high = mid - 1;
            }
        }

        return null;
    }

}
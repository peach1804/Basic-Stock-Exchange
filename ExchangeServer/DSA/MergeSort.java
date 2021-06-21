package DSA;

import java.util.ArrayList;
import java.util.List;

public class MergeSort<T extends Comparable<T>> {

    public void mergeSort(List<T> list) {
        mergeSort(list, list.size());
    }

    public void mergeSort(List<T> list, int n) {

        if (n > 1) {

            int mid = n / 2;
            List<T> left = new ArrayList<>(mid);
            List<T> right = new ArrayList<>(n - mid);

            for (int i = 0; i < mid; i++) {

                left.add(list.get(i));
            }

            for (int i = mid; i < n; i++) {

                right.add(list.get(i));
            }

            mergeSort(left, mid);
            mergeSort(right, n - mid);

            merge(list, left, right);
        }
    }

    public void merge(List<T> list, List<T> left, List<T> right) {

        int m = left.size();
        int n = right.size();

        int i = 0, j = 0, k = 0;

        // increment k and the index that is copied
        while (i < m && j < n) {

            if (left.get(i).compareTo(right.get(j)) < 0) {

                list.set(k, left.get(i));
                k++;
                i++;

            } else {

                list.set(k, right.get(j));
                k++;
                j++;
            }
        }

        // copy remaining elements of left[] to array[]
        for (; i < m; i++) {

            list.set(k, left.get(i));
            k++;
        }

        // copy remaining elements of right[] to array[]
        for (; j < n; j++) {

            list.set(k, right.get(j));
            k++;
        }
    }
}

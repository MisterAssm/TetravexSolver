package fr.azemouchi.tetravex.utils;

import java.lang.reflect.Array;

public class ArrayUtils {

    public static int[] remove(final int[] array, final int index) {
        return (int[]) remove((Object) array, index);
    }

    public static int getLength(final Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    private static Object remove(final Object array, final int index) {
        final int length = getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        final Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }

    public static int[] stringArrToIntArr(String[] s) {
        int[] result = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            result[i] = Integer.parseInt(s[i]);
        }
        return result;
    }

}

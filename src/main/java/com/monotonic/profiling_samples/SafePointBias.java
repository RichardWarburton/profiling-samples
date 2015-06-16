package com.monotonic.profiling_samples;

import java.util.Random;

public class SafePointBias
{
    private static final int SIZE = 1024;
    private static final int N = 1_000_000;
    private static final int OUTER = 1_000_000;
    private static final int[] array = new int[SIZE];

    public static void main(String[] args)
    {
        Util.printPid();
        init();
        outer();
        System.out.println(array[new Random().nextInt(SIZE)]);
    }

    private static void init()
    {
        for (int i = 0; i < SIZE; i++)
        {
            array[i] = i;
        }
    }

    private static void outer()
    {
        for (int i = 0; i < OUTER; i++)
        {
            hotMethod(i);
        }
    }

    private static void hotMethod(final int i)
    {
        final int[] array = SafePointBias.array;
        for (int k = 0; k < N; k++)
        {
            final int index = i % SIZE;
            for (int j = index; j < SIZE; j++)
            {
                array[index] += array[j];
            }
        }
    }
}

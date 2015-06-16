package com.monotonic.profiling_samples;

import java.util.Random;

/**
 * This code example is identical to {@link SafePointBias} but
 * uses a <code>long</code> indexed loop, whose loop entry
 * safe-points aren't optimised away.
 */
public class NoSafePointBias
{
    private static final int SIZE = 1024;
    private static final long N = 1_000_000;
    private static final long OUTER = 1_000_000;
    private static final long[] array = new long[SIZE];

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
        for (long i = 0; i < OUTER; i++)
        {
            hotMethod(i);
        }
    }

    private static void hotMethod(final long i)
    {
        final long[] array = NoSafePointBias.array;
        for (long k = 0; k < N; k++)
        {
            final int index = (int) (i % SIZE);
            for (long j = index; j < SIZE; j++)
            {
                array[index] += array[((int) j)];
            }
        }
    }
}

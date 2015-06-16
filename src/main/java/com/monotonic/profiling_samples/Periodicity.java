package com.monotonic.profiling_samples;

public class Periodicity
{
    private static final int SIZE = 10;
    private static final int N = 1_000_000;
    private static final int[] array = new int[SIZE];
    private static final int INITIAL_I = 10;
    private static final double PROFILE_INTERVAL = 1000;

    public static void main(String[] args)
    {
        Util.printPid();

        init();

        warmup();

        final double iterations = findIterations();
        System.out.println(iterations);
        final int hot1Iterations = (int) (iterations * 0.50);
        //final int hot2Iterations = (int) (iterations * 0.20);

        while (true)
        {
            for (int i = 0; i < hot1Iterations; i++)
            {
                hotMethod(i);
            }

            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static void init()
    {
        for (int i = 0; i < SIZE; i++)
        {
            array[i] = i;
        }
    }

    private static double findIterations()
    {
        long start = System.currentTimeMillis();
        hotMethod(INITIAL_I);
        long time = System.currentTimeMillis() - start;

        return PROFILE_INTERVAL / time;
    }

    private static void warmup()
    {
        findIterations();
        hotMethod2(INITIAL_I);
        findIterations();
        hotMethod2(INITIAL_I);
        findIterations();
        hotMethod2(INITIAL_I);
    }


    private static void hotMethod(final long i)
    {
        final int[] array = Periodicity.array;
        for (long k = 0; k < N; k++)
        {
            final int index = (int) (i % SIZE);
            for (long j = index; j < SIZE; j++)
            {
                array[index] += array[((int) j)];
            }
        }
    }

    private static void hotMethod2(final long i)
    {
        final int[] array = Periodicity.array;
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

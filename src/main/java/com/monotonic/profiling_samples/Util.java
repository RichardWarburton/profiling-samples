package com.monotonic.profiling_samples;

import java.lang.management.ManagementFactory;

public class Util
{
    public static void printPid()
    {
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
    }
}

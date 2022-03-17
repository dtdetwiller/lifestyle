package com.example.lifestyle.dashboardfragments;

import junit.framework.TestCase;

public class DashboardBMIFragmentTest extends TestCase {

    public void testCalculateBMI() {
        int feet = 5;
        int inches = 8;
        int weight = 167;
        double delta = .1;

        DashboardBMIFragment bmiFrag = new DashboardBMIFragment();

        double output = bmiFrag.calculateBMI(feet, inches, weight);
        assertEquals(25.4 ,output, delta);
    }
}
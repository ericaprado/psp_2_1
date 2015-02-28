/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.ecos.controller;

import edu.uniandes.ecos.NumericalIntegration;

/**
 *
 * @author ASUS-PC
 */
public class ConsoleController {
    public static void main( String[] args )
    {
        NumericalIntegration numericalIntegration = new NumericalIntegration();
        System.out.println( "Test1: "  + numericalIntegration.calculateXValue(new Double("0.20"), 6));
        System.out.println( "Test2: "  + numericalIntegration.calculateXValue(new Double("0.45"), 15));
        System.out.println( "Test3: "  + numericalIntegration.calculateXValue(new Double("0.495"), 4));
    }
}

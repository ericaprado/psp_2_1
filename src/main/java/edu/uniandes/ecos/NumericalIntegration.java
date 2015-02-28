/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.ecos;

import java.util.LinkedList;





/**
 * Clase que se encarga de realizar la Integración
 * numérica con la regla Simpson
 * @author ASUS-PC
 */
public class NumericalIntegration {
    
    
    private static double initialValueX = 1.0;
    private static int numSeg = 10;
    private static double accetableError = 0.000001;
    
   /**
    * Este método sirve para calcular la funcion Gamma, 
    * es el que evalua si es entero o fraccionario para
    * ejecutar la función indicada
    * @param num - numerador de la fracción
    * @param den - denominador de la fracción
    * @return calculo de la función gamma
    */
    public double calculateGamma(int num, int den){           
        int result = num%den; 
        
        if(result == 0)
            return calculateFactorial((num/den)-1);
        else
            return calculateGammaNonInteger((long)num, (long)den);
            
    }
    
    /**
     * Este método se encarga de realizar el calculo de la función
     * Gamma para numeros cuya división no es exacta
     * @param num numerador
     * @param den denominador
     * @return valor calculado
     */
    public double calculateGammaNonInteger(long num, long den){
        
        long acum = num - 2;        
        long numM = acum - 2;
        
        
        while(numM > 0){
            acum *= numM;
            numM -= 2;
            den *= 2;
        }
        
         return (acum * Math.sqrt(Math.PI))/(double)den; 
    }
    
    /**
     * Este método realiza el calculo del factorial de un número entero
     * @param value número al cual se le va a calcular el factorial
     * @return resultado del calculo
     */
    public double calculateFactorial(int value) {
        long res = 1;
        for (int i = 1; i <= value; i++) {
            res *= i;
        }
        return new Double(res);
    }
    
    
    /**
     * Este método realiza el calculo completo de la función F(x)
     * @param dof - Degrees od Freedom
     * @param value - valor de x en la función
     * @return valor calculado
     */
    public double calculateFunction(int dof, double value) {
        double res = calculateGamma(dof+1, 2);        
        res = res/((Math.sqrt(dof*Math.PI)) * calculateGamma(dof, 2));        
        res = res * (Math.pow((double)1/(1 + ((Math.pow(value, 2))/dof)), (dof+1)/(double)2));      
        return res;
    }
    
    /**
     * Método que realiza el calculo total de la Integración Numérica
     * @param numSeg - Número de Segmentos
     * @param x - Valor de x
     * @param dof - Degrees of Freedom en la fórmula
     * @return valor p calculado
     */
    public double calculateIntegralValue(int numSeg, double x, int dof){
        
        double p = 0;
        double w = x/numSeg;
        
        for (int i = 0; i < numSeg + 1; i++) {
            int multiplier = 1;
            
            if(i!=0 && i!=numSeg)
                multiplier = i%2==0?2:4;
            double terms = calculateFunction(dof, i*w);
            
            terms = terms * (w/3) * multiplier;
            p += terms;
        }
        
        return p;
    }
    
    /**
     * Metodo para calcular x a partir de p y dof
     * @param p - valor real de p
     * @param dof - Degrees of Freedom en la fórmula
     * @return 
     */
    public double calculateXValue(double p, int dof){
    
        double x = initialValueX;
        double d = 0.5;
        int i = 0;
        LinkedList<Double> listError = new LinkedList<Double>();
        
        while (true) {
            
            double result = calculateIntegralValue(numSeg, x, dof);
            double error = p -result;
            listError.add(error);
            
            if(result == p)
                return x;
            
            if(Math.abs(error) <= accetableError)
                return x;
            
            if(result<p)
                x+=d;
            
            if(result>p)
                x-=d;
                        
            if(i>0){
                double lastError = listError.get(i-1);
                
                if((lastError>0 && error<0)||(lastError<0 && error>0))
                    d = d!=new Double("0")? d / new Double("2"):d;
            } 
            
            
            i++;
        }
            
    }
    
}

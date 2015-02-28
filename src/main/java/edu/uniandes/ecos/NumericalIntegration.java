/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.ecos;

import java.util.LinkedList;





/**
 * Clase que se encarga de realizar la Integraci�n
 * num�rica con la regla Simpson
 * @author ASUS-PC
 */
public class NumericalIntegration {
    
    
    private static double initialValueX = 1.0;
    private static int numSeg = 10;
    private static double accetableError = 0.000001;
    
   /**
    * Este m�todo sirve para calcular la funcion Gamma, 
    * es el que evalua si es entero o fraccionario para
    * ejecutar la funci�n indicada
    * @param num - numerador de la fracci�n
    * @param den - denominador de la fracci�n
    * @return calculo de la funci�n gamma
    */
    public double calculateGamma(int num, int den){           
        int result = num%den; 
        
        if(result == 0)
            return calculateFactorial((num/den)-1);
        else
            return calculateGammaNonInteger((long)num, (long)den);
            
    }
    
    /**
     * Este m�todo se encarga de realizar el calculo de la funci�n
     * Gamma para numeros cuya divisi�n no es exacta
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
     * Este m�todo realiza el calculo del factorial de un n�mero entero
     * @param value n�mero al cual se le va a calcular el factorial
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
     * Este m�todo realiza el calculo completo de la funci�n F(x)
     * @param dof - Degrees od Freedom
     * @param value - valor de x en la funci�n
     * @return valor calculado
     */
    public double calculateFunction(int dof, double value) {
        double res = calculateGamma(dof+1, 2);        
        res = res/((Math.sqrt(dof*Math.PI)) * calculateGamma(dof, 2));        
        res = res * (Math.pow((double)1/(1 + ((Math.pow(value, 2))/dof)), (dof+1)/(double)2));      
        return res;
    }
    
    /**
     * M�todo que realiza el calculo total de la Integraci�n Num�rica
     * @param numSeg - N�mero de Segmentos
     * @param x - Valor de x
     * @param dof - Degrees of Freedom en la f�rmula
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
     * @param dof - Degrees of Freedom en la f�rmula
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

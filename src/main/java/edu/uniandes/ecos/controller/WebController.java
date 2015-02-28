/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uniandes.ecos.controller;


import edu.uniandes.ecos.NumericalIntegration;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;

/**
 *
 * @author ASUS-PC
 */
public class WebController extends HttpServlet{
    /**
     * Clase main que se llama cuando el programa es ejecutado y el resultado
     * se mostrará a través de web
     * @param args
     * @throws Exception 
     */
    public static void main( String[] args )  throws Exception 
    {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new WebController()),"/*");
        server.start();
        server.join();
    
    }
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
               
        String result = "<HTML>";
               
        result += "<H1>Test</H1></BR><TABLE border=1>";
        
        NumericalIntegration numericalIntegration = new NumericalIntegration();
        result += "<TR><TD>p = 0.20</TD><TD>dof = 6</TD><TD>0 to x= " + numericalIntegration.calculateXValue(0.20, 6)+"</TD></TR>"; 
        result += "<TR><TD>p = 0.45</TD><TD>dof = 15</TD><TD>0 to x= " + numericalIntegration.calculateXValue(0.45, 15)+"</TD></TR>"; 
        result += "<TR><TD>p = 0.495</TD><TD>dof = 4</TD><TD>0 to x= " + numericalIntegration.calculateXValue(0.495, 4)+"</TD></TR>";   
                
        result += "</TABLE></HTML>";
        
        resp.getWriter().write(result);          
       
    }
}

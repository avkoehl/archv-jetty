package org.eclipse.jetty.embedded;

import java.io.*;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.FilterHolder;

import org.eclipse.jetty.servlets.CrossOriginFilter; 

public class AllServlet 
{
  public static void main (String[] args) throws Exception
  {
    Server server = new Server(8888);
    ServletContextHandler context = new ServletContextHandler (ServletContextHandler.SESSIONS);
    context.addServlet (DrawMatches.class, "/draw/*");
    context.addServlet (ProcessImages.class, "/process/*");
    context.addServlet (ScanDatabase.class, "/scan/*");
    context.addServlet (ShowKeypoints.class, "/show/*");

    //to enable CORS
    FilterHolder holder = new FilterHolder (CrossOriginFilter.class);
    holder.setInitParameter("allowedOrigins", "*");
    holder.setInitParameter("allowedMethods", "GET,POST");

    context.addFilter(holder, "/*", null);
    server.setHandler(context);

    server.start();
    server.join();
  }


  public static class DrawMatches extends HttpServlet
  {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      int flag = 0;
      response.setContentType("application/json; charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);

      String filePath = "..//archive-vision//drawMatches.exe"; 
      String [] arg = new String[18];
      arg[0] = "-i1";
      arg[1] = request.getParameter("-i1");
      arg[2] = "-i2";
      arg[3] = request.getParameter("-i2");
      arg[4] = "-o";
      arg[5] = request.getParameter("-o");
      arg[6] = "-p";
      arg[7] = request.getParameter("-p");

      if (request.getParameter("-p") != null)
        flag = 1;

      else
      {
        arg[8] = "-h";
        arg[9] = request.getParameter("-h");
        arg[10] = "-oct";
        arg[11] = request.getParameter("-oct");
        arg[12] = "-l";
        arg[13] = request.getParameter("-l");
        arg[14] = "-s";
        arg[15] = request.getParameter("-s");
        arg[16] = "-r";
        arg[17] = request.getParameter("-r");
      }

    try {
      ProcessBuilder builder = new ProcessBuilder (filePath, arg[0], arg[1], arg[2], arg[3], arg[4], arg[5], arg[6], arg[7]);
      if (flag == 0)
        builder = new ProcessBuilder (filePath, arg[0], arg[1], arg[2], arg[3], arg[4], arg[5], arg[8], arg[9], arg[10], arg[11], arg[12], arg[13], arg[14], arg[15], arg[16], arg[17]);
      Process process = builder.start();
      process.waitFor();

      String output = "{\"file\": \"" + arg[5] + "\"}";
      response.getWriter().println(output);

    } catch (Exception ioe) {
	String error = "{\"error\": \"Syntax error in request, fix parameters\"}";
	response.getWriter().println(error);
      }

    }
  }

  public static class ProcessImages extends HttpServlet
  {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      int flag = 0;
      response.setContentType("application/json; charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);

      String filePath = "..//archive-vision//processImages.exe"; 
      String [] arg = new String[16];
      arg[0] = "-i";
      arg[1] = request.getParameter("-i");
      arg[2] = "-o";
      arg[3] = request.getParameter("-o");
      arg[4] = "-p";
      arg[5] = request.getParameter("-p");

      if (request.getParameter("-p") != null)
        flag = 1;

      else
      {
        arg[6] = "-h";
        arg[7] = request.getParameter("-h");
        arg[8] = "-oct";
        arg[9] = request.getParameter("-oct");
        arg[10] = "-l";
        arg[11] = request.getParameter("-l");
        arg[12] = "-s";
        arg[13] = request.getParameter("-s");
        arg[14] = "-r";
        arg[15] = request.getParameter("-r");
      }

    try {
      ProcessBuilder builder = new ProcessBuilder (filePath, arg[0], arg[1], arg[2], arg[3], arg[4], arg[5]);
      if (flag == 0)
        builder = new ProcessBuilder (filePath, arg[0], arg[1], arg[2], arg[3], arg[6], arg[7], arg[8], arg[9], arg[10], arg[11], arg[12], arg[13], arg[14], arg[15]);

      Process process = builder.start();
      process.waitFor();
      String output = "{\"file\": \"" + arg[3] + "\"}";
      response.getWriter().println(output);

    } catch (Exception ioe) {
	String error = "{\"error\": \"Syntax error in request, fix parameters\"}";
	response.getWriter().println(error);
    }

    }
  }

  public static class ScanDatabase extends HttpServlet
  {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      int flag = 0;
      response.setContentType("application/json; charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);

      String filePath = "..//archive-vision//scanDatabase.exe"; 
      String [] arg = new String[20];
      arg[0] = "-i";
      arg[1] = request.getParameter("-i");
      arg[2] = "-d";
      arg[3] = request.getParameter("-d");
      arg[4] = "-k";
      arg[5] = request.getParameter("-k");
      arg[6] = "-o";
      arg[7] = request.getParameter("-o");
      arg[8] = "-p";
      arg[9] = request.getParameter("-p");

      if (request.getParameter("-p") != null)
        flag = 1;

      else
      {
        arg[10] = "-h";
        arg[11] = request.getParameter("-h");
        arg[12] = "-oct";
        arg[13] = request.getParameter("-oct");
        arg[14] = "-l";
        arg[15] = request.getParameter("-l");
        arg[16] = "-s";
        arg[17] = request.getParameter("-s");
        arg[18] = "-r";
        arg[19] = request.getParameter("-r");
      }

    try {
      ProcessBuilder builder = new ProcessBuilder (filePath, arg[0], arg[1], arg[2], arg[3], arg[4], arg[5], arg[6], arg[7], arg[8], arg[9]);
      if (flag == 0)
        builder = new ProcessBuilder (filePath, arg[0], arg[1], arg[2], arg[3], arg[4], arg[5], arg[6], arg[7], arg[10], arg[11], arg[12], arg[13], arg[14], arg[15], arg[16], arg[17], arg[18], arg[19]);
      Process process = builder.start();
      process.waitFor();
      String output = "{\"file\": \"" + arg[7] + "\"}";
      response.getWriter().println(output);

    } catch (Exception ioe) {
	String error = "{\"error\": \"Syntax error in request, fix parameters\"}";
	response.getWriter().println(error);
      }

    }
  }

  public static class ShowKeypoints extends HttpServlet
  {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
      int flag = 0;
      response.setContentType("text/html; charset=utf-8");
      response.setStatus(HttpServletResponse.SC_OK);

      String filePath = "..//archive-vision/showKeypoints.exe"; 
      String [] arg = new String[16];
      arg[0] = "-i";
      arg[1] = request.getParameter("-i");
      arg[2] = "-o";
      arg[3] = request.getParameter("-o");
      arg[4] = "-p";
      arg[5] = request.getParameter("-p");

      if (request.getParameter("-p") != null)
        flag = 1;

      else
      {
        arg[6] = "-h";
        arg[7] = request.getParameter("-h");
        arg[8] = "-oct";
        arg[9] = request.getParameter("-oct");
        arg[10] = "-l";
        arg[11] = request.getParameter("-l");
        arg[12] = "-s";
        arg[13] = request.getParameter("-s");
        arg[14] = "-r";
        arg[15] = request.getParameter("-r");
      }

    try {
      ProcessBuilder builder = new ProcessBuilder (filePath, arg[0], arg[1], arg[2], arg[3], arg[4], arg[5]);
      if (flag == 0)
        builder = new ProcessBuilder (filePath, arg[0], arg[1], arg[2], arg[3], arg[6], arg[7], arg[8], arg[9], arg[10], arg[11], arg[12], arg[13], arg[14], arg[15]);
      Process process = builder.start();
      process.waitFor();
      String output = "{\"file\": \"" + arg[3] + "\"}";
      response.getWriter().println(output);

    } catch (Exception ioe) {
	String error = "{\"error\": \"Syntax error in request, fix parameters\"}";
	response.getWriter().println(error);
      }

    }
  }
}

     



/* Servlet that manages log in behavior of webpage */

package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.sps.data.Login;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    UserService userService = UserServiceFactory.getUserService();
  
    
    String emailAddress = null;
    try {
      emailAddress = userService.getCurrentUser().getEmail();
    } catch(NullPointerException e) { }
    
    // send logout URL if user is logged in, otherwise send login URL
    String URL = emailAddress == null ? userService.createLoginURL("/") : userService.createLogoutURL("/");
    
    response.setContentType("application/json;");
    response.getWriter().println((new Gson()).toJson(new Login(emailAddress, URL)));
  }

  public static String getCurrentEmail() {
    return UserServiceFactory.getUserService().getCurrentUser().getEmail();
  }

}
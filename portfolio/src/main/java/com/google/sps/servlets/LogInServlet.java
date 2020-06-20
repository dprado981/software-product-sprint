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

@WebServlet("/login")
public class LogInServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    UserService userService = UserServiceFactory.getUserService();
    boolean loggedIn = userService.isUserLoggedIn();
    
    // send logout URL if user is logged in, otherwise send login URL
    String URL = loggedIn ? userService.createLogoutURL("/index.html") : userService.createLoginURL("/index.html");
    response.setContentType("application/json;");
    response.getWriter().println((new Gson()).toJson(URL));
  }

}
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/SessionDemo"})
public class SessionDemo extends HttpServlet {

  protected void processRequest(HttpServletRequest request, 
                              HttpServletResponse response)
          throws ServletException, IOException {
    String nameFromForm = request.getParameter("name");
    String secretFromForm = request.getParameter("secret");
    if (nameFromForm != null && nameFromForm.length() > 1 && secretFromForm!= null) {
      request.getSession().setAttribute("name", nameFromForm);
      request.getSession().setAttribute("secret", secretFromForm);
    } 
    String nameFromSession;
    String secretFromSession;
    { //Will set to null if no session exists
      nameFromSession = (String) request.getSession().getAttribute("name");
      secretFromSession = (String) request.getSession().getAttribute("secret");
    }
    makeHtmlPage(nameFromSession,secretFromSession,response);
  }

  private void makeHtmlPage(String name, String secret,HttpServletResponse response) throws IOException {
    response.setContentType("text/html;charset=UTF-8");
    try (PrintWriter out = response.getWriter()) {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet SessionDemo</title>");
      out.println("</head>");
      out.println("<body>");
      if (name != null) {
        out.println("<p> Welcome " + name  + " !</p>");
        out.println("<p> Your secret is " + secret  + " !</p>");
        out.println(MySessionAttributeListener.getUsersAsTable());
      } else {
        out.println("<h2>Please enter your name, and submit</h2>");
        out.println("<form action='SessionDemo'   method='POST'>" );
        out.println("<input type='input' placeholder='Your name (min 2 chars)' name='name'>");
        out.println("<input type='input' placeholder='Your secret' name='secret'>");
        out.println("<input type='submit'></form>");
      }
      out.println("</body>");
      out.println("</html>");
    }
  }
 
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

 
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>

}

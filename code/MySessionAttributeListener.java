import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

// The annotation marks the class as a listener, and the container will automatically register it.
@WebListener
public class MySessionAttributeListener implements HttpSessionAttributeListener {

  private static Map<String, String> users = new ConcurrentHashMap<>();
 
  @Override
  public void attributeAdded(HttpSessionBindingEvent event) {

    System.out.println("Attribute, Value added is " + event.getName()+ ", "+event.getValue());
     if (event.getName().equals("secret")) {
      //Here we assume, that name is already placed in the session object for this session
      HttpSession session = event.getSession();
      String user = (String) session.getAttribute("name");
      String secret = (String) session.getAttribute("secret");
      users.put(user, secret);
    }
  }
  
  // Return all on-line users in a HTML-Table 
  public static String getUsersAsTable(){
    StringBuilder table = new StringBuilder();
    table.append("<table><thead><tr><th>Users logged in</th></thead><tbody>");
    for(String userName: users.keySet()){
      table.append("<tr><td>"+userName+"</td></tr>");
    }    
    table.append("</tbody></table>");
    return table.toString();
  }

  @Override
  public void attributeRemoved(HttpSessionBindingEvent event) {
    System.out.println("Attribute Removed is " + event.getName());
    users.remove(event.getName()); 
  }

  @Override
  public void attributeReplaced(HttpSessionBindingEvent event) {
    System.out.println("Attribute replaced is " + event.getName());
  }
}

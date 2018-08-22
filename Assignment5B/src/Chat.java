
import java.io.PrintWriter;
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;




@WebServlet("/Chat")
public class Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public Chat() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5390/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			){
			
			HttpSession session =request.getSession();
			String name_client = (String) session.getAttribute("name_client");
			String uid = (String) session.getAttribute("uid");
			String uid_client = (String) session.getAttribute("uid_client");
			out.println("<html><center> <h1>You are talking to " + name_client + " </h1><center></html>");
			out.println("<html><center> <h3>Say hello :) </h3> </center> </html>");
			
			out.println("<html><form action=\"Chat \" method=\"post\"> Message: <input type=\"text\" name = \"message\"> <br>  <br> <input type=\"submit\" value = \"Send\"> </form> </html>");
			String message = request.getParameter("message");
			//out.println(name);
			
			int uid1=Integer.parseInt(uid);
			int sender=Integer.parseInt(uid);
			int uid2=Integer.parseInt(uid_client);
			
			if(uid1>uid2)
			{
				int temp =uid2;
				uid2=uid1;
				uid1=temp;
				
			}
			
			
			PreparedStatement ps = conn.prepareStatement("select *  from conversations where uid1=? and uid2=?");
			ps.setString(1,Integer.toString(uid1));
			ps.setString(2,Integer.toString(uid2));
			ResultSet check=ps.executeQuery();
			
			if(check.next()==false)
			{
				
				PreparedStatement ps1 = conn.prepareStatement("insert into conversations(uid1,uid2) values (?,? )");
				ps1.setString(1,Integer.toString(uid1));
				ps1.setString(2,Integer.toString(uid2));
				ps1.executeQuery();
			}
			
			//out.println("<html>  <a href = \"http://localhost:8080/Assignment5B/Messages \"> View Messages </a> </html>");
			
			PreparedStatement ps2 = conn.prepareStatement("select thread_id from conversations where uid1=? and uid2=? ");
			ps2.setString(1,Integer.toString(uid1));
			ps2.setString(2,Integer.toString(uid2));
			ResultSet rs= ps2.executeQuery();
			
			int thread_id=0;
			//out.println("hello");
			while(rs.next())
			{
				 thread_id=rs.getInt(1);
				 
			}
			session.setAttribute("thread_id", thread_id);
			
			out.println("<html>  <a href = \"http://localhost:8080/Assignment5B/Messages \"> View Messages </a> </html>");
			
			out.println("<html>  <a href = \"http://localhost:8080/Assignment5B/Logout \"> Logout </a> </html>");
			
			
			if(message!=null && message.length()>0)
			{
			PreparedStatement ps3 = conn.prepareStatement("insert into posts(uid,thread_id,timestamp,text) values (?,?,now(),? )");
			ps3.setString(1,Integer.toString(sender));
			ps3.setInt(2,thread_id);
			ps3.setString(3,message);
			ps3.executeQuery();
			}
			
			
			
			
			
			
			//displaying messages
			
			
			
			
			
			
			
			
		}
		
		catch (Exception sqle)
		{
		System.out.println("Exception : " + sqle);
		}
		}
		
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

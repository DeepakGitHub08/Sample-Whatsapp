

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/Home_page")
public class Home_page extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Home_page() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (
			    Connection conn = DriverManager.getConnection(
			    		"jdbc:postgresql://localhost:5390/postgres", "deepak", "");
			    Statement stmt = conn.createStatement();
			){
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("uid");
		String phone = (String) session.getAttribute("phone");
		PrintWriter out = response.getWriter();
		
		PreparedStatement ps = conn.prepareStatement("select name from users where uid = ?");
		ps.setString(1,uid);
		ResultSet rset = ps.executeQuery();
		String name = "";
		while(rset.next()) {
			 name = rset.getString(1);
			 //out.println("loop running");
		}
	    
		out.println("<html><h2> Hello " + name + " </h2> " + "<form action=\"Home_page \" method=\"post\"> Who do you want to talk with: <input type=\"text\" name = \"uid_client\"> <br>  <br> <input type=\"submit\" value = \"Submit\"> </form> </html>");

		out.println("<html>  <a href = \"http://localhost:8080/Assignment5B/Logout \"> Logout </a> </html>");
	}
	catch (Exception sqle)
	{
	System.out.println("Exception : " + sqle);
	}

}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();		
		try (
					    Connection conn = DriverManager.getConnection(
					    		"jdbc:postgresql://localhost:5390/postgres", "deepak", "");
					    Statement stmt = conn.createStatement();
					){
				
				

				String uid_client = request.getParameter("uid_client");
				
				
				PreparedStatement ps1 = conn.prepareStatement("select name from users where uid = ?");
				ps1.setString(1,uid_client);
				ResultSet rset1 = ps1.executeQuery();
				String name_client = "";
				while(rset1.next()) {
					 name_client = rset1.getString(1);
				}
				HttpSession session = request.getSession();
				
				
				
				if(name_client.length()!=0)
				{
					session.setAttribute("name_client", name_client);
					//session.setAttribute("name_client", name_client);
					session.setAttribute("uid_client", uid_client);
					response.sendRedirect("Chat");
				}
				
				
				
				
				
				
				
				
			
			}
			catch (Exception sqle)
			{
			System.out.println("Exception : " + sqle);
			}

	}

}

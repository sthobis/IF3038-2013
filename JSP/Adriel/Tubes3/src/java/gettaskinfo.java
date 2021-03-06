/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Adriel
 */
@WebServlet(urlPatterns = {"/gettaskinfo"})
public class gettaskinfo extends HttpServlet {
    private Connection connection;
    private String query = null;
    private Statement statement;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
 
        String url = "jdbc:mysql://localhost:3306/progin_405_13510029";
        String username = "progin";
        String password = "progin";
        try {
         Class.forName("com.mysql.jdbc.Driver").newInstance();

         connection = DriverManager.getConnection(url, username , password);
        }
        catch (Exception e) {

         e.printStackTrace();
        }

       }
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String id = request.getParameter("id");
            String type = request.getParameter("type");
            
            try {
                if (type.equals("taskname"))
                {
                    query="SELECT task_name FROM task WHERE task_id = '" + id + "'";
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    rs.next();
                    out.print(rs.getString("task_name"));
                }
                else if (type.equals("status"))
                {
                    query="SELECT status FROM task WHERE task_id = '" + id + "'";
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    rs.next();
                    out.print(rs.getString("status"));
                }
                else if (type.equals("attach"))
                {
                    query="SELECT link FROM attachment WHERE task_id = '" + id + "'";
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    String returnvalue = "";
                    while (rs.next())
                    {
                            returnvalue = returnvalue + rs.getString("link") + " ";
                    }
                    out.print(returnvalue);
                }
                else if (type.equals("deadline"))
                {
                    query="SELECT deadline FROM task WHERE task_id = '" + id + "'";
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    rs.next();
                    out.print(rs.getString("deadline"));
                }
                else if (type.equals("assignee"))
                {
                    query="SELECT people_incharge_task FROM task_incharge WHERE task_id = '" + id + "'";
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    String returnvalue = "";
                    while (rs.next())
                    {
                            returnvalue = returnvalue + rs.getString("people_incharge_task") + ",";
                    }
                    out.print(returnvalue);
                }
                else if (type.equals("tag"))
                {
                    query="SELECT tag_name FROM tag, tasktag WHERE tasktag.task_id = '" + id + "' AND tag.tag_id = tasktag.tag_id";
                    statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(query);
                    String returnvalue = "";
                    while (rs.next())
                    {
                            returnvalue = returnvalue + rs.getString("tag_name") + ",";
                    }
                    out.print(returnvalue);
                }
            } catch (SQLException ex) {
                Logger.getLogger(addcomment.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {            
            out.close();
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(addcomment.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

/**********************************************************************
    Freeciv-web - the web version of Freeciv. http://play.freeciv.org/
    Copyright (C) 2009-2015  The Freeciv-web project

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

***********************************************************************/


package org.freeciv.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import javax.sql.*;
import javax.naming.*;


/**
 * Counts number of users.
 */
public class UserCount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

       Connection conn = null;
        try {
            Context env = (Context) (new InitialContext().lookup("java:comp/env"));
            DataSource ds = (DataSource) env.lookup("jdbc/freeciv_mysql");
            conn = ds.getConnection();

            String countSQL = "SELECT count(*) FROM `auth`";
            PreparedStatement preparedStatement = conn.prepareStatement(countSQL);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            if (rs != null) {
              response.getOutputStream().print(rs.getString(1));
              return;
            }


      } catch (Exception err) {
            response.setHeader("result", "error");
            err.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to count users");
            return;
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }


    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.getOutputStream().print("Sorry");

    }

}

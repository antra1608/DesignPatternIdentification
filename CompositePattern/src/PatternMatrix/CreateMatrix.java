package PatternMatrix;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBhelper;
import util.FetchClass;

/**
 * Servlet implementation class CreateMatrix
 */
@WebServlet("/CreateMatrix")
public class CreateMatrix extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateMatrix() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = DBhelper.getConnection();
		PreparedStatement pt;

		String type = request.getParameter("matrix");
		if (type.equals("coverage")) {
			String ans[][] = new String[7][4];

			try {
				Class[] a = FetchClass.getClasses("MVCPatternDesign");

				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 4; j++) {
						if (j == 0) {
							ans[i][j] = a[i].getName().substring(a[i].getName().lastIndexOf(".") + 1);
						} else {
							pt = con.prepareStatement(
									"select sub_element_id from subelement where pattern_main_element=? and pattern_id=? ");
							pt.setInt(1, i);
							pt.setInt(2, j - 1);
							ResultSet rs = pt.executeQuery();
							int count = 0;
							while (rs.next()) {
								count++;
							}
							ans[i][j] = String.valueOf(count + 1);
						}
					}
				}
				request.setAttribute("matrix", ans);
				request.getRequestDispatcher("index.jsp").include(request, response);
				;

			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (type.equals("overlapping")) {
			System.out.println("in case 2");
			String ans[][] = new String[4][4];
			String a[] = new String[4];
			a[1] = "Composite";
			a[2] = "Observer";
			a[3] = "Strategy";
			try {

				pt = con.prepareStatement("select * from subelement where sub_element_name=? ");
				pt.setString(1, "update");
				ResultSet rs = pt.executeQuery();

				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						if (i == 0 || j == 0) {
							if(i==0 && j==0)
								ans[i][j]=" ";
							if (j == 0 ) {
								ans[i][j] = a[i];

							}
							if (i == 0 ) {
								ans[i][j] = a[j];
							}
							System.out.println("case one for i="+i +"ans j= "+j);
						} else {
							ans[i][j] = String.valueOf(1);
						}
					}
				}

				int count = 0;
				while (rs.next()) {
					if (rs.getInt("pattern_id") == 3)
						count++;
				}
				ans[2][3] = String.valueOf(count + 1);
				ans[3][2] = String.valueOf(count + 1);
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						System.out.print(ans[i][j]);
					}

					System.out.println();
				}

				request.setAttribute("overlappingmatrix", ans);
				System.out.println("attribute set");
				request.getRequestDispatcher("index.jsp").include(request, response);
				;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

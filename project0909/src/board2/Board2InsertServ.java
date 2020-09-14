package board2;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import common.FileRenamePolicy;

/**
 * Servlet implementation class BoardServ
 */

//파라미터 스트림 값을 바운드리(구분기호)로 잘라서 part배열로 만들어줌
@MultipartConfig(location = "c:/upload", maxRequestSize = 1024 * 1024 * 10)
@WebServlet("/board2/board2Insert.do")
public class Board2InsertServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Board2InsertServ() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/board2/board2Insert.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Board2VO board = new Board2VO();

		board.setSub(request.getParameter("sub"));
		board.setFilename(request.getParameter("filename"));
		board.setId(request.getParameter("id"));

		// 첨부파일처리 부분
		Part part = request.getPart("filename");
		if (part != null) {
			String fileName = getFileName(part);
			String path = request.getServletContext().getRealPath("/images");// "c:/upload";
			System.out.println(path);
			// 파일명 중복체크
			File renameFile = FileRenamePolicy.rename(new File(path, fileName));
			part.write(path + "/" + renameFile.getName());
			board.setFilename(renameFile.getName());			
		}

		Board2DAO dao = new Board2DAO();// board2 DAO 인서트
		dao.insert(board);

	}
	private String getFileName(Part part) throws UnsupportedEncodingException {
		for (String cd : part.getHeader("Content-Disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}

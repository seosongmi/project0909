package board2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class BoardSelectServ
 */
@WebServlet("/Board2SelectServ")
public class Board2SelectServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Board2VO paramVO =  new Board2VO();
	int no = Integer.parseInt(request.getParameter("no"));
	paramVO.setNo(no);
	
	//단건조회
	Board2DAO dao = new Board2DAO();		
	Board2VO board = dao.selectOne(paramVO);
	
	//조회결과 request 저장
	request.setAttribute("board",board);
	
	//view 페이지로 이동(포워드)
	request.getRequestDispatcher("boardUpdate.jsp")
		   .forward(request, response);
	
	}   
    public Board2SelectServ() {
        super();
    }
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		Board2VO board = new Board2VO();
		board.setNo(Integer.parseInt(request.getParameter("no")));
		board.setSub(request.getParameter("sub"));
		//
		
	    
	    Board2DAO dao = new Board2DAO();	
	    
		dao.update(board);
		//목록으로 이동 
		response.sendRedirect("memberSelectAll.do");
	}

	
	

}

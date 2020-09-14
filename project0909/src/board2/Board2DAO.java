package board2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import common.ConnectionManager;

public class Board2DAO {
	private Connection conn;
	private PreparedStatement pstmt;
	
	public ArrayList<Board2VO> selectAll(Board2VO board2VO) {
		Board2VO resultVO = null;
		ResultSet rs = null;
		ArrayList<Board2VO> list = new ArrayList<Board2VO>();
		 try {
			 conn = ConnectionManager.getConnnect();
			 String where = " where 1=1";
			 if(board2VO.getSub() != null) {
				 where += "and sub like '%' || ? || '%'";
			 }
			 if(board2VO.getId() != null) {
				 where += "and id like '%' || ? || '%'";
			 }
			 String sql = "select a.* from ( select rownum rn,b.* from("
					 + " SELECT DEPARTMENT_ID, DEPARTMENT_NAME, MANAGER_ID mgr_id, LOCATION_ID"
			 			+ " FROM HR.DEPARTMENTS"
			 			+ where 
			 		   +" ORDER BY DEPARTMENT_ID"
			 		+  " )b  )a where rn between ? and ? ";
			 pstmt = conn.prepareStatement(sql);
			 int pos = 0;
			 if(board2VO.getSub() != null) {
				 pstmt.setString(pos++, board2VO.getSub());
			 }
			 pstmt.setInt(1, board2VO.getFirst());
			 pstmt.setInt(2, board2VO.getLast());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				resultVO = new Board2VO();
				resultVO.setSub(rs.getString(1));
				resultVO.setId(rs.getString("id"));
//				resultVO.setManager_id(rs.getInt("mgr_id"));
//				resultVO.setLocation_id(rs.getInt("LOCATION_ID"));
				list.add(resultVO);
			} 
		 }catch(Exception e) {
			 e.printStackTrace();		 
		 } finally {
			 ConnectionManager.close(rs, pstmt, conn);
		 }
		return list;
	}
	//단건조회
	public Board2VO selectOne(Board2VO deptVO) {
		 Board2VO resultVO = null;
		 ResultSet rs = null;
		 try {
			 conn = ConnectionManager.getConnnect();
			 String sql = "SELECT *"
			 			+ " FROM board"
			 		   +" order by no";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setInt(1, deptVO.getNo());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				resultVO = new Board2VO();
				resultVO.setNo(rs.getInt("no"));
				resultVO.setSub(rs.getString("sub"));
				resultVO.setFilename(rs.getString("filename"));
				resultVO.setId(rs.getString("id"));
				resultVO.setCode(rs.getInt("code"));
			} else {
				System.out.println("no data");
			}
		 }catch(Exception e) {
			 e.printStackTrace();		 
		 } finally {
			 ConnectionManager.close(rs, pstmt, conn);
		 }
		return resultVO;
	}
	
	//삭제
	public void delete(Board2VO board2VO) {
		try {
			conn = ConnectionManager.getConnnect();
			String sql = "delete departments where department_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board2VO.getSub());
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 삭제됨");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(null, pstmt, conn);
	}
	}
	
	public void update(Board2VO board2VO) {
		try {
			conn = ConnectionManager.getConnnect();
			String sql = "update board2 set id = ? where department_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board2VO.getSub());
			pstmt.setString(2, board2VO.getId());
			int r = pstmt.executeUpdate();
			System.out.println(r + "건이 수정됨");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(null, pstmt, conn);
	}
	}
	public void insert(Board2VO board2VO) {
		try {
		//1.DB연결
		conn = ConnectionManager.getConnnect();
		//2.sql 구문 실행
		String sql = "insert into board2 (sub, id, contents, no)"
				+ " values (   ?,?,?,no.nextval )";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, board2VO.getSub());
		pstmt.setString(2, board2VO.getId());
		pstmt.setString(3, board2VO.getContents());
		
		int r = pstmt.executeUpdate();
		//3.결과 처리
		 
			System.out.println(r+"건이 처리됨");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		//4.연결해제
		ConnectionManager.close(conn);
		}
	}
	
	//전체 건수 
//		public int count(BoardVO boardVO) {
//			int cnt = 0;
//			try {
//				conn = ConnectionManager.getConnnect();
//				 String where = " where 1=1";
//				 if(boardVO.getDepartment_name() != null) {
//					 where += "and department_name like '%' || ? || '%'";
//				 }
//				String sql = "select count(*) from hr.departments" + where;
//				pstmt = conn.prepareStatement(sql);
//				int pos = 1;
//				if(deptVO.getDepartment_name() != null) {
//					pstmt.setString(pos++, deptVO.getDepartment_name());
//				}
//				ResultSet rs = pstmt.executeQuery();
//				rs.next();
//				cnt = rs.getInt(1);
//			}catch(Exception e) {
//				e.printStackTrace();
//			}finally {
//				ConnectionManager.close(conn);
//			}
//			return cnt;
//	}
}



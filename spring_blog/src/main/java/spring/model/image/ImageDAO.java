package spring.model.image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.utility.blog.DBClose;
import spring.utility.blog.DBOpen;

@Repository
public class ImageDAO {
	@Autowired
	private SqlSessionTemplate sqlSession;

	public void viewcnt(int no) {
		sqlSession.update("image.viewcnt", no);

	}

	public boolean checkRefno(int no) {
		boolean flag = false;

		int cnt = sqlSession.selectOne("image.checkRefno", no);
		if (cnt > 0) {
			flag = true;// 부모글이다.
		}

		return flag;
	}

	public void upAno(Map map) {
		sqlSession.update("image.upAno", map);
	}

	public boolean createReply(ImageDTO dto) {
		boolean flag = false;

		int cnt = sqlSession.insert("image.createReply", dto);
		if (cnt > 0) {
			flag = true;
		}
		return flag;

	}

	public ImageDTO readReply(int no) {

		return sqlSession.selectOne("image.readReply", no);
	}

	public int total(String col, String word) {
		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);

		return sqlSession.selectOne("image.total", map);
	}

	public List<ImageDTO> list(Map map) {

		return sqlSession.selectList("image.list", map);
	}

	public boolean passwd(Map map) {
		boolean flag = false;

		int cnt = sqlSession.selectOne("image.passwd", map);
		if (cnt > 0) {
			flag = true;
		}

		return flag;

	}

	public boolean delete(int no) {
		boolean flag = false;

		int cnt = sqlSession.delete("image.delete", no);
		if (cnt > 0) {
			flag = true;
		}

		return flag;
	}

	public boolean update(ImageDTO dto) {
		boolean flag = false;

		int cnt = sqlSession.update("image.update", dto);

		if (cnt > 0) {
			flag = true;
		}

		return flag;

	}

	public boolean updateFile(ImageDTO dto) {
		boolean flag = false;

		int cnt = sqlSession.update("image.updateFile", dto);
		if (cnt > 0) {
			flag = true;
		}

		return flag;

	}

	public Map sumnail(int no) {

		return sqlSession.selectOne("image.sumnail", no);

	}

	public ImageDTO read(int no) {

		return sqlSession.selectOne("image.read", no);

	}

	public boolean create(ImageDTO dto) {
		boolean flag = false;

		int cnt = sqlSession.insert("image.create", dto);
		if (cnt > 0) {
			flag = true;
		}

		return flag;

	}

}

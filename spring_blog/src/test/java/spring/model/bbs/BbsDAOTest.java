package spring.model.bbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class BbsDAOTest {
	private static BeanFactory beans;
	private static BbsDAO bdao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Resource resourec = new ClassPathResource("blog.xml");
		beans = new XmlBeanFactory(resourec);

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bdao = (BbsDAO) beans.getBean("bdao");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testCheckRefno() {
		int bbsno = 1;
		assertTrue(bdao.checkRefno(bbsno));

	}

	@Test
	@Ignore
	public void testTotal() {
		// assertNotNull(bdao.total("", ""));// col,word
		assertEquals(bdao.total("wname", "홍"), 2);
	}

	@Test
	@Ignore
	public void testUpAnsnum() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testCreateReply() {
		BbsDTO dto = bdao.readReply(3);
		dto.setWname("답변자");
		dto.setContent("답변내용");
		dto.setPasswd("1234");
		dto.setFilename("답변");
		dto.setFilesize(10);
		dto.setPasswd("123");
		Map map = new HashMap();
		map.put("grpno", dto.getGrpno());
		map.put("ansnum", dto.getAnsnum());
		bdao.upAnsnum(map);
		assertTrue(bdao.createReply(dto));

	}

	@Test
	@Ignore
	public void testDelete() {
		assertTrue(bdao.delete(1));
	}

	@Test
	@Ignore
	public void testUpdate() {
		BbsDTO dto = new BbsDTO();
		dto.setBbsno(3);
		dto.setWname("홍길동");
		dto.setTitle("제목변경");
		dto.setContent("내용변경");
		dto.setFilename("test");
		dto.setFilesize(123);

		assertTrue(bdao.update(dto));

	}

	@Test
	@Ignore
	public void testPassCheck() {
		Map map = new HashMap();
		map.put("bbsno", 3);
		map.put("passwd", "2");

		assertTrue(bdao.passCheck(map));

	}

	@Test
	@Ignore
	public void testReadReply() {
		BbsDTO dto = bdao.readReply(3);
		assertEquals(dto.getGrpno(), 3);
		assertEquals(dto.getIndent(), 0);
		assertEquals(dto.getAnsnum(), 0);
		assertEquals(dto.getTitle(), "제목변경");

	}

	@Test
	public void testRead() {
		bdao.upViewcnt(300);
		BbsDTO dto = bdao.read(3);
		assertNotNull(dto);
	}

	@Test
	@Ignore
	public void testUpViewcnt() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testList() {
		Map map = new HashMap();
		map.put("col", "wname");
		map.put("word", "");
		map.put("sno", 1);
		map.put("eno",46);
		List<BbsDTO> list = bdao.list(map);
		assertEquals(list.size(), 46);
	}

	@Test
	@Ignore
	public void testCreate() {
		BbsDTO dto = new BbsDTO();
		dto.setWname("홍길동");
		dto.setTitle("test");
		dto.setContent("test");
		dto.setPasswd("123");
		dto.setFilename("test");
		dto.setFilesize(100);
		assertTrue(bdao.create(dto));

	}

}

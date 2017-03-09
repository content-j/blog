package spring.model.memo;

import static org.junit.Assert.*;

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

public class MemoDAOTest {
	private static BeanFactory beans;
	private static MemoDAO mdao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Resource resource = new ClassPathResource("blog.xml");
		beans = new XmlBeanFactory(resource);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mdao = (MemoDAO) beans.getBean("mdao");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testTotal() {
		// assertNotNull(mdao.total("title", "월요일"));
		assertEquals(mdao.total("title", ""), 35);
	}

	@Test
	@Ignore
	public void testUpViewcnt() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testDelete() {
		assertTrue(mdao.delete(613));
	}

	@Test
	@Ignore
	public void testUpdate() {
		MemoDTO dto = new MemoDTO();
		dto.setMemono(613);
		dto.setTitle("변경test");
		dto.setContent("변경test");
		assertTrue(mdao.update(dto));
	}

	@Test
	@Ignore
	public void testRead() {
		mdao.upViewcnt(613);
		MemoDTO dto = mdao.read(613);
		assertNotNull(dto);
	}

	@Test
	@Ignore
	public void testCreate() {
		MemoDTO dto = new MemoDTO();
		dto.setTitle("월요일");
		dto.setContent("내용");
		assertTrue(mdao.create(dto));
	}

	@Test
	public void testList() {
		Map map = new HashMap();
		map.put("col", "title");
		map.put("word", "");
		map.put("sno", 1);
		map.put("eno", 46);
		List<MemoDTO> list = mdao.list(map);
		assertEquals(list.size(), 35);
	}
}

package spring.model.memo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
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

public class ReplyDAO_memoTest {
	private static BeanFactory beans;
	private static ReplyDAO_memo rmdao;

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
		rmdao = (ReplyDAO_memo) beans.getBean("rmdao");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testRcount() {
		assertEquals(rmdao.rcount(608), 1);
	}

	@Test
	@Ignore
	public void testCreate() {
		ReplyDTO_memo rmdto = new ReplyDTO_memo();
		rmdto.setMemono(2);
		rmdto.setId("admin");
		rmdto.setContent("ㅎㅇ");
		assertTrue(rmdao.create(rmdto));
	}

	@Test
	@Ignore
	public void testRead() {
		assertNotNull(rmdao.read(11));
	}

	@Test
	@Ignore
	public void testUpdate() {
		ReplyDTO_memo rmdto = new ReplyDTO_memo();
		rmdto.setRnum(11);
		;
		rmdto.setContent("ㅎㅇㅎㄴ");
		assertTrue(rmdao.update(rmdto));
	}

	@Test
	@Ignore
	public void testList() {
		Map map = new HashMap();
		map.put("col", "content");
		map.put("word", "");
		map.put("sno", 1);
		map.put("eno", 5);
		map.put("memono", 609);

		assertNotNull(rmdao.list(map));
	}

	@Test
	@Ignore
	public void testTotal() {
		assertEquals(rmdao.total(609), 9);
	}

	@Test
	@Ignore
	public void testDelete() {
		assertTrue(rmdao.delete(11));
	}

	@Test
	@Ignore
	public void testBdelete() throws Exception {
		assertEquals(rmdao.bdelete(2), 1);
	}

}

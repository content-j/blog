package spring.sts.blog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.model.memo.MemoDAO;
import spring.model.memo.MemoDTO;
import spring.model.memo.MemoService;
import spring.model.memo.ReplyDAO_memo;
import spring.model.memo.ReplyDTO_memo;
import spring.model2.IReplyDAO;
import spring.utility.blog.Utility;

@Controller
public class MemoController {
	@Autowired
	private MemoDAO dao;

	@Autowired
	private ReplyDAO_memo rdao_memo;

	@Autowired
	private MemoService service; // 추가

	@RequestMapping("/memo/rdelete")
	public String rdelete(int rnum, int memono, int nowPage, int nPage, String col, String word, Model model) {

		int total = rdao_memo.total(memono);// 댓글전체레코드값을 가져와서
		int totalPage = (int) (Math.ceil((double) total / 3)); // 전체 페이지
		if (rdao_memo.delete(rnum)) {
			if (nPage != 1 && nPage == totalPage && total % 3 == 1) {// 마지막페이지의
																		// 마지막레코드이면(3은
																		// 한페이지당보여줄
																		// 레코드
																		// 갯수)
				nPage = nPage - 1; // 현재의 페이지값에서 1을 빼자
			}
			model.addAttribute("memono", memono);
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("nPage", nPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);

		} else {
			return "error/error";
		}

		return "redirect:./read";
	}

	@RequestMapping("/memo/rupdate")
	public String rupdate(ReplyDTO_memo dto, int nowPage, int nPage, String col, String word, Model model) {
		if (rdao_memo.update(dto)) {
			model.addAttribute("memono", dto.getMemono());
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("nPage", nPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
		} else {
			return "error/error";
		}

		return "redirect:./read";
	}

	@RequestMapping("/memo/rcreate")
	public String rcreate(ReplyDTO_memo dto, int nowPage, String col, String word, Model model) {

		if (rdao_memo.create(dto)) {
			model.addAttribute("memono", dto.getMemono());
			model.addAttribute("nowPage", nowPage);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
		} else {
			return "error/error";
		}

		return "redirect:./read";
	}

	@RequestMapping( "/memo/delete")
	public String delete(int memono, MemoDTO dto, Model model, String col, String word, String nowPage) {
		String url = "error";
		try {
			service.delete(memono);
			model.addAttribute("col", col);
			model.addAttribute("word", word);
			model.addAttribute("nowPage", nowPage);
			url = "redirect:./list";

		} catch (Exception e) {
			e.printStackTrace();
			url = "error";
		}
		System.out.println(url);
		return url;

	}

	

	@RequestMapping(value = "/memo/update", method = RequestMethod.POST)
	public String update(MemoDTO dto, Model model, String col, String word, String nowPage) {
		if (dao.update(dto)) {

			model.addAttribute("col", col);
			model.addAttribute("word", word);
			model.addAttribute("nowPage", nowPage);
			return "redirect:./list";
		} else {

			return "error";
		}
	}

	@RequestMapping(value = "/memo/update", method = RequestMethod.GET)
	public String update(int memono, Model model) {
		model.addAttribute("dto", dao.read(memono));

		return "/memo/update";
	}

	@RequestMapping("/memo/read")
	public String read(int nowPage, String col, String word, int memono, Model model, HttpServletRequest request) {

		dao.upViewcnt(memono);
		model.addAttribute("col", col);
		model.addAttribute("word", word);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("dto", dao.read(memono));

		/* 댓글 관련 시작 */
		String url = "read";
		int nPage = 1; // 시작 페이지 번호는 1부터
		String noname = "memono";
		if (request.getParameter("nPage") != null) {
			nPage = Integer.parseInt(request.getParameter("nPage"));
		}
		int recordPerPage = 3; // 한페이지당 출력할 레코드 갯수

		int sno = ((nPage - 1) * recordPerPage) + 1; //
		int eno = nPage * recordPerPage;

		Map map = new HashMap();
		map.put("sno", sno);
		map.put("eno", eno);
		map.put("memono", memono);

		List<ReplyDTO_memo> list = rdao_memo.list(map);

		int total = rdao_memo.total(memono);

		String paging = Utility.paging(noname, total, nPage, recordPerPage, url, memono, nowPage, col, word);

		model.addAttribute("rlist", list);
		model.addAttribute("paging", paging);
		model.addAttribute("nPage", nPage);

		/* 댓글 관련 끝 */

		return "/memo/read";
	}

	@RequestMapping(value = "/memo/create", method = RequestMethod.POST)
	public String create(MemoDTO dto) {

		if (dao.create(dto)) {

			return "redirect:./list";
		} else {

			return "error";
		}

	}

	@RequestMapping(value = "/memo/create", method = RequestMethod.GET)
	public String create() {

		return "/memo/create";
	}

	@RequestMapping("/memo/list")
	public String list(HttpServletRequest request) {

		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));
		if (col.equals("total")) {
			word = "";
		}

		int nowPage = 1;// 현재페이지
		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}
		int recordPerPage = 5;// 한페이지당 보여줄 레코드 갯수

		int sno = ((nowPage - 1) * recordPerPage) + 1;
		int eno = nowPage * recordPerPage;

		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);
		List<MemoDTO> list = dao.list(map);
		int total = dao.total(col, word);
		String paging = Utility.paging3(total, nowPage, recordPerPage, col, word);

		request.setAttribute("list", list);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("paging", paging);
		// list.jsp에서 댓글 갯수 가져올 <util:rcount(num,rdao)>에서 사용할
		// rdao(ReplyDAO)의 값을 request 객체에 담는다.
		IReplyDAO irdao = rdao_memo;
		request.setAttribute("irdao_memo", irdao);

		return "/memo/list";
	}
}

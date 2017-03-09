package spring.sts.blog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import spring.model.member.MemberDAO;
import spring.model.member.MemberDTO;
import spring.utility.blog.Utility;

@Controller
public class MemberController {
	@Autowired
	private MemberDAO dao;

	@RequestMapping(value = "/member/delete", method = RequestMethod.POST)
	public String delete(HttpSession session, String id, String oldfile, HttpServletRequest request) {
		String basePsth = request.getRealPath("/member/storage");

		if (dao.delete(id)) {
			if (!oldfile.equals("member.jpg") && oldfile != null) {
				Utility.deleteFile(basePsth, oldfile);
			}
			session.invalidate();
			return "redirect:../";
		} else {

			return "error";
		}
	}

	@RequestMapping(value = "/member/delete", method = RequestMethod.GET)
	public String delete(Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		String oldfile = dao.getFname(id);
		model.addAttribute("id", id);
		model.addAttribute("oldfile", oldfile);
		return "/member/delete";
	}

	@RequestMapping(value = "/member/updatePw", method = RequestMethod.POST)
	public String updatePw(String id, String passwd, Model model) {
		if (dao.updatePw(id, passwd)) {

			model.addAttribute("id", id);
			return "redirect:./read";
		} else {

			return "error";
		}
	}

	@RequestMapping(value = "/member/updatePw", method = RequestMethod.GET)
	public String updatePw() {

		return "/member/updatePwForm";
	}

	@RequestMapping(value = "/member/updateFile", method = RequestMethod.POST)
	public String updateFile(HttpServletRequest request, MultipartFile fnameMF, String id, String oldfile,
			Model model) {

		String basePath = request.getRealPath("/member/storage");
		int filesize = (int) fnameMF.getSize();
		String fname = "";
		if (filesize > 0) {
			fname = Utility.saveFile(fnameMF, basePath);

		}

		if (dao.updateFile(id, fname)) {
			if (oldfile != null && !oldfile.equals("member.jpg")) {
				Utility.deleteFile(basePath, oldfile);
			}
			model.addAttribute("id", id);
			return "redirect:./read";
		} else {

			return "error";
		}
	}

	@RequestMapping(value = "/member/updateFile", method = RequestMethod.GET)
	public String updateFile() {
		return "/member/updateFileForm";
	}

	@RequestMapping(value = "/member/update", method = RequestMethod.POST)
	public String update(HttpSession session, MemberDTO dto, String col, String word, String nowPage, Model model) {

		String grade = (String) session.getAttribute("grade");
		String id = (String) session.getAttribute("id");

		if (dao.update(dto)) {
			if (nowPage != null && !nowPage.equals("")) {

				model.addAttribute("col", col);
				model.addAttribute("word", word);
				model.addAttribute("nowPage", nowPage);
				return "redirect:../admin/list";
			} else {

				return "redirect:../";
			}
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "/member/update", method = RequestMethod.GET)
	public String update(HttpSession session, Model model, HttpServletRequest request) {

		String id = request.getParameter("id");
		session = request.getSession();
		String grade = (String) session.getAttribute("grade");

		if (id == null) {// 관리자가 아닐때
			id = (String) session.getAttribute("id");
		}

		model.addAttribute("dto", dao.read(id));

		return "/member/update";

	}

	@RequestMapping("/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:../";
	}

	@RequestMapping("/member/email")
	public String email_form() {

		return "member/email_form";
	}

	@RequestMapping("/member/email_proc")
	public String email_proc(String email, Model model) {
		model.addAttribute("email", email);
		model.addAttribute("flag", dao.duplicateEmail(email));
		return "member/email_proc";
	}

	@RequestMapping("/member/id_form")
	public String id_form() {

		return "member/id_form";
	}

	@RequestMapping("/member/id_proc")
	public String id_proc(String id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("flag", dao.duplicateId(id));
		return "member/id_proc";
	}

	@RequestMapping(value = "/member/login", method = RequestMethod.POST)
	public String login(String id, String passwd, String c_id, String no, String nowPage, String nPage, String col,String ino, 
			String word, String bflag, HttpServletResponse response, HttpSession session, Model model) {
		boolean flag = dao.loginCheck(id, passwd);
		String grade = null;// 회원등급
		if (flag) {// 회원인경우
			grade = dao.getGrade(id);
			session.setAttribute("id", id);
			session.setAttribute("grade", grade);
			// ----------------------------------------------
			// Cookie 저장, Checkbox는 선택하지 않으면 null 임
			// ----------------------------------------------
			Cookie cookie = null;

			if (c_id != null) { // 처음에는 값이 없음으로 null 체크로 처리
				cookie = new Cookie("c_id", "Y"); // 아이디 저장 여부 쿠키
				cookie.setMaxAge(120); // 2 분 유지
				response.addCookie(cookie); // 쿠키 기록

				cookie = new Cookie("c_id_val", id); // 아이디 값 저장 쿠키
				cookie.setMaxAge(120); // 2 분 유지
				response.addCookie(cookie); // 쿠키 기록

			} else {
				cookie = new Cookie("c_id", ""); // 쿠키 삭제
				cookie.setMaxAge(0);
				response.addCookie(cookie);

				cookie = new Cookie("c_id_val", ""); // 쿠키 삭제
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			String url = "redirect:/";
			System.out.println(bflag);
			if (bflag != null && !bflag.equals("")) {
				model.addAttribute(ino, no);
				model.addAttribute("nowPage", nowPage);
				model.addAttribute("nPage", nPage);
				model.addAttribute("col", col);
				model.addAttribute("word", word);
				url = "redirect:" + bflag;
			}
			return url;
		} else {
			return "/member/idPwError";
		}
	}

	@RequestMapping(value = "/member/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {

		/*----쿠키설정 내용시작----------------------------*/
		String c_id = ""; // ID 저장 여부를 저장하는 변수, Y
		String c_id_val = ""; // ID 값

		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;

		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];

				if (cookie.getName().equals("c_id")) {
					c_id = cookie.getValue(); // Y
				} else if (cookie.getName().equals("c_id_val")) {
					c_id_val = cookie.getValue(); // user1...
				}
			}
		}
		request.setAttribute("c_id", c_id);
		request.setAttribute("c_id_val", c_id_val);
		/*----쿠키설정 내용 끝----------------------------*/
		return "/member/loginForm";
	}

	@RequestMapping(value = "/member/create", method = RequestMethod.POST)
	public String create(MemberDTO dto, HttpServletRequest request, String id, String email) {
		String basePath = request.getRealPath("/member/storage");
		int filesize = (int) dto.getFileMF().getSize();
		String filename = "";
		String viewPage = "member/prcreateProc";
		String str = null;
		if (dao.duplicateId(id)) {
			str = "중복된 아이디입니다.";
			request.setAttribute("str", str);
		} else if (dao.duplicateEmail(email)) {
			str = "중복된 이메일입니다.";
			request.setAttribute("str", str);
		} else {

			if (filesize > 0) {
				filename = Utility.saveFile(dto.getFileMF(), basePath);

			}
			dto.setFname(filename);
			if (dao.create(dto)) {

				viewPage = "redirect:../";
			} else {
				viewPage = "error";
			}
		}
		return viewPage;
	}

	@RequestMapping(value = "/member/create", method = RequestMethod.GET)
	public String create() {

		return "/member/create";
	}

	@RequestMapping("/member/agree")
	public String agreement() {
		return "/member/agreement";
	}

	@RequestMapping("/member/read")
	public String read(HttpServletRequest request, Model model, MemberDTO dto, HttpSession session) {

		String id = request.getParameter("id");
		// ㄴ관리자가 list에서 id클릭하고 사용자 상세정보 보기
		session = request.getSession();
		// read.jsp에서 회원목록버튼 보여주기위한 권한
		String grade = (String) session.getAttribute("grade");

		// ㄴ일반 사용자가 로그인후 메뉴에서 나의정보메뉴를 눌렀을때 본인정보 확인할때
		if (id == null) {// 관리자가 아닐때
			id = (String) session.getAttribute("id");
		}
		model.addAttribute("id", id);
		model.addAttribute("grade", grade);
		model.addAttribute("dto", dao.read(id));
		return "/member/read";
	}

	@RequestMapping("/admin/list")
	public String list(HttpServletRequest request) {
		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));
		if (col.equals("total")) {
			word = "";
		}

		// 페이징관련
		int nowPage = 1;
		int recordPerPage = 5;
		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}

		// DB에서 가져올 순번
		int sno = ((nowPage - 1) * recordPerPage) + 1;
		int eno = nowPage * recordPerPage;

		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);
		int total = dao.total(col, word);
		List<MemberDTO> list = dao.list(map);

		String paging = Utility.paging3(total, nowPage, recordPerPage, col, word);

		request.setAttribute("list", list);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("paging", paging);

		return "/member/list";
	}
}

package spring.sts.blog;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.model.image.ImageDAO;
import spring.model.image.ImageDTO;
import spring.utility.blog.Utility;

@Controller
public class imageController {
	@Autowired
	private ImageDAO dao;

	@RequestMapping(value = "/image/reply", method = RequestMethod.POST)
	public String reply(ImageDTO dto, Model model, String col, String word, String nowPage,
			HttpServletRequest request) {
		String basePath = request.getRealPath("/image/storage");

		int filesize = (int) dto.getFileMF().getSize();
		System.out.println(filesize);
		String filename = "";
		if (filesize > 0) {
			filename = Utility.saveFile(dto.getFileMF(), basePath);
		}
		dto.setFilename(filename);
		if (dao.createReply(dto)) {
			model.addAttribute("col", col);
			model.addAttribute("word", word);
			model.addAttribute("nowPage", nowPage);

			return "redirect:../";
		} else {

			return "error";
		}
	}

	@RequestMapping(value = "/image/reply", method = RequestMethod.GET)
	public String reply(Model model, int no) {
		model.addAttribute("dto", dao.readReply(no));
		return "image/replyForm";
	}

	@RequestMapping(value = "/image/delete", method = RequestMethod.POST)
	public String delete(int no, HttpServletRequest request, String oldfile, Model model, String col, String word,
			String nowPage,String passwd) {

		String basePsth = request.getRealPath("/image/storage");
		Map map =new HashMap();
		map.put("no", no);
		map.put("passwd", passwd);
		if(dao.passwd(map)){
			if (dao.delete(no)) {

				Utility.deleteFile(basePsth, oldfile);
				model.addAttribute("col", col);
				model.addAttribute("word", word);
				model.addAttribute("nowPage", nowPage);

				return "redirect:../";
			} else {
				return "error";
			}
		}else{
			return "passwdError";
		}
		
	}

	@RequestMapping(value = "/image/delete", method = RequestMethod.GET)
	public String delete(int no, Model model) {

		boolean flag = dao.checkRefno(no);
		model.addAttribute("flag", flag);
		return "/image/deleteForm";

	}

	@RequestMapping(value = "/image/update", method = RequestMethod.POST)
	public String update(String oldfile, HttpServletRequest request, ImageDTO dto, Model model, int no, String passwd,
			String col, String nowPage, String word) {

		Map map = new HashMap();
		map.put("no", no);
		map.put("passwd", passwd);

		String basePath = request.getRealPath("/image/storage");

		int filesize = (int) dto.getFileMF().getSize();
		String filename = "";
		System.out.println(oldfile);
		if (dao.passwd(map)) {

			if (filesize > 0) {
				filename = Utility.saveFile(dto.getFileMF(), basePath);
				dto.setFilename(filename);
				Utility.deleteFile(basePath, oldfile);
			}

			dto.setFilename(oldfile);
			if (dao.update(dto)) {
				model.addAttribute("col", col);
				model.addAttribute("word", word);
				model.addAttribute("nowPage", nowPage);
				return "redirect:./list";

			}

			else {
				return "error";
			}
		} else

		{

			return "passwdError";
		}
	}

	@RequestMapping(value = "/image/update", method = RequestMethod.GET)
	public String update(int no, Model model) {
		model.addAttribute("dto", dao.read(no));
		return "/image/updateForm";
	}

	@RequestMapping(value = "/image/create", method = RequestMethod.POST)
	public String create(ImageDTO dto, HttpServletRequest request) {
		String basePath = request.getRealPath("/image/storage");

		int filesize = (int) dto.getFileMF().getSize();
		System.out.println(filesize);
		String filename = "";
		if (filesize > 0) {
			filename = Utility.saveFile(dto.getFileMF(), basePath);
		}
		dto.setFilename(filename);
		System.out.println("파일이름" + filename);
		if (dao.create(dto)) {
			if (filename != null && filename.equals("")) {
				dto.setFilename(filename);
			}
			String str = dto.getFilename();
			System.out.println("파일111" + str);
			return "redirect:./list";
		} else {
			Utility.deleteFile(basePath, filename);
			return "error";
		}
	}

	@RequestMapping(value = "/image/create", method = RequestMethod.GET)
	public String create() {
		return "/image/createForm";
	}

	@RequestMapping("/image/read")
	public String read(Model model, int no, String col, String word, String nowPage, HttpServletRequest request) {

	ImageDTO dto = dao.read(no);

	Map map = dao.sumnail(no);
	BigDecimal[] noArr = {((BigDecimal)map.get("PRE_NO2")), 
	((BigDecimal)map.get("PRE_NO1")),
	((BigDecimal)map.get("NO")),
	((BigDecimal)map.get("NEX_NO1")),
	((BigDecimal)map.get("NEX_NO2"))
	};

	String[] files = {
	((String)map.get("PRE_FILE2")),
	((String)map.get("PRE_FILE1")),
	((String)map.get("FILENAME")),
	((String)map.get("NEX_FILE1")),
	((String)map.get("NEX_FILE2"))

	};

	String content = dto.getContent();
	content = content.replaceAll("\r\n", "<br>");

	model.addAttribute("col", col);
	model.addAttribute("word", word);
	model.addAttribute("nowPage", nowPage);
	model.addAttribute("dto", dto);
	model.addAttribute("no", no);
	model.addAttribute("files", files);
	model.addAttribute("noArr", noArr);

	return "/image/read";
	}

	@RequestMapping("/image/list")
	public String list(HttpServletRequest request) {

		String col = Utility.checkNull(request.getParameter("col"));
		String word = Utility.checkNull(request.getParameter("word"));
		if (col.equals("total")) {
			word = "";
		}

		int nowPage = 1;
		if (request.getParameter("nowPage") != null)
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		int recordPerPage = 5;

		int total = dao.total(col, word);
		int sno = ((nowPage - 1) * recordPerPage) + 1;
		int eno = nowPage * recordPerPage;

		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);

		List<ImageDTO> list = dao.list(map);

		String paging = Utility.paging3(total, nowPage, recordPerPage, col, word);

		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("totalRecord", total);
		request.setAttribute("recordPerPage", recordPerPage);
		request.setAttribute("list", list);
		request.setAttribute("paging", paging);

		return "/image/list";
	}
}

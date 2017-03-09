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

import spring.model.address.AddressDAO;
import spring.model.address.AddressDTO;
import spring.utility.blog.Utility;

@Controller
public class AddressController {
	@Autowired
	private AddressDAO dao;
	
	@RequestMapping(value = "/address/delete")
	public String delete(int num ,Model model ,String col ,String word,String nowPage){
		if(dao.delete(num)){
			model.addAttribute("col",col);
			model.addAttribute("word",word);
			model.addAttribute("nowPage",nowPage);
			return "redirect:./list";
		}else{
			return "error";
		}
	}

	@RequestMapping(value = "/address/update", method = RequestMethod.POST)
	public String update(AddressDTO dto,Model model, int num ,String col ,String word,String nowPage) {
		if (dao.update(dto)) {
			model.addAttribute("col",col);
			model.addAttribute("word",word);
			model.addAttribute("nowPage",nowPage);
			
			return "redirect:./list";
		} else {
			return "error";
		}
	}

	@RequestMapping(value = "/address/update", method = RequestMethod.GET)
	public String update(Model model, int num ,String col ,String word,String nowPage) {
		model.addAttribute("dto", dao.read(num));
		model.addAttribute("col",col);
		model.addAttribute("word",word);
		model.addAttribute("nowPage",nowPage);
		return "/address/update";
	}

	@RequestMapping(value = "/address/create", method = RequestMethod.GET)
	public String create() {
		return "/address/create";
	}

	@RequestMapping(value = "/address/create", method = RequestMethod.POST)
	public String create(AddressDTO dto,Model model,String col, String word,String nowPage) {
		if (dao.create(dto)) {
			model.addAttribute("col", col);
			model.addAttribute("word", word);
			model.addAttribute("nowPage", nowPage);
			return "redirect:./list";
		} else {
			return "error";
		}
	}

	@RequestMapping("/address/read")
	public String read(int num, Model model,String col, String word,String nowPage) {
		AddressDTO dto = dao.read(num);

		model.addAttribute("dto", dto);
		model.addAttribute("col", col);
		model.addAttribute("word", word);
		model.addAttribute("nowPage", nowPage);

		return "/address/read";

	}

	@RequestMapping("/address/list")
	public String list(HttpServletRequest request, String col, String word) {

		System.out.println("1" + col);
		System.out.println("1" + word);
		col = Utility.checkNull(col);
		word = Utility.checkNull(word);
		if (col.equals("total")) {
			word = "";
		}
		System.out.println("2" + col);
		System.out.println("2" + word);
		int nowPage = 1;
		if (request.getParameter("nowPage") != null) {
			nowPage = Integer.parseInt(request.getParameter("nowPage"));
		}

		int recordPerPage = 5;

		int total = dao.total(col, word);
		int sno = ((nowPage - 1) * recordPerPage) + 1;
		int eno = nowPage * recordPerPage;

		Map map = new HashMap();
		map.put("col", col);
		map.put("word", word);
		map.put("sno", sno);
		map.put("eno", eno);

		String paging = Utility.paging3(total, nowPage, recordPerPage, col, word);

		List<AddressDTO> list = dao.list(map);

		request.setAttribute("list", list);
		request.setAttribute("col", col);
		request.setAttribute("word", word);
		request.setAttribute("paging", paging);
		request.setAttribute("nowPage", nowPage);

		return "/address/list";
	}

}

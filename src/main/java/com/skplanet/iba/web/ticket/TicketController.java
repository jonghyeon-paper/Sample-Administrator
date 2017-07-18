package com.skplanet.iba.web.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skplanet.iba.domain.auth.LoginInfo;
import com.skplanet.iba.domain.ticket.Ticket;
import com.skplanet.iba.domain.ticket.TicketService;
import com.skplanet.iba.framework.data.PagingContents;
import com.skplanet.iba.framework.data.PagingRequest;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	@Autowired
	private TicketService ticketService;
	
	@RequestMapping(value = "/list.do", params = {"page", "countPerPage"})
	@ResponseBody
	public PagingContents<Ticket> selectList(LoginInfo loginInfo, PagingRequest pagingRequest, Ticket ticket){
		return ticketService.selectList(loginInfo.getUser(), pagingRequest, ticket);
	}
	
	@RequestMapping(value = "/info.do")
	@ResponseBody
	public Ticket selectOne(Ticket ticket){
		return ticketService.selectOne(ticket);
	}
	
	@RequestMapping(value = "/listView.do", method = RequestMethod.GET)
	public String listView(){		
		return "/ticket/list.page";
	}
	
	@RequestMapping(value = "/infoView.do", method = RequestMethod.GET)
	public String infoView(){		
		return "/ticket/info.page";
	}
}

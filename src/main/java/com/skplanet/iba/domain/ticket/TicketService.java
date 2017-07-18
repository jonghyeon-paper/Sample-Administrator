package com.skplanet.iba.domain.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skplanet.iba.domain.common.BaseEntityService;

@Service
public class TicketService extends BaseEntityService<Ticket, TicketMapper> {
	@Autowired
	private TicketMapper ticketMapper;
	
	public Ticket selectOne(Ticket parameterTicket) {
		Ticket ticket = ticketMapper.selectOne(parameterTicket);
		ticket.setTicketComments(selectTicketCommentList(ticket.getTicketId()));
		ticket.setTicketAttachments(selectTicketAttachmentList(ticket.getTicketId()));
		return ticket;
	}
	
	public List<TicketComment> selectTicketCommentList(int ticketId){
		return ticketMapper.selectTicketCommentList(ticketId);
	}
	
	public List<TicketAttachment> selectTicketAttachmentList(int ticketId){
		return ticketMapper.selectTicketAttachmentList(ticketId);
	}
}

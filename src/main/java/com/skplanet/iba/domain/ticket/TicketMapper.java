package com.skplanet.iba.domain.ticket;

import java.util.List;

import com.skplanet.iba.domain.common.BaseEntityMapper;

public interface TicketMapper extends BaseEntityMapper<Ticket> {
	List<TicketComment> selectTicketCommentList(int ticketId);
	
	List<TicketAttachment> selectTicketAttachmentList(int ticketId);
}

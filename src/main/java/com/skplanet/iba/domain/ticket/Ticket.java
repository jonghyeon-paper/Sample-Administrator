package com.skplanet.iba.domain.ticket;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skplanet.iba.domain.common.BaseEntity;

@JsonIgnoreProperties({ "regDate", "regUserId", "regUserName", "modDate" , "modUserId", "modUserName"})
public class Ticket extends BaseEntity {
	private int ticketId;
	private String ticketKey;
	private String summary;
	private String description;
	private String issueType;
	private String status;
	private String priority;
	private String resolution;
	private String components;
	private String labels;
	private String creatorKey;
	private String creatorName;
	private String assigneeKey;
	private String assigneeName;
	private String createDate;
	private String updateDate;
	private List<TicketComment> ticketComments;
	private List<TicketAttachment> ticketAttachments;
	
	public int getTicketId() {
		return ticketId;
	}

	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public String getTicketKey() {
		return ticketKey;
	}

	public void setTicketKey(String ticketKey) {
		this.ticketKey = ticketKey;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getComponents() {
		return components;
	}

	public void setComponents(String components) {
		this.components = components;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getCreatorKey() {
		return creatorKey;
	}

	public void setCreatorKey(String creatorKey) {
		this.creatorKey = creatorKey;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getAssigneeKey() {
		return assigneeKey;
	}

	public void setAssigneeKey(String assigneeKey) {
		this.assigneeKey = assigneeKey;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public List<TicketComment> getTicketComments() {
		return ticketComments;
	}

	public void setTicketComments(List<TicketComment> ticketComments) {
		this.ticketComments = ticketComments;
	}

	public List<TicketAttachment> getTicketAttachments() {
		return ticketAttachments;
	}

	public void setTicketAttachments(List<TicketAttachment> ticketAttachments) {
		this.ticketAttachments = ticketAttachments;
	}
}

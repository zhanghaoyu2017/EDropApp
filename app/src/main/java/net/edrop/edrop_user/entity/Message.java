/**
 * @Title: Message.java
 * @Package com.edrop.pojo
 * @Description: 
 * @author 13071
 * @date 2019年12月18日
 * @version V1.0
 */
package net.edrop.edrop_user.entity;

import java.sql.Timestamp;

/**
 * @ClassName: Message
 * @Description: 
 * @author 13071
 * @date 2019年12月18日
 *
 */
public class Message {
	private Integer id;
	private Integer cid;
	private Integer isuserId;
	private Integer isemployeeId;
	private String content;
	private Timestamp sendTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getIsuserId() {
		return isuserId;
	}
	public void setIsuserId(Integer isuserId) {
		this.isuserId = isuserId;
	}
	public Integer getIsemployeeId() {
		return isemployeeId;
	}
	public void setIsemployeeId(Integer isemployeeId) {
		this.isemployeeId = isemployeeId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Message(Integer id, Integer cid, Integer isuserId, Integer isemployeeId, String content,
			Timestamp sendTime) {
		super();
		this.id = id;
		this.cid = cid;
		this.isuserId = isuserId;
		this.isemployeeId = isemployeeId;
		this.content = content;
		this.sendTime = sendTime;
	}
	@Override
	public String toString() {
		return "Message [id=" + id + ", cid=" + cid + ", isuserId=" + isuserId + ", isemployeeId=" + isemployeeId
				+ ", content=" + content + ", sendTime=" + sendTime + "]";
	}
}

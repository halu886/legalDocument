package com.halu.legalDocument.entity;

import java.io.Serializable;

/**
 * 
 * @Description:法律文书实体类 
 * @ClassName: LegalCell
 * @author halu
 * @date 2017年6月5日 下午8:53:12
 */
public class LegalCell implements Serializable{
	
	private static final long serialVersionUID = -867996916707458021L;
	
	private String id;
	
	private Long timeTamp;
	
	private String columnFamily;
	
	private String title;
	
	private String body;

	public LegalCell() {
		super();
	}

	public LegalCell(String id, Long timeTamp, String columnFamily, String title, String body) {
		super();
		this.id = id;
		this.timeTamp = timeTamp;
		this.columnFamily = columnFamily;
		this.title = title;
		this.body = body;
	}

	@Override
	public String toString() {
		return "LegalCell [id=" + id + ", timeTamp=" + timeTamp + ", columnFamily=" + columnFamily + ", title=" + title
				+ ", body=" + body + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LegalCell other = (LegalCell) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getTimeTamp() {
		return timeTamp;
	}

	public void setTimeTamp(Long timeTamp) {
		this.timeTamp = timeTamp;
	}

	public String getColumnFamily() {
		return columnFamily;
	}

	public void setColumnFamily(String columnFamily) {
		this.columnFamily = columnFamily;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}

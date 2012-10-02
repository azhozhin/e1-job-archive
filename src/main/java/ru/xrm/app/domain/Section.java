package ru.xrm.app.domain;

public class Section extends DomainObject{

	// All fields should be protected for DomainObject methods
	protected Long id;
	
	protected String name;
	protected String href;
	
	public Section(){
		this.name="";
		this.href="";
	}
	
	public Section(String name){
		this.name=name;
		this.href="";
	}
	
	public Section(String name, String href){
		this.name=name;
		this.href=href;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public boolean equals(Object o){
		return name.equals((String)o);
	}
}

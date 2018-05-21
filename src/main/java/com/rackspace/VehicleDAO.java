package com.rackspace;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class VehicleDAO {
	
	@Id
	@GeneratedValue
	private Long id;
	private String type;
    private String name;
    public VehicleDAO() {
        super();
    }
    public VehicleDAO(Long id, String type, String name) {
        super();
        this.id = id;
        this.type = type;
        this.name = name;
    }
    
	public VehicleDAO(String type, String name) {
		super();
		this.type = type;
		this.name = name;
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return String.format("Vehicle [id=%s, type=%s, name=%s]", id, type, name);
    }
}

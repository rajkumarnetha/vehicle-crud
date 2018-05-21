package com.rackspace;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDAORepository {
	@Autowired
	JdbcTemplate jdbcTemplate;

	class VehicleDAORowMapper implements RowMapper<VehicleDAO> {
		@Override
		public VehicleDAO mapRow(ResultSet rs, int rowNum) throws SQLException {
			VehicleDAO vehicle = new VehicleDAO();
			vehicle.setId(rs.getLong("id"));
			vehicle.setType(rs.getString("type"));
			vehicle.setName(rs.getString("name"));
			return vehicle;
		}

	}

	public List<VehicleDAO> findAll() {
		return jdbcTemplate.query("select * from vehicle order by id", new VehicleDAORowMapper());
	}

	public VehicleDAO findById(long id) {
		return jdbcTemplate.queryForObject("select * from vehicle where id=?", new Object[] { id },
				new BeanPropertyRowMapper<VehicleDAO>(VehicleDAO.class));
	}
	
	public List<VehicleDAO> findByType(String type) {
		return jdbcTemplate.query("select * from vehicle where type like %?%", new Object[] { type },
				new BeanPropertyRowMapper<VehicleDAO>(VehicleDAO.class));
	}
	
	public List<VehicleDAO> findByName(String name) {
		return jdbcTemplate.query("select * from vehicle where name like %?%", new Object[] { name },
				new BeanPropertyRowMapper<VehicleDAO>(VehicleDAO.class));
	}
	
	public List<VehicleDAO> findByNameAndType(String type,String name) {
		return jdbcTemplate.query("select * from vehicle where type=? and name =?", new Object[] { type,name },
				new BeanPropertyRowMapper<VehicleDAO>(VehicleDAO.class));
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from vehicle where id=?", new Object[] { id });
	}

	public int insert(VehicleDAO vehicle) {
		return jdbcTemplate.update("insert into vehicle (id,type, name) " + "values(?,  ?, ?)",
				new Object[] { vehicle.getId(), vehicle.getType(), vehicle.getName() });
	}

	public int update(VehicleDAO vehicle) {
		System.err.println(vehicle.toString());
		return jdbcTemplate.update("update vehicle " + " set type = ?, name = ? " + " where id = ?",
				new Object[] { vehicle.getType(), vehicle.getName(), vehicle.getId() });
	}
	

}

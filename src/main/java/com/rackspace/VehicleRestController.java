package com.rackspace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class VehicleRestController implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	VehicleDAORepository repository;

	@RequestMapping("/vehicle")
	@ResponseBody
	Object[] getVehicle() {
		return repository.findAll().toArray();
	}

	@RequestMapping(value = "/vehicle", method = RequestMethod.POST)
	@ResponseBody
	String addVehicle(@RequestParam String type, 
            @RequestParam String name) {		
		repository.insert(new VehicleDAO(type, name));
		return "ok";
	}

	@RequestMapping(value = "/vehicle/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	String deleteVehicle(@PathVariable("id") int id) {
		repository.deleteById(id);
		return "ok";
	}

	@RequestMapping(value = "/vehicle/{id}", method = RequestMethod.PUT)
	@ResponseBody
	String updateVehicle(@PathVariable("id") long id, 
            @RequestParam String type, 
            @RequestParam String name) {
		repository.update(new VehicleDAO(id, type, name));
		return "ok";
	}
	
	@RequestMapping(value = "/searchVehicle", method = RequestMethod.POST)
	@ResponseBody
	Object[] searchVehicle(@RequestParam String type, 
            @RequestParam String name) {
		if(name==null || name.isEmpty()) {	
				return repository.findByType(type).toArray();
		}else if(type==null || type.isEmpty()){
			return repository.findByName(name).toArray();
		}else {
			return repository.findByNameAndType(type,name).toArray();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(VehicleRestController.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.info("All vehicle -> {}", repository.findAll());
	}
}

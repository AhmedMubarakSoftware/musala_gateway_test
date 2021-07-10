package com.musala.ahmedTest.practicalTask.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musala.ahmedTest.practicalTask.exceptions.NotAllowedDataException;
import com.musala.ahmedTest.practicalTask.exceptions.NotFoundException;
import com.musala.ahmedTest.practicalTask.modelAssembler.GatewayModelAssembler;
import com.musala.ahmedTest.practicalTask.models.Gateway;
import com.musala.ahmedTest.practicalTask.models.PeripheralDevice;
import com.musala.ahmedTest.practicalTask.services.IGatewayService;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("/api/v1/gateways")
public class GatewayController {

	@Autowired
	private IGatewayService gatewayService;

	@Autowired
	private GatewayModelAssembler assembler;

	// Get all Gateways
	@GetMapping
	public CollectionModel<EntityModel<Gateway>> getAll() {
		List<EntityModel<Gateway>> gateways = gatewayService.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(gateways, linkTo(methodOn(GatewayController.class).getAll()).withSelfRel());
	}

	// Create new Gateway
	@PostMapping
	public ResponseEntity<EntityModel<Gateway>> newGateway(@RequestBody Gateway gateway)
			throws NotAllowedDataException {

		Gateway newGateway = gatewayService.addNewGateway(gateway);
		return ResponseEntity
				.created(linkTo(methodOn(GatewayController.class).getOneById(newGateway.getSerialNum())).toUri())
				.body(assembler.toModel(newGateway));
	}
	
	//update a gateway by it's id with another gateway
	 @PutMapping("/{id}")
	 public EntityModel<Gateway> replaceGateway(@RequestBody Gateway gateway, @PathVariable String serial) throws NotAllowedDataException {
			
		 return assembler.toModel(gatewayService.replaceGateway(gateway,serial));
	  }
	
	// Get one Gateway By Id
	@GetMapping("/{serial}")
	public EntityModel<Gateway> getOneById(@PathVariable String serial) {

		return assembler.toModel(gatewayService.findById(serial));
	}

	// Add a Peripheral device to the Gateway
	@PostMapping("/{serial}/addDevice")
	public ResponseEntity<EntityModel<Gateway>> addNewDevice(@RequestBody PeripheralDevice newDevice,
			@PathVariable String serial) throws NotAllowedDataException, NotFoundException {
		Gateway newGateway = gatewayService.addNewDevice(newDevice, serial);
		return ResponseEntity
				.created(linkTo(methodOn(GatewayController.class).getOneById(newGateway.getSerialNum())).toUri())
				.body(assembler.toModel(newGateway));
	}

	// Delete a gateway with it's serial
	@DeleteMapping("/{serial}")
	public ResponseEntity<?> deleteGateway(@PathVariable String serial) {
		
		gatewayService.deleteById(serial);
		return ResponseEntity.ok().build();
	}
//
	// Delete a device From a Gateway
	@DeleteMapping("/{serial}/deleteDevice/{uid}")
	public ResponseEntity<?> deleteDeviceFromGateway(@PathVariable String serial, @PathVariable Integer uid) {
		
		gatewayService.deleteDeviceFromGatewayById(serial, uid);
		return ResponseEntity.ok().build();
	
	}

}

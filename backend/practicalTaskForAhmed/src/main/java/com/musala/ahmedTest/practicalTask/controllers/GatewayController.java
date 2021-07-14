package com.musala.ahmedTest.practicalTask.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import com.musala.ahmedTest.practicalTask.dtos.GatewayDTO;
import com.musala.ahmedTest.practicalTask.dtos.PeripheralDeviceDTO;
import com.musala.ahmedTest.practicalTask.exceptions.NotAllowedDataException;
import com.musala.ahmedTest.practicalTask.exceptions.NotFoundException;
import com.musala.ahmedTest.practicalTask.modelAssemblers.GatewayModelAssembler;
import com.musala.ahmedTest.practicalTask.models.Gateway;
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
	public CollectionModel<EntityModel<Gateway>> All() {
		List<EntityModel<Gateway>> gateways = gatewayService.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(gateways, linkTo(methodOn(GatewayController.class).All()).withSelfRel());
	}

	// Create new Gateway
	@PostMapping
	public ResponseEntity<EntityModel<Gateway>> create(@RequestBody @Valid GatewayDTO gatewayDTO)
			throws NotAllowedDataException {

		Gateway gateway = gatewayService.addGateway(gatewayDTO);
		return ResponseEntity
				.created(linkTo(methodOn(GatewayController.class).findById(gateway.getSerialNum())).toUri())
				.body(assembler.toModel(gateway));
	}

	// update a gateway by it's id with another gateway
	@PutMapping("/{serial}")
	public EntityModel<Gateway> update(@RequestBody GatewayDTO gatewayDTO, @PathVariable String serial)
			throws NotAllowedDataException {

		return assembler.toModel(gatewayService.update(gatewayDTO, serial));
	}

	// Get one Gateway By Id
	@GetMapping("/{serial}")
	public EntityModel<Gateway> findById(@PathVariable String serial) {

		return assembler.toModel(gatewayService.findById(serial));
	}

	// Add a Peripheral device to the Gateway
	@PostMapping("/{serial}/addDevice")
	public ResponseEntity<EntityModel<Gateway>> addDevice(@RequestBody PeripheralDeviceDTO perDeviceDTO,
			@PathVariable String serial) throws NotAllowedDataException, NotFoundException {
		Gateway newGateway = gatewayService.addDevice(perDeviceDTO, serial);
		return ResponseEntity
				.created(linkTo(methodOn(GatewayController.class).findById(newGateway.getSerialNum())).toUri())
				.body(assembler.toModel(newGateway));
	}

	// Delete a gateway with it's serial
	@DeleteMapping("/{serial}")
	public ResponseEntity<?> delete(@PathVariable String serial) {

		gatewayService.deleteById(serial);
//		return ResponseEntity.ok().build();
		return ResponseEntity.noContent().build();

	}

//
	// Delete a device From a Gateway
	@DeleteMapping("/{serial}/deleteDevice/{uid}")
	public ResponseEntity<?> deleteDevice(@PathVariable String serial, @PathVariable Integer uid) {

		gatewayService.deleteDeviceFromGatewayById(serial, uid);
//		return ResponseEntity.ok().build();
		return ResponseEntity.noContent().build();

	}

}

package com.musala.ahmedTest.practicalTask.modelAssemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.musala.ahmedTest.practicalTask.controllers.GatewayController;
import com.musala.ahmedTest.practicalTask.models.Gateway;

@Component
public class GatewayModelAssembler implements RepresentationModelAssembler<Gateway, EntityModel<Gateway>> {

	@Override
	public EntityModel<Gateway> toModel(Gateway gateway) {

		// basic links for each model
		return EntityModel
				.of(gateway, linkTo(methodOn(GatewayController.class).getOneById(gateway.getSerialNum())).withSelfRel(),
						linkTo(methodOn(GatewayController.class).deleteGateway(gateway.getSerialNum()))
								.withRel("deleteGateway"),
						linkTo(methodOn(GatewayController.class).getAll()).withRel("gateways"));
		//may add other links
//    if (gateway.getPeripheralDevices().size() < 10) {
//    	gatewayModel.add(linkTo(methodOn(GatewayController.class).addNewDevice(gateway.getSerialNum())).withRel("addNewDevice"));
//	}
//		return gatewayModel;
	}

}

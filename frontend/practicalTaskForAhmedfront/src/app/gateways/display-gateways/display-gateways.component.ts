import { Component, OnInit } from '@angular/core';
import { GatewayService } from '../gateway.service';
import { Gateway } from '../../domains/Gateway';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-display-gateways',
  templateUrl: './display-gateways.component.html',
  styleUrls: ['./display-gateways.component.css']
})
export class DisplayGatewaysComponent implements OnInit {
  gateways: Gateway[];
  gateway: any;

  constructor(private gatewayService:GatewayService ,private router: Router) {
    this.gateways=[];
   }


 
  ngOnInit() {
    this.reloadData();
  }

  reloadData() {

    this.gatewayService.getAllGateways().subscribe(
      data => this.gateways = data
      );
      console.log(this.gateways)
  }
 
  getOneGateway(serialNum:string){

    this.gatewayService.getOneGateway(serialNum).subscribe(
          gateway => this.gateway=gateway
        ) ;
        console.log(this.gateway)

      }
  deleteEmployee(serial: string) {
    this.gatewayService.deleteGateway(serial)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  employeeDetails(serial: string){
    this.router.navigate(['details', serial]);
  }

  updateEmployee(id: number){
    this.router.navigate(['update', id]);
  }

}

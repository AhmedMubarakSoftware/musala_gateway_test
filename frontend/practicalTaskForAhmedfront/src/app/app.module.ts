import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {AccordionModule} from 'primeng/accordion';     //accordion and accordion tab
import {MenuItem} from 'primeng/api';                  //api

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DisplayGatewaysComponent } from './gateways/display-gateways/display-gateways.component';
import { AddEditGatewaysComponent } from './gateways/add-edit-gateways/add-edit-gateways.component';
import { GatewayService} from './gateways/gateway.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    DisplayGatewaysComponent,
    AddEditGatewaysComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AccordionModule ,
  HttpClientModule ],
  providers: [GatewayService],
  bootstrap: [AppComponent]
})
export class AppModule { }

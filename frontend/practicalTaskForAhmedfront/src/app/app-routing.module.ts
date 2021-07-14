import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DisplayGatewaysComponent } from './gateways/display-gateways/display-gateways.component';

const routes: Routes = [
  {path:"" , component:DisplayGatewaysComponent},
  {path:"displayGateways" , component:DisplayGatewaysComponent},
  {path:"**" , component:DisplayGatewaysComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

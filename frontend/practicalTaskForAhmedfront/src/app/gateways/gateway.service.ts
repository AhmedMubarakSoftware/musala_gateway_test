import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Gateway } from '../domains/Gateway';
import {map} from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class GatewayService {
  baseUrl:string="http://localhost:8081/api/v1/gateways";

  constructor(private http: HttpClient) { 
  }
  getAllGateways(): Observable<Gateway[]>{

    return this.http.get<GetResponseGateway>(this.baseUrl).pipe(
      map(response => {
console.log(response);
console.log(response._embedded);

console.log(response._embedded.gatewayList);
      return  response._embedded.gatewayList;
      }
    ));
  }

  getOneGateway(serial:string):Observable<Gateway>{

    return this.http.get<Gateway>(`${this.baseUrl}/${serial}`);
  }
 
  addGateway(newGateway: Gateway): Observable<Gateway> {
    return this.http.post<Gateway>(`${this.baseUrl}`, newGateway);
  }

  updateGateway(serial:string, value: any): Observable<Gateway> {
    return this.http.put<Gateway>(`${this.baseUrl}/${serial}`, value);
  }

  deleteGateway(serial:string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${serial}`, { responseType: 'text' });
  }

}
interface GetResponseGateway {
  _embedded: {
    gatewayList: Gateway[]
  }
}
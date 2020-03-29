import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { PlotItem } from "./plot-item.model";
import { HttpClient, HttpHeaders } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PlotService {

  private readonly BASE_URL = "http://localhost:8080";
  private readonly TIMEOUT_GET_PLOT_DATA: number = 10 * 60 * 1000;

  constructor(private http: HttpClient) {
  }

  createPlotItem(): Observable<PlotItem> {
    return this.http.post<PlotItem>(this.BASE_URL + "/average", undefined,
      {headers: new HttpHeaders({readTimeout: `${this.TIMEOUT_GET_PLOT_DATA}`})}
    );
  }

  getPlotData(): Observable<PlotItem[]> {
    return this.http.get<PlotItem[]>(this.BASE_URL + "/average");
  }
}

import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { PlotItem } from "./plot-item.model";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PlotService {

  private readonly BASE_URL = "http://localhost:8080";

  constructor(private http: HttpClient) { }

  createPlotItem(): Observable<PlotItem> {
    return this.http.post<PlotItem>(this.BASE_URL + "/average", undefined);
  }

  getPlotData(): Observable<PlotItem[]> {
    return this.http.get<PlotItem[]>(this.BASE_URL + "/average");
  }
}

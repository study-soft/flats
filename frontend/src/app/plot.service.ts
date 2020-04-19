import { Injectable } from '@angular/core';
import { environment } from "../environments/environment";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs";
import { PlotItem } from "./plot-item.model";
import { parseISO } from 'date-fns';
import { delay, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PlotService {

  private readonly BASE_URL = environment.serverApiUrl;
  private readonly TIMEOUT_GET_PLOT_DATA: number = 10 * 60 * 1000;

  constructor(private http: HttpClient) {
    console.log("production: ", environment.production);
    console.log("PlotService initialized. BASE_URL = " + this.BASE_URL);
  }

  createPlotItem(): Observable<PlotItem> {
    return this.http.post<PlotItem>(this.BASE_URL + "/average", undefined,
      {headers: new HttpHeaders({readTimeout: `${this.TIMEOUT_GET_PLOT_DATA}`})}
    );
  }

  getPlotData(): Observable<PlotItem[]> {
    return this.http.get<PlotItem[]>(this.BASE_URL + "/average")
      .pipe(
        delay(5000),
        map(res => this.convertDateArrayFromServer(res))
      );
  }

  private convertDateFromServer(target: PlotItem): PlotItem {
    if (target && target.date && typeof target.date === "string") {
      target.date = parseISO(target.date);
    }
    return target;
  }

  protected convertDateArrayFromServer(target: PlotItem[]): PlotItem[] {
    if (target) {
      target.forEach(item => {
        this.convertDateFromServer(item);
      });
    }
    return target;
  }
}

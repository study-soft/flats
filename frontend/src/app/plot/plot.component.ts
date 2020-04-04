import { Component, OnInit } from '@angular/core';
import { PlotService } from "../plot.service";
import { PlotItem } from "../plot-item.model";
import * as Chart from "chart.js";
import { add } from 'date-fns'
import { MessageService } from "primeng";

@Component({
  selector: 'app-plot',
  templateUrl: './plot.component.html',
  providers: [MessageService]
})
export class PlotComponent implements OnInit {

  data: Chart.ChartData;
  options: Chart.ChartOptions;

  constructor(private plotService: PlotService) {
  }

  ngOnInit(): void {
    this.populatePlot();

    this.options = {
      scales: {
        xAxes: [{
          type: 'time',
          time: {
            unit: "day",
          },
        }]
      }
    }
  }

  populatePlot() {
    this.plotService.getPlotData()
      .subscribe(data => {
        this.setPlotData(data);
      })
  }

  private setPlotData(data: PlotItem[]) {
    this.data = {
      labels: data?.map(e => e.date),
      datasets: [
        {
          label: 'Price, $',
          data: data?.map(e => e.price),
          fill: true,
          borderColor: '#007ad9',
          backgroundColor: 'rgb(51, 153, 255, 0.5)',
          pointBackgroundColor: '#007ad9'
        }
      ]
    };
  }

  generatePlotItem() {
    this.plotService.createPlotItem()
      .subscribe(item => {
        console.log("generated item: ", item);
      })
  }

  private mockPlotData(): PlotItem[] {
    const now = new Date();
    return [
      {
        date: now,
        price: 1000
      } as PlotItem,
      {
        date: add(now, {days: 1}),
        price: 1200
      } as PlotItem,
      {
        date: add(now, {days: 2}),
        price: 1100
      } as PlotItem,
      {
        date: add(now, {days: 3}),
        price: 1500
      } as PlotItem,
    ]
  }
}

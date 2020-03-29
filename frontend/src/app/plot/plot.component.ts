import { Component, OnInit } from '@angular/core';
import { MessageService } from "primeng/api";
import { PlotService } from "../plot.service";
import { PlotItem } from "../plot-item.model";
import * as Chart from "chart.js";
import { add } from 'date-fns'

@Component({
  selector: 'app-plot',
  templateUrl: './plot.component.html',
  styles: [],
  providers: [MessageService]
})
export class PlotComponent implements OnInit {

  data: Chart.ChartData;
  options: Chart.ChartOptions;

  private plotData: PlotItem[];

  constructor(private messageService: MessageService,
              private plotService: PlotService) {
  }

  ngOnInit(): void {
    // this.plotData = this.mockPlotData();
    this.getPlotData();

    this.data = {
      labels: this.plotData?.map(e => e.date),
      datasets: [
        {
          label: 'Price, $',
          data: this.plotData?.map(e => e.price),
          fill: true,
          borderColor: '#2a2a2a',
          backgroundColor: 'rgba(97,255,66,0.5)',
          pointBackgroundColor: '#2a2a2a'
        }
      ]
    };

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

  selectData(event) {
    this.messageService.add({
      severity: 'info',
      summary: 'Data Selected',
      // @ts-ignore
      'detail': this.data.datasets[event.element._datasetIndex].data[event.element._index]
    });
  }

  generatePlotItem() {
    this.plotService.createPlotItem()
      .subscribe(item => {
        console.log("generated item: ", item);
      })
  }

  getPlotData() {
    this.plotService.getPlotData()
      .subscribe(data => this.plotData = data)
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

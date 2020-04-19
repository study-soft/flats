import { Component, OnInit } from '@angular/core';
import { PlotItem } from '../plot-item.model';
import { add } from 'date-fns';
import { PlotService } from '../plot.service';
import * as Chart from 'chart.js';

@Component({
  selector: 'app-plot',
  templateUrl: './plot.component.html'
})
export class PlotComponent implements OnInit {

  loading = true;

  data: Chart.ChartData;
  options: Chart.ChartOptions;

  constructor(private plotService: PlotService) {
  }

  ngOnInit(): void {
    this.populatePlot();
    // this.populateMockPlotData();

    this.options = {
      aspectRatio: 3,
      scales: {
        xAxes: [{
          type: 'time',
          time: {
            unit: 'day',
          },
        }]
      }
    };
  }

  populatePlot() {
    this.plotService.getPlotData()
      .subscribe(data => {
        this.loading = false;
        this.setPlotData(data);
      });
  }

  private setPlotData(data: PlotItem[]) {
    this.data = {
      labels: data?.map(e => e.date),
      datasets: [
        {
          label: 'Price, $ / m\u00B2',
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
        console.log('generated item: ', item);
      });
  }

  private populateMockPlotData(): void {
    const now = new Date();
    const data: PlotItem[] = [
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
    ];
    this.setPlotData(data);
  }
}

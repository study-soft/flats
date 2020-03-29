import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PlotComponent } from './plot/plot.component';
import { ToastModule } from "primeng/toast";
import { ChartModule } from "primeng/chart";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { ButtonModule } from "primeng/button";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { DEFAULT_TIMEOUT, TimeoutInterceptor } from "./timeout-interceptor";

@NgModule({
  declarations: [
    AppComponent,
    PlotComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ToastModule,
    ChartModule,
    ButtonModule,
    ButtonModule
  ],
  providers: [
    // [
    //   {
    //     provide: HTTP_INTERCEPTORS,
    //     useClass: TimeoutInterceptor, multi: true
    //   }
    // ],
    // [
    //   {
    //     provide: DEFAULT_TIMEOUT, useValue: 30000
    //   }
    // ]
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

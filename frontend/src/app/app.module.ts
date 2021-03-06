import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
    NbActionsModule,
    NbButtonModule,
    NbCardModule,
    NbCheckboxModule, NbIconModule,
    NbInputModule,
    NbLayoutModule, NbSpinnerModule,
    NbThemeModule,
    NbToastrModule, NbToggleModule
} from '@nebular/theme';
import { NbEvaIconsModule } from '@nebular/eva-icons';
import { PlotComponent } from './plot/plot.component';
import { ChartModule } from "angular2-chartjs";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { LoginComponent } from "./auth/components/login/login.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AuthInterceptor } from "./auth/interceptors/auth.interceptor";
import { AuthExpiredInterceptor } from "./auth/interceptors/auth-expired.interceptor";
import { fakeBackendProvider } from "./auth/interceptors/fake-backend.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    PlotComponent,
    LoginComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        NbThemeModule.forRoot({name: 'default'}),
        NbToastrModule.forRoot({duration: 5000}),
        NbLayoutModule,
        NbEvaIconsModule,
        ChartModule,
        NbButtonModule,
        FormsModule,
        NbInputModule,
        NbCheckboxModule,
        NbCardModule,
        ReactiveFormsModule,
        NbIconModule,
        NbActionsModule,
        NbToggleModule,
        NbSpinnerModule,
    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    fakeBackendProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

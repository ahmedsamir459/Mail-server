import { HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule,routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { HttpHandler, HttpClientModule } from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomepageComponent } from './homepage/homepage.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import {ComposeComponent} from './compose/compose.component';
import { ContactComponent } from './contact/contact.component';
import { CommonModule } from '@angular/common';
import { EmailComponent } from './email/email.component';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [
    AppComponent,
   routingComponents,
   HomepageComponent,
   SidebarComponent,
   ContactComponent,
   EmailComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    BrowserAnimationsModule,
    FormsModule,
    NgxPaginationModule,
  ],
  providers: [HttpClient,HomepageComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }

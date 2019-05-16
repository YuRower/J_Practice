import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {routing} from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { AddUserComponent } from './add-user/add-user.component';
import { UserListComponent } from './user-list/user-list.component';
import { EditUserComponent } from './edit-user/edit-user.component';
import { ApiService } from './service/api.service';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TokenInterceptor} from './interceptors/token-interceptor';
import { UserComponent } from './user/user.component';
import { NotFoundComponent } from './not-found/not-found.component'
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AddUserComponent,
    UserListComponent,
    EditUserComponent,
    UserComponent,
    NotFoundComponent
  ],
  imports: [
        FormsModule  ,
    BrowserModule,
    routing,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [ApiService, {provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi : true}],
  bootstrap: [AppComponent]
})
export class AppModule { }

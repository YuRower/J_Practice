import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AddUserComponent} from "./add-user/add-user.component";
import {UserListComponent} from "./user-list/user-list.component";
import {EditUserComponent} from "./edit-user/edit-user.component";
import {UserComponent} from "./user/user.component";

import { NotFoundComponent }   from './not-found/not-found.component';
const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'add-user', component: AddUserComponent },
  { path: 'user-list', component: UserListComponent },
  { path: 'edit-user', component: EditUserComponent },
    {path: 'user-page' , component: UserComponent},
    {path : '', component : LoginComponent},

{ path: '**', component: NotFoundComponent },
];

export const routing = RouterModule.forRoot(routes);

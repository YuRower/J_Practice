import { Component, OnInit , Inject} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../model/user.model";
import { ApiService } from '../service/api.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-list-user',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
   adminName : string;
  users: User[];
 private sub: any;
  constructor(private router: Router, private apiService: ApiService, private route: ActivatedRoute) { }

  ngOnInit() {
    if (!window.localStorage.getItem('token')) {
      this.router.navigate(['login']);
      return;
    }
    this.apiService.getUsers()
      .subscribe( data => {
          this.users = data.user;
      });
        this.sub = this.route
          .queryParams
          .subscribe(params => {
              this.adminName = params['username'];
          });
  }

  deleteUser(user: User): void {
    this.apiService.deleteUser(user.login)
      .subscribe( data => {
        this.users = this.users.filter(u => u !== user);
      })
  }

  editUser(user: User): void {
    window.localStorage.removeItem("editUserLogin");
    window.localStorage.setItem("editUserLogin", user.login.toString());
    this.router.navigate(['edit-user']);
  }

  addUser(): void {
    this.router.navigate(['add-user']);
  }

   logout(): void {
    window.sessionStorage.clear();
    window.localStorage.clear();
    this.router.navigate(['login']);
  }
}

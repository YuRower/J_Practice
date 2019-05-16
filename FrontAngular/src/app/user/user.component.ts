import {Component, OnInit, Inject} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../model/user.model';
import {first} from 'rxjs/operators';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-list-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  login: string;
  private sub: any;
  constructor(private router: Router , private route: ActivatedRoute) {
  }

  
  ngOnInit() {
    this.sub = this.route
      .queryParams
      .subscribe(params => {
        this.login = params['username'] ;
      });
  }

 ngOnDestroy() {
    this.sub.unsubscribe();
  }
   logout(): void {
    window.sessionStorage.clear();
    window.localStorage.clear();
    this.router.navigate(['login'], { replaceUrl: true });
  }
}
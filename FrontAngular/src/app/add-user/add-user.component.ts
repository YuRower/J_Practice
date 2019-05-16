import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ApiService} from "../service/api.service";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,private router: Router, private apiService: ApiService) { }
    roles = [{ id: 1, name: 'User' }, { id: 2, name: 'Amdin' }];
    selectedRole = this.roles[0].name;
  addForm: FormGroup;

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      id: [''],
      login: ['', Validators.required],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', Validators.required],
      birthday: ['', Validators.required],
      roleId: ['', Validators.required]
    });

  }

  onSubmit() {
    this.apiService.createUser(this.addForm.value)
      .subscribe( data => {
        this.router.navigate(['user-list']);
      });
  }

}


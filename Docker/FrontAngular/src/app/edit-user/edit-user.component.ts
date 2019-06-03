import { Component, OnInit, Inject } from '@angular/core';
import { Router } from "@angular/router";
import { User } from "../model/user.model";

import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { first } from "rxjs/operators";
import { ApiService } from "../service/api.service";
import { CommentStmt } from '@angular/compiler';

@Component({
    selector: 'app-edit-user',
    templateUrl: './edit-user.component.html',
    styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

    roles = [{ id: 1, name: 'User' }, { id: 2, name: 'Amdin' }];
    selectedRole = this.roles[0].name;
  
    user: User;
    editForm: FormGroup;
    constructor(private formBuilder: FormBuilder, private router: Router, private apiService: ApiService) { }

    ngOnInit() {
        let userLogin = window.localStorage.getItem("editUserLogin");
        if (!userLogin) {
            alert("Invalid action.")
            this.router.navigate(['user-list']);
            return;
        }
        this.editForm = this.formBuilder.group({

            id: [''],
            login: ['', Validators.required],
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            email: ['', Validators.required],
            password: ['', Validators.required],
            roleId: ['', Validators.required],
            birthday: ['', Validators.required]
        });

        this.apiService.getUserByLogin(userLogin)
            .subscribe(data => {
                console.log(data);
                this.editForm.setValue(data.result);
            });
    }

    onSubmit() {
        this.apiService.updateUser(this.editForm.value)
            .pipe(first())
            .subscribe(
                data => {
                    if (data.status == 204) {
                        alert('User updated successfully.');
                        this.router.navigate(['user-list']);
                    } else {
                        alert(data.message);
                    }
                },
                error => {
                    alert(error);
                });
    }

}

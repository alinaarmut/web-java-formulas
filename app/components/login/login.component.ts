import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DataService} from '../../data.service';
import {Data} from '../../data';
import {Router} from '@angular/router';
import {AngularFireAuth} from '@angular/fire/compat/auth';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  isTextVisible: boolean = false;
  errorMessage: string = '';
  role: string = '';
  email: string = '';

  constructor(public dataLogin: Data, private dataService: DataService, private router: Router) {
  }
  tryLogin() {
    this.dataService.loginUser(this.dataLogin).subscribe(
      (response) => {
        this.isTextVisible = false;
        if (response.statusCode === 200) {
          console.log("ответ, login.component.ts")
          console.log(response)

          sessionStorage.setItem('username', response.userData.username);
          sessionStorage.setItem('email', response.userData.email);
          sessionStorage.setItem('firstName', response.userData.firstName);
          sessionStorage.setItem('role', response.userData.role);
          sessionStorage.setItem('teacherCode', response.userData.teacherCode);

          console.log(sessionStorage);

          if (response.userData.role === 'TEACHER') {
            this.router.navigate(['/teacher']);
          } else if (response.userData.role === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/main']);
          }
        } else {
          this.errorMessage = response.message;
          this.isTextVisible = true;
        }
      },
      (error) => {
        this.errorMessage = 'Something went wrong, try again.';
        this.isTextVisible = true;
      }
    );
  }




}

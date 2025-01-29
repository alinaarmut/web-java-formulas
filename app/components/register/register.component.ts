import { Component } from '@angular/core';
import {Data} from '../../data';
import {DataService} from '../../data.service';
import {Router} from '@angular/router';


@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  username: string = '';
  role: string = '';
  email: string = '';
  isTextVisible: boolean = false;
  errorMessage: string = '';
  name: string = '';
  teacherCode: string = '';



  constructor(public dataRegister: Data, private dataService: DataService, private router: Router) {
  }

  tryRegister() {
    const MIN_PASSWORD_LENGTH = 8;

    if (this.dataRegister.password.length < MIN_PASSWORD_LENGTH) {
      this.errorMessage = `Пароль должен быть более ${MIN_PASSWORD_LENGTH} символов`;
      this.isTextVisible = true;
      return;
    }
    const roleMapping: { [key: string]: string } = {
      student: 'USER',
      teacher: 'TEACHER'
    };
    this.dataRegister.role = roleMapping[this.dataRegister.role];

    if (this.dataRegister.role === 'USER' && this.teacherCode) {
      this.dataRegister.teacherCode = this.teacherCode;
    }


    this.dataService.registerUser(this.dataRegister).subscribe(
      (response) => {
        this.isTextVisible = false;
        if (response.statusCode === 201 || response.statusCode === 200) {
          sessionStorage.setItem('role', response.userData.role);
          sessionStorage.setItem('firstName', response.userData.firstName);
          sessionStorage.setItem('email', response.userData.email);
          sessionStorage.setItem('username', response.userData.username);
          sessionStorage.setItem('teacherCode', response.userData.teacherCode);
          console.log('Data sent successfully', response);



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

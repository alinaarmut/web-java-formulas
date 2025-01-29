import {Component, OnInit} from '@angular/core';
import {Data} from '../../data';
import {DataService} from '../../data.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-main',
  standalone: false,
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit{
  isModerator: boolean = false;


  constructor(public dataRegister: Data, private dataService: DataService, private router: Router) {
  }

  ngOnInit(): void {
    // Проверка роли пользователя
    this.isModerator = this.dataService.getUserRole() === 'MODERATOR';
  }

  navigateProfile(): void {
    this.router.navigate(['/account']);
  }
  navigateExercise2(): void {
    this.router.navigate(['/exerciseTwo']);
  }
  navigateExercise1(): void {
    this.router.navigate(['/exerciseOne']);
  }
  navigateFormula(): void {
    this.router.navigate(['/formulas']);
  }
  logout() {
    this.router.navigate(['/login']);
  }
}

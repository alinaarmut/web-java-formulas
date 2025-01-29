import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {DataService} from '../../data.service';

@Component({
  selector: 'app-personal-account',
  standalone: false,
  templateUrl: './personal-account.component.html',
  styleUrl: './personal-account.component.css'
})
export class PersonalAccountComponent implements OnInit{
  userData: { username: string | null, email: string | null, firstName: string | null, role: string | null } | null = null;
  progress: number = 0;
  subscriptionStatus: string | null = null;
  constructor(private router: Router, private dataService: DataService) {}

  ngOnInit(): void {
    this.userData = this.dataService.getUserData();
    console.log('Данные пользователя:', this.userData);

    if (this.userData && this.userData.username) {
      const username = this.userData.username;

      this.dataService.getSubscriptionStatus(username).subscribe(
        (response) => {
          if (response) {
            this.subscriptionStatus = response.subscriptionStatus;
            console.log('Статус подписки:', this.subscriptionStatus);

            // Если подписка активна, загружаем прогресс
            if (this.subscriptionStatus === 'ACTIVE') {
              this.dataService.getProgress(username).subscribe(
                (progressResponse) => {
                  this.progress = progressResponse;
                  console.log('Прогресс пользователя:', this.progress);
                },
                (error) => {
                  console.error('Ошибка при получении прогресса:', error);
                }
              );
            }
          } else {
            this.subscriptionStatus = 'INACTIVE';
          }
        },
        (error) => {
          console.error('Ошибка при получении статуса подписки:', error);
          this.subscriptionStatus = 'INACTIVE';
        }
      );
    }
  }
  navigateHome() {
    this.router.navigate(['/main']);
  }

  buySubscription(): void {
    if (this.userData && this.userData.username) {
      this.dataService.buySubscription(this.userData.username);
    }
  }

}

import {Component, OnInit} from '@angular/core';
import {EssenseService} from '../../essense.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-admin',
  standalone: false,
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit{
  users: Array<{
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    middleName: string;
    role: string;
  }> = [];

  constructor(private essenseService: EssenseService, private router: Router) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.essenseService.getAllUsers().subscribe(
      (response: any) => {

        if (Array.isArray(response)) {
          console.log('Пользователи загружены:', response);
          this.users = response; // Сохраняем массив пользователей
        } else {
          console.error('Некорректный формат ответа:', response);
          this.users = [];
        }
      },
      (error) => {
        console.error('Ошибка при загрузке пользователей:', error);
      }
    );
  }

  deleteUser(userId: number): void {
    this.essenseService.deleteUser(userId).subscribe(
      () => {
        console.log(`Пользователь с id ${userId} удален`);
        this.users = this.users.filter(user => user.id !== userId); // Удаляем пользователя из списка
      },
      (error) => {
        console.error(`Ошибка при удалении пользователя с id ${userId}:`, error);
      }
    );
  }


  logout(): void {
    this.router.navigate(['/login']);
  }

}

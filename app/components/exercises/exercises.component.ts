import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {DataService} from '../../data.service';


interface Card {
  id: number;
  text: string;
  formula: string;
  selected: boolean;
  matched: boolean;
  incorrect: boolean;
}

@Component({
  selector: 'app-exercises',
  standalone: false,
  templateUrl: './exercises.component.html',
  styleUrl: './exercises.component.css'
})

export class ExercisesComponent implements OnInit {

  constructor(private router: Router, private dataService: DataService) {}

  leftCards = [
    { id: 1, text: 'a² + b² = c²', selected: false, matched: false, incorrect: false },
    { id: 2, text: 'E = mc²', selected: false, matched: false, incorrect: false },
    { id: 3, text: 'F = ma', selected: false, matched: false, incorrect: false }
  ];

  rightCards = [
    { id: 1, text: 'Теорема Пифагора', selected: false, matched: false, incorrect: false },
    { id: 2, text: 'Энергия', selected: false, matched: false, incorrect: false },
    { id: 3, text: 'Второй закон Ньютона', selected: false, matched: false, incorrect: false }
  ];
  selectedLeft: any = null;
  selectedRight: any = null;
  correctPairs = 0;
  totalPairs: number = this.leftCards.length; // Число пар
  remainingAttempts: number = this.totalPairs; // Число оставшихся попыток
  message: string = 'Выберите карточки для соединения.';
  progress: number = 0;


  ngOnInit(): void {
    console.log('Инициализация компонента:');
    console.log('Изначальный прогресс:', this.progress);
    console.log('Оставшиеся попытки:', this.remainingAttempts);
    this.updateProgress();
  }

  selectCard(card: any, side: string) {
    if (this.remainingAttempts <= 0) {
      this.message = 'Упражнение завершено. Попытки закончились.';
      return;
    }

    if (side === 'left') {
      this.selectedLeft = card;
    } else {
      this.selectedRight = card;
    }

    card.selected = true;
    // this.checkMatch();
    if (this.selectedLeft && this.selectedRight) {
      this.remainingAttempts--;
      console.log(`Оставшиеся попытки: ${this.remainingAttempts}`);
      this.checkMatch();
    }
  }

  checkMatch() {
    if (this.selectedLeft && this.selectedRight) {
      if (this.selectedLeft.id === this.selectedRight.id) {
        if (!this.selectedLeft.matched && !this.selectedRight.matched) {
          this.selectedLeft.matched = true;
          this.selectedRight.matched = true;
          this.correctPairs++;
          this.updateProgress();
          this.message = 'Правильно! Вы нашли соответствие.';
        } else {
          this.message = 'Эти карточки уже были соединены. Попробуйте другие.';
        }

        setTimeout(() => {
          this.selectedLeft.selected = false;
          this.selectedRight.selected = false;
          this.selectedLeft = null;
          this.selectedRight = null;
        }, 1000);
      } else {
        // Если пара неправильная
        this.selectedLeft.incorrect = true;
        this.selectedRight.incorrect = true;
        this.message = 'Неправильно. Попробуйте снова.';
        this.updateProgress();

        setTimeout(() => {
          this.selectedLeft.incorrect = false;
          this.selectedRight.incorrect = false;
          this.selectedLeft.selected = false;
          this.selectedRight.selected = false;
          this.selectedLeft = null;
          this.selectedRight = null;
        }, 1000);
      }
    }
  }

  updateProgress() {
    this.progress = Math.round((this.correctPairs / this.totalPairs) * 100);
    console.log(`Обновление прогресса: ${this.correctPairs} правильных пар, прогресс: ${this.progress}%`);
    const username = sessionStorage.getItem('username');
    console.log(this.remainingAttempts)
    if (this.remainingAttempts === 0) {
      console.log(this.remainingAttempts)
      console.log(`Попытки закончились. Отправка прогресса ${this.progress} для пользователя ${username}`);
      if (username) {
        console.log('Данные для отправки на сервер:', { username, progress: this.progress });
        this.dataService.updateProgress(username, this.progress).subscribe(response => {
          if (response) {
            console.log('Прогресс успешно обновлен на сервере');
          }
        });
      } else {
        console.error('Имя пользователя не найдено в сессии');
      }
    }
  }


  navigateHome() {
    this.router.navigate(['/main']);
  }

}

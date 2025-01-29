import {Component, OnInit} from '@angular/core';
import {EssenseService} from '../../essense.service';
import {DataService} from '../../data.service';
import {Data} from '../../data';
import {Router} from '@angular/router';

@Component({
  selector: 'app-teacher',
  standalone: false,
  templateUrl: './teacher.component.html',
  styleUrl: './teacher.component.css'
})
export class TeacherComponent implements OnInit {
  progressMap: Map<string, any> = new Map();
  teacherCode: string = '';
  students: Array<any> = [];
  userData: { username: string | null, email: string | null, firstName: string | null, role: string | null, teacherCode: string | null} | null = null;
  constructor(private essenseService: EssenseService, private dataService: DataService,
              private router: Router) {}


  ngOnInit(): void {

    const teacherCode = sessionStorage.getItem("teacherCode")
      console.log(teacherCode)

    this.userData = this.dataService.getUserData();


    if (this.userData?.teacherCode) {
      this.teacherCode = this.userData.teacherCode;
    }
    this.loadStudents();
  }

  loadStudents() {
    this.essenseService.getStudentsByTeacherCode(this.teacherCode).subscribe(
      (response) => {
        console.log('Студенты загружены:', response);
        this.students = response.userData?.students || [];
        console.log('Студенты до фильтрации:', this.students);
        // Фильтруем студентов, исключая тех, у кого роль 'TEACHER'
        this.students = this.students.filter(student => student.role !== 'TEACHER');


        this.students.forEach(student => {
          this.getProgress(student.username);
        });
      },
      (error) => {
        console.error('Ошибка при загрузке студентов:', error);
      }
    );
  }


  getProgress(username: string): void {
    this.dataService.getProgress(username).subscribe(
      (progress) => {
        this.progressMap.set(username, progress);
      },
      (error) => {
        console.error('Ошибка при получении прогресса для пользователя', username, error);
      }
    );
  }

  getStudentProgress(username: string): string {
    const progress = this.progressMap.get(username);
    return progress ? `${progress}%` : 'Не доступен';
  }

  logout() {
    this.router.navigate(['/login']);
  }
}

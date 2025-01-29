import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChatService} from './chat.service';
import {EssenseService} from '../../essense.service';
import {Observable} from 'rxjs';
@Component({
  selector: 'app-chat',
  standalone: false,
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent implements OnInit, OnDestroy {

  messageText: string = '';
  messages: any[] = [];
  selectedReceiver: string = '';
  users: Array<{
    id: number;
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    middleName: string;
    role: string;
  }> = [];
  teacherCode: string = '';
  senderUsername: string = '';

  constructor(private chatService: ChatService, private essenseService: EssenseService ) {}

  ngOnInit(): void {

    this.teacherCode= sessionStorage.getItem('teacherCode') || '';
    this.chatService.subscribeToMessages(this.teacherCode).subscribe(message => {
      console.log('Получено сообщение:', message);
      this.messages.push(message);
    });
    this.loadUsers();
  }

  // ngOnInit(): void {
  //
  //   console.log(this.selectedReceiver)
  //     this.chatService.subscribeToMessages(this.selectedReceiver).subscribe(message => {
  //       console.log('Получено сообщение:', message);
  //       this.messages.push(message);
  //     });
  //
  //   this.loadUsers();
  // }



  sendMessage(): void {
    if (this.messageText.trim()) {
      const message = {
        messageText: this.messageText,
        receiverUsername: this.selectedReceiver,
        teacherCode: this.teacherCode

      };
      this.chatService.sendMessage(message);
      console.log('отправленное сообщение на сервер', message)
      this.messageText = '';
    }
  }


  // sendMessage(): void {
  //   if (this.messageText.trim() && this.selectedReceiver) {
  //     const message = {
  //       messageText: this.messageText,
  //       receiverUsername: this.selectedReceiver, // Указываем получателя
  //     };
  //     this.chatService.sendMessage(message);
  //     console.log('Отправленное сообщение:', message);
  //     this.messageText = ''; // Очищаем поле ввода
  //   } else {
  //     console.warn('Не указан получатель сообщения или сообщение пустое');
  //   }
  // }


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
  ngOnDestroy(): void {
    this.chatService.disconnect();
  }

}

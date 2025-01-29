import { Injectable } from '@angular/core';
import { Client, IStompSocket } from '@stomp/stompjs';
import { Observable } from 'rxjs';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private client: Client;
  private isConnected: boolean = false;
  error: string = '';
  username: string = '';

  constructor() {

    this.client = new Client();


    this.client.webSocketFactory = () => new SockJS('http://localhost:8083/ws') as unknown as IStompSocket;
    this.client.connectHeaders = {
      'Authorization': 'Bearer '
    };


    this.client.onConnect = (frame) => {
      console.log('Connected to WebSocket', frame);
      this.isConnected = true;
      // this.subscribeToMessages();
    };


    this.client.onStompError = (frame) => {
      console.error('STOMP Error', frame);
    };

    this.client.activate();
  }


  sendMessage(message: any) {
    if (this.client.connected) {
      this.client.publish({
        destination: '/app/sendMessage',
        body: JSON.stringify(message)
      });2
    } else {
      console.error('WebSocket не подключен', this.error);
    }
  }

  // sendMessage(message: any) {
  //   if (this.client.connected) {
  //     const destination = `/app/sendMessage`; // Личный канал пользователя
  //     this.client.publish({
  //       destination: destination,
  //       body: JSON.stringify(message),
  //     });
  //   } else {
  //     console.error('WebSocket не подключен', this.error);
  //   }
  // }
  // sendMessage(message: any) {
  //   if (this.client.connected) {
  //     const destination = `/app/sendMessage`; // Путь, соответствующий @MessageMapping на сервере
  //     this.client.publish({
  //       destination: destination,
  //       body: JSON.stringify(message),
  //     });
  //     console.log(`Сообщение отправлено на сервер: ${destination}`);
  //   } else {
  //     console.error('WebSocket не подключен');
  //   }
  // }


  // subscribeToMessages(): Observable<any> {
  //   return new Observable(observer => {
  //     // Ждем, пока клиент подключится, и только потом подписываемся на тему
  //     const checkConnectionInterval = setInterval(() => {
  //       if (this.isConnected) {
  //         clearInterval(checkConnectionInterval);  // Останавливаем проверку после успешного подключения
  //         this.client.subscribe('/topic/messages', (message) => {
  //           console.log('Сообщение получено от сервера:', message.body);
  //           observer.next(JSON.parse(message.body));  // Парсим сообщение и передаем в Observable
  //         });
  //       }
  //     }, 100);  // Проверяем каждые 100 мс
  //   });
  // }
  subscribeToMessages(teacherCode: string): Observable<any> {
    return new Observable(observer => {
      const checkConnectionInterval = setInterval(() => {
        if (this.isConnected) {
          clearInterval(checkConnectionInterval);

          const topic = `/topic/messages/${teacherCode}`; // Канал для конкретного учителя
          this.client.subscribe(topic, (message) => {
            console.log('Сообщение получено от сервера:', message.body);
            observer.next(JSON.parse(message.body));
          });
        }
      }, 100);
    });
  }



  // subscribeToMessages(selectedReceiver: string): Observable<any> {
  //   console.log(selectedReceiver);
  //   return new Observable(observer => {
  //     const checkConnectionInterval = setInterval(() => {
  //       if (this.isConnected) {
  //         clearInterval(checkConnectionInterval);
  //         console.log(selectedReceiver);
  //         const destination = `/user/student1/queue/messages`;
  //         console.log(`Подписка на канал: ${destination}`);
  //         this.client.subscribe(destination, (message) => {
  //           console.log('Сообщение получено:', message.body);
  //           observer.next(JSON.parse(message.body));
  //         });
  //       }
  //     }, 100);
  //   });
  // }



  // Отключение от WebSocket
  disconnect() {
    this.client.deactivate();
  }
}

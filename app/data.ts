import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})

export class Data {
  username: string = "";
  password: string = "";
  role: string = "";
  email: string = "";
  firstName: string = "";
  lastName: string = "";
  middleName: string = "";
  teacherCode: string = "";

}

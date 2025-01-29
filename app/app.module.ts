import { NgModule } from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import {MainComponent} from './components/main/main.component';
import {PersonalAccountComponent} from './components/personal-account/personal-account.component';
import {ChatComponent} from './components/chat/chat.component';
import {ExercisesComponent} from './components/exercises/exercises.component';
import {RegisterComponent} from './components/register/register.component';
import {LoginComponent} from './components/login/login.component';
import { AppRoutingModule } from './app-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {FormulasComponent} from './components/formulas/formulas.component';
import {ExerciseTwoComponent} from './components/exercise-two/exercise-two.component';
import {AngularFireModule} from '@angular/fire/compat';
import {AngularFireAuthModule} from '@angular/fire/compat/auth';
import {TeacherComponent} from './components/teacher/teacher.component';
import {AdminComponent} from './components/admin/admin.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    MainComponent,
    PersonalAccountComponent,
    ChatComponent,
    ExercisesComponent,
    FormulasComponent,
    ExerciseTwoComponent,
    TeacherComponent,
    AdminComponent

  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}



import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { MainComponent } from './components/main/main.component';
import { PersonalAccountComponent } from './components/personal-account/personal-account.component';
import { ExercisesComponent } from './components/exercises/exercises.component';
import {FormulasComponent} from './components/formulas/formulas.component';
import {ExerciseTwoComponent} from './components/exercise-two/exercise-two.component';
import {AdminComponent} from './components/admin/admin.component';
import {TeacherComponent} from './components/teacher/teacher.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'main', component: MainComponent },
  { path: 'account', component: PersonalAccountComponent },
  { path: 'exerciseOne', component: ExercisesComponent },
  { path: 'formulas', component: FormulasComponent },
  { path: 'exerciseTwo', component: ExerciseTwoComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'teacher', component: TeacherComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

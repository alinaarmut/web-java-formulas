import {AfterViewChecked, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {DataService} from '../../data.service';
import {EssenseService} from '../../essense.service';
import {Formula} from '../../formula.module';


declare var MathJax: any;


@Component({
  selector: 'app-formulas',
  templateUrl: './formulas.component.html',
  styleUrl: './formulas.component.css',
  standalone: false
})
export class FormulasComponent implements OnInit {
  constructor(
    private router: Router,
    private dataService: DataService,
    private essenceService: EssenseService,
    private cdr: ChangeDetectorRef // Добавлено для рендеринга после изменения данных
  ) {}

  mathFormulas: Formula[] = [];
  geometryFormulas: Formula[] = [];
  physicsFormulas: Formula[] = [];

  mathJaxRendered = false;
  isModerator: boolean = false;

  ngOnInit(): void {
    this.isModerator = this.dataService.getUserRole() === 'MODERATOR';
    this.loadFormulas();
  }

  loadFormulas(): void {
    this.essenceService.getFormulasByType('Математика').subscribe((data) => {
      this.mathFormulas = data;
      this.renderMathJax();
    });

    this.essenceService.getFormulasByType('Геометрия').subscribe((data) => {
      this.geometryFormulas = data;
      this.renderMathJax();
    });

    this.essenceService.getFormulasByType('Физика').subscribe((data) => {
      this.physicsFormulas = data;
      this.renderMathJax();
    });
  }

  renderMathJax(): void {

    this.cdr.detectChanges();
    if (window.MathJax) {
      MathJax.typesetPromise().then(() => {
        this.mathJaxRendered = true;
        console.log('MathJax rendering complete');
      }).catch((err: any) => {
        console.error('MathJax rendering failed', err);
      });
    }
  }

  navigateHome(): void {
    this.router.navigate(['/main']);
  }

  deleteCard(formula: Formula, category: string): void {
    console.log('Card to delete:', formula);

    this.essenceService.deleteFormula(formula, category).subscribe(
      () => {
        // После успешного удаления на сервере обновляем локальные данные
        switch (category) {
          case 'Математика':
            this.mathFormulas = this.mathFormulas.filter((f) => f !== formula);
            break;
          case 'Геометрия':
            this.geometryFormulas = this.geometryFormulas.filter((f) => f !== formula);
            break;
          case 'Физика':
            this.physicsFormulas = this.physicsFormulas.filter((f) => f !== formula);
            break;
          default:
            console.error('Unknown category:', category);
        }

        // Перерисовать MathJax для оставшихся формул
        this.renderMathJax();
      },

    );
  }

}

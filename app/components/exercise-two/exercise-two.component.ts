import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';


declare const MathJax: any;
@Component({
  selector: 'app-exercise-two',
  standalone: false,
  templateUrl: './exercise-two.component.html',
  styleUrl: './exercise-two.component.css'
})
export class ExerciseTwoComponent implements OnInit{
  constructor(private router: Router,  private cdr: ChangeDetectorRef) {}


  cardData = [
    { formula: 'a^2 + b^2 = c^2', description: 'Теорема Пифагора', isFlipped: false },
    { formula: 'E = mc^2', description: 'Энергия тела в покое', isFlipped: false },
    { formula: 'F = ma', description: 'Второй закон Ньютона', isFlipped: false },
    { formula: 'V = IR', description: 'Закон Ома', isFlipped: false },
    { formula: '\\log_a(x) = \\frac{\\ln(x)}{\\ln(a)}', description: 'Логарифм по основанию a', isFlipped: false },
    { formula: '\\pi r^2', description: 'Площадь круга', isFlipped: false }
  ];


  resultsVisible = false;
  correctCount = 0;
  totalCards = this.cardData.length;
  currentCardIndex = 0;
  currentCard = this.cardData[this.currentCardIndex];
  mathJaxRendered = false;
  ngOnInit(): void {
    this.renderMathJax();
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


  nextCard() {
    if (this.currentCardIndex < this.cardData.length - 1) {
      this.currentCardIndex++;
      this.currentCard = this.cardData[this.currentCardIndex];
      this.renderMathJax();
    }
  }

  prevCard() {
    if (this.currentCardIndex > 0) {
      this.currentCardIndex--;
      this.currentCard = this.cardData[this.currentCardIndex];
      this.renderMathJax();
    }
  }

  flipCard(card: any) {
    if (!card.isFlipped) {
      this.correctCount++;
    }
    card.isFlipped = !card.isFlipped;
    this.renderMathJax();
  }

  navigateHome() {
    this.router.navigate(['/main']);
  }
}

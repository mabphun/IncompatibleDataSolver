import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ButtonModule } from "primeng/button";

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [ButtonModule, RouterLink],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.scss'
})
export class NavigationComponent {

  constructor(
    private router: Router
  ){}

}

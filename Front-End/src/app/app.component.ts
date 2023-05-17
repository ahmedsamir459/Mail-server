import { Component } from '@angular/core';
import { EmailValidator } from '@angular/forms';
import { ApiserveService } from './service';
import { HttpClient } from '@angular/common/http';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'mail_server';
  
  constructor(private drawit : ApiserveService, private http: HttpClient, private modalservice: NgbModal,private router:Router) {
  this.router.navigate(["/SignIn"])
  }

  
}

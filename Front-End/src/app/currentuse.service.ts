import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { Mail } from './mail';
import { ApiserveService } from './service';

@Injectable({
  providedIn: 'root'
})
export class CurrentuseService {
  currentuser='';
  currfolder2:string='';
  currentaccount:string='';
  currentmails:Mail[]=[]
  currentmail!:Mail;
  currfolder:string="";
  currentfolders:any[]=[]
  draftedmail!:Mail;
  attachedFile: File[] = []
  attachedFileName: String[] = []
  attachedFileUrl: any[] = []   
  Reply!: string;
  page=1
  constructor(private modalservice:NgbModal,private service:ApiserveService,private http:HttpClient,private router:Router) { }
  reload(){

    this.service.reload(this.currentuser)
  }
}
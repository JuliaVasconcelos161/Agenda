import { Component, OnInit } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from './contact';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit{

  constructor(
    private service: ContactService
  ) {}

  ngOnInit(): void {
    const c: Contact = new Contact();
    c.name = 'Jo';
    c.email = 'jo@email.com';
    c.favorite = false;

    this.service.saveContact(c).subscribe( response => {
      console.log(response);
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from './contact';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit{

  form!: FormGroup;
  contacts: Contact[] = [];
  columns = ['id', 'name', 'email', 'favorite']
  constructor(
    private service: ContactService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.formComponent();
    
    this.listContacts();
 
  }

  listContacts() {
    this.service.listContactsService().subscribe(response => {
      this.contacts = response;
    })
  }

  formComponent() {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    });
  }

  submit() {
    const formValues = this.form.value;
    const contact: Contact = new Contact(formValues.name, formValues.email);
    this.service.saveContact(contact).subscribe( response => {
      this.contacts.push(response);
      console.log(this.contacts);
    });
  }
}

import { Component, OnInit } from '@angular/core';
import { ContactService } from '../contact.service';
import { Contact } from './contact';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ContactDetailsComponent } from '../contact-details/contact-details.component';
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit{

  form!: FormGroup;
  contacts: Contact[] = [];
  columns = ['picture','id', 'name', 'email', 'favorite']
  constructor(
    private service: ContactService,
    private fb: FormBuilder,
    private dialog: MatDialog
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

  favoriteContact(contact: Contact) {
    this.service.favoriteContactService(contact).subscribe(response => {
      contact.favorite = !contact.favorite;
    });   
  }

  submit() {
    const formValues = this.form.value;
    const contact: Contact = new Contact(formValues.name, formValues.email);
    this.service.saveContact(contact).subscribe( response => {
      let listContacts: Contact[] = [...this.contacts, response];
      this.contacts = listContacts;
    });
  }

  uploadPicture(event: any, contact: Contact) {
    const files = event.target.files;
    if(files) {
      const picture = files[0];
      const formData: FormData = new FormData();
      formData.append("picture", picture);
      this.service.uploadPictureService(contact, formData).subscribe(response => {
        this.listContacts();
      });
    }
  }

  visualizeContact(contact: Contact) {
    this.dialog.open(ContactDetailsComponent, {
      width:'400px',
      height: '450px',
      data: contact
    })
  }
}

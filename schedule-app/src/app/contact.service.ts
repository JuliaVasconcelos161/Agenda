import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Contact } from './contact/contact';
import { environment } from 'src/environment/environment';
@Injectable({
  providedIn: 'root'
})
export class ContactService {

  url: string = environment.apiBaseUrl;


  constructor(
    private http: HttpClient
  ) { }

  saveContact (contact: Contact) : Observable<Contact> {
    return this.http.post<Contact>(this.url, contact);
  }

  listContactsService(): Observable<Contact[]> {
    return this.http.get<any>(this.url);
  }
}

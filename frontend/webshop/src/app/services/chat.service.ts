import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environment/environment';
import { ChatResponse } from '../models/chat-response';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) { }

  public chat(message: string): Observable<ChatResponse> {
    return this.http.get<ChatResponse>('http://localhost:8083/api/assistant/chat', {
      params: {
        message: message
      }
    });
  }
}

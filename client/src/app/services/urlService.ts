import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ShortUrlDTO } from '../model/ShortUrlDTO';
import { UrlDTO } from '../model/UrlDTO';

@Injectable({
  providedIn: 'root'
})
export class UrlService {
  private apiUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }


  public shortUrl(urlDTO: UrlDTO): Observable<ShortUrlDTO>{
    return this.http.post<UrlDTO>(this.apiUrl+'/shorten', urlDTO)
  }

}
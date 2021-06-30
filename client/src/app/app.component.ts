import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ShortUrlDTO } from './model/ShortUrlDTO';
import { UrlDTO } from './model/UrlDTO';
import { UrlService } from './services/urlService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  urlDto: UrlDTO;
  shortUrl: ShortUrlDTO;


  constructor(private urlService: UrlService ) { 
    this.urlDto = new UrlDTO("",null);
    this.shortUrl = new ShortUrlDTO("");
  }

  ngOnInit(){

  }

  public onSubmit(){
    this.urlService.shortUrl(this.urlDto).subscribe(
      (response: ShortUrlDTO) =>{
        alert('URL Shortened !')
        this.shortUrl= response;
      },
      (error: HttpErrorResponse) =>{
        if (error.status == 302){
          alert("The ALIAS currently exists, please write another one")
        }else if(error.status == 400){
          alert("The URL is invalid")
        }
      } 
    )
  }

  public clearFields(){
    this.urlDto = new UrlDTO("",null);
    this.shortUrl = new ShortUrlDTO("");
  }

}

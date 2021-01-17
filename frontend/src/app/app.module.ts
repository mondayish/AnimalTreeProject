import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {MatTreeModule} from '@angular/material/tree';
import {MatIconModule} from '@angular/material/icon';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {CdkTreeModule} from '@angular/cdk/tree';

@NgModule({
    declarations: [
        AppComponent
    ],
    imports: [
        BrowserModule,
        MatTreeModule,
        MatIconModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatButtonModule,
        FormsModule,
        ReactiveFormsModule,
        CdkTreeModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}

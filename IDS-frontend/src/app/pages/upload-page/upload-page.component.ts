import { Component } from '@angular/core';
import { FileUploadHandlerEvent, FileUploadModule } from 'primeng/fileupload';
import { ProductService } from '../../shared/services/product.service';
import { NgForOf, NgIf } from '@angular/common';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
  selector: 'app-upload-page',
  standalone: true,
  imports: [
    FileUploadModule,
    NgForOf,
    NgIf,
    ProgressSpinnerModule
],
  templateUrl: './upload-page.component.html',
  styleUrl: './upload-page.component.scss'
})
export class UploadPageComponent {

  errors: string[] = []
  success: boolean = false
  loading: boolean = false

  constructor(
    private productService: ProductService
  ){}

  onUpload($event: FileUploadHandlerEvent){
    this.errors = []
    this.success = false
    const file: File = $event.files[0];
    const formData = new FormData();
    formData.append('file', file, file.name);

    this.loading = true
    this.productService.uploadFile(formData).subscribe({
      next: (res: string[]) => {
        this.loading = false
        this.success = true
      },
      error: (err: any) => {
        this.loading = false
        err.error.map((e: string) => {
          this.errors.push(e)
        })
      },
    })
  }
}

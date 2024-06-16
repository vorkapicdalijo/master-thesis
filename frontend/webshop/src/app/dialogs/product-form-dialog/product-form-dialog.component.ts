import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle,
} from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { SizePrice } from '../../models/size-price';
import { ProductNote } from '../../models/product-note';
import { Category } from '../../models/category';
import { Brand } from '../../models/brand';
import { Note } from '../../models/note';
import { NoteType } from '../../models/note-type';
import { Type } from '../../models/type';
import { SelectService } from '../../services/select.service';
import { Product } from '../../models/product';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { ProductService } from '../../services/product.service';
import { environment } from '../../../environment/environment';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { State } from '@popperjs/core';
import { Observable, map, startWith } from 'rxjs';
import { AsyncPipe } from '@angular/common';

export interface DialogData {
  isEdit: boolean;
  product?: Product;
}

@Component({
  selector: 'app-product-form-dialog',
  standalone: true,
  imports: [
    MatButtonModule,
    MatDialogActions,
    MatDialogClose,
    MatDialogTitle,
    MatDialogContent,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatIconModule,
    MatDividerModule,
    MatAutocompleteModule,
    AsyncPipe,
  ],
  templateUrl: './product-form-dialog.component.html',
  styleUrl: './product-form-dialog.component.scss',
})
export class ProductFormDialogComponent implements OnInit{
  isEdit: boolean = false;

  productForm: FormGroup;
  sizePriceForm: FormGroup;
  productNoteForm: FormGroup;
  product: Product;

  categories: Category[] = [];
  brands: Brand[] = [];
  types: Type[] = [];

  filteredCategories: Observable<Category[]>;
  filteredBrands: Observable<Brand[]>;
  filteredTypes: Observable<Type[]>;
  filteredNotes: Observable<Note[]>;

  notes: Note[] = [];
  noteTypes: NoteType[] = [];

  sizePrices: SizePrice[] = [];
  productNotes: ProductNote[] = [];

  selectedFile: File | null = null;
  containsImage: boolean = false;
  imgSrc: string = "";
  initialProductEditFormValue: any;


  constructor(
    private fb: FormBuilder,
    private selectService: SelectService,
    public dialogRef: MatDialogRef<ProductFormDialogComponent>,
    private productService: ProductService,
    @Inject(MAT_DIALOG_DATA) public data: DialogData
  ) {
    if (this.data) {
      this.isEdit = this.data.isEdit;
    }
  }
  ngOnInit(): void {
    this.productForm = this.fb.group({
      name: new FormControl('', Validators.required),
      type: new FormControl('', Validators.required),
      brand: new FormControl('', Validators.required),
      category: new FormControl('', Validators.required),
      description: new FormControl('',),
      amount: new FormControl('',  Validators.required)
    });

    this.sizePriceForm = this.fb.group({
      size: new FormControl('', Validators.required),
      price: new FormControl('', Validators.required),
    });

    this.productNoteForm = this.fb.group({
      note: new FormControl('', Validators.required),
      noteTypeId: new FormControl('', Validators.required),
    });


    this.selectService.getBrands().subscribe(brands => {
      this.brands = brands;

      this.filteredBrands = this.productForm.get('brand')!.valueChanges.pipe(
        startWith(''),
        map(brand => (brand ? this._filterBrands(brand.name ? brand.name : brand) : this.brands.slice())),
      );
    });
    this.selectService.getCategories().subscribe(categories => {
      this.categories = categories;

      this.filteredCategories = this.productForm.get('category')!.valueChanges.pipe(
        startWith(''),
        map(category => (category ? this._filterCategories(category.name ? category.name : category) : this.categories.slice())),
      );
    });
    this.selectService.getTypes().subscribe(types => {
      this.types = types;

      this.filteredTypes = this.productForm.get('type')!.valueChanges.pipe(
        startWith(''),
        map(type => (type ? this._filterTypes(type.name ? type.name : type) : this.types.slice())),
      );
    });
    this.selectService.getNotes().subscribe(notes => {
      this.notes = notes;

      this.filteredNotes = this.productNoteForm.get('note')!.valueChanges.pipe(
        startWith(''),
        map(note => (note ? this._filterNotes(note.name ? note.name : note) : this.notes.slice())),
      );
    });
    this.selectService.getNoteTypes().subscribe(noteTypes => {
      this.noteTypes = noteTypes;
    });

    if(this.isEdit) {
      this.product = this.data.product!;
      this.fillProductFormValues();
    }
  }

  private _filterBrands(value: string): Brand[] {
    const filterValue = value.toLowerCase();

    return this.brands.filter(brand => brand.name.toLowerCase().includes(filterValue));
  }

  private _filterCategories(value: string): Category[] {
    const filterValue = value.toLowerCase();

    return this.categories.filter(category => category.name.toLowerCase().includes(filterValue));
  }

  private _filterTypes(value: string): Type[] {
    const filterValue = value.toLowerCase();

    return this.types.filter(type => type.name.toLowerCase().includes(filterValue));
  }

  private _filterNotes(value: string): Note[] {
    const filterValue = value.toLowerCase();

    return this.notes.filter(note => note.name.toLowerCase().includes(filterValue));
  }
  
  
  fillProductFormValues() {
    this.productForm.patchValue({
      name: this.product.name,
      description: this.product.description,
      amount: this.product.amount,
      category: this.product.category,
      brand: this.product.brand,
      type: this.product.type,
    });

    this.initialProductEditFormValue = this.productForm.value;
    
    this.productNotes = this.product.productNotes;
    this.sizePrices = this.product.sizePrices;
    this.imgSrc = environment.imageBaseUrl + this.product.imageUrl;
  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files[0]) {
      this.selectedFile = event.target.files[0];
      this.imgSrc = URL.createObjectURL(event.target.files[0]);
    }
  }

  displayFn(value: any): string {
    return value?.name;
  }

  onSubmit() {
    const formData = new FormData();
    if (this.selectedFile) {
      formData.append("file", this.selectedFile);
    }


    if(this.isEdit) {
      this.product = {
        name: this.productForm.get('name')?.value,
        description: this.productForm.get('description')?.value,
        imageUrl: this.product.imageUrl,
        brand: this.productForm.get('brand')?.value,
        category: this.productForm.get('category')?.value,
        type: this.productForm.get('type')?.value,
        productId: this.product.productId,
        productNotes: this.productNotes,
        sizePrices: this.sizePrices,
        amount: this.productForm.get('amount')?.value
      }
      formData.append('product', JSON.stringify(this.product));


      this.productService.updateProduct(formData, this.product.productId).subscribe(res => {
        this.dialogRef.close(res);
      });
    }
    else {
      this.product = {
        name: this.productForm.get('name')?.value,
        description: this.productForm.get('description')?.value,
        imageUrl: null!,
        brand: this.productForm.get('brand')?.value,
        category: this.productForm.get('category')?.value,
        type: this.productForm.get('type')?.value,
        productId: null!,
        productNotes: this.productNotes,
        sizePrices: this.sizePrices,
        amount: this.productForm.get('amount')?.value
      }
      formData.append('product', JSON.stringify(this.product));

      this.productService.addProduct(formData).subscribe(res => {
        this.dialogRef.close(res);
      });
    }
  }

  addSizePrice() {
    let sizePrice: SizePrice = {
      sizePriceId: null!,
      size: this.sizePriceForm.get('size')?.value,
      price: this.sizePriceForm.get('price')?.value
    }
    this.sizePrices.push(sizePrice);

    this.sizePriceForm.reset();
  }

  removeSizePrice(index: number) {
    this.sizePrices.splice(index, 1);
  }

  addProductNote() {
    let noteToAdd: Note = this.productNoteForm.get('note')?.value;
    let noteTypeId: number = this.productNoteForm.get('noteTypeId')?.value;

    //let noteName: string = this.notes.find(note => note.noteId === noteToAdd.noteId)?.name ?? '';
    let noteTypeName: string = this.noteTypes.find(type => type.noteTypeId === noteTypeId)?.name ?? '';

    let productNote: ProductNote = {
      productNoteId: null!,
      note: noteToAdd,
      noteType: {
        noteTypeId: noteTypeId,
        name: noteTypeName
      }
    }
    this.productNotes.push(productNote);

    this.productNoteForm.reset();
  }

  removeProductNote(index: number) {
    this.productNotes.splice(index, 1);
  }

  closeDialog() {
    this.dialogRef.close();
  }
}

import { Component, Inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from '@angular/material/dialog';

export interface DialogData {
  title: string;
  content: string;
}

@Component({
  selector: 'app-purchase-dialog',
  standalone: true,
  imports: [MatButtonModule, MatDialogActions, MatDialogClose, MatDialogTitle, MatDialogContent],
  templateUrl: './purchase-dialog.component.html',
  styleUrl: './purchase-dialog.component.scss'
})
export class PurchaseDialogComponent {

  constructor(public dialogRef: MatDialogRef<PurchaseDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: DialogData) {}

}

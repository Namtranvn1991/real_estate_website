import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {AngularFireDatabase, AngularFireList} from '@angular/fire/database';

@Component({
  selector: 'app-bottom-sheet-notify',
  templateUrl: './bottom-sheet-notify.component.html',
  styleUrls: ['./bottom-sheet-notify.component.scss']
})
export class BottomSheetNotifyComponent implements OnInit {

  notifies: Observable<any>[];

  constructor(private notify: AngularFireDatabase) {
    const items: AngularFireList<any> = notify.list('/notifies');
    items.valueChanges().subscribe(
      x => {
        this.notifies = x;
      }
    );
  }

  ngOnInit(): void {
  }

  delete(item) {
    const db = this.notify.database.ref();
    const query = this.notify.database.ref('/notifies').orderByKey();
    query.once('value')
      .then(snapshot => {
        snapshot.forEach(childSnapshot => {
          const pkey = childSnapshot.key;
          const chVal = childSnapshot.val();
          if (chVal.date === item.date && chVal.mess === item.mess && chVal.time === item.time) {
            db.child('notifies/' + pkey).remove();
          }
        });
      });
  }
}

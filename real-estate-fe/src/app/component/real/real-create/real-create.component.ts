import {Component, Inject, OnInit, Renderer2, ViewChild} from '@angular/core';
import {Direction} from '../../../model/real/direction';
import {RealEstateType} from '../../../model/real/real-estate-type';
import {Observable, Subscription} from 'rxjs';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {RealService} from '../../../service/real.service';
import {AngularFireStorage, AngularFireUploadTask} from '@angular/fire/storage';
import {finalize} from 'rxjs/operators';
import {AngularFireDatabase, AngularFireList} from '@angular/fire/database';
import {RealEstateNew} from '../../../model/real/real-estate-new';
import {Router} from '@angular/router';
import {TokenStorageService} from '../../../service/token-storage.service';
import {Customer} from '../../../model/customer/customer';
import {CurrencyPipe} from '@angular/common';


@Component({
  selector: 'app-real-create',
  templateUrl: './real-create.component.html',
  styleUrls: ['./real-create.component.scss']
})
export class RealCreateComponent implements OnInit {
  directions: Direction[];
  realTypes: RealEstateType[];
  customer: Customer;
  private subscription: Subscription | undefined;

  selectFiles: FileList;
  files: File[];
  urls = new Array<string>();
  uploadUrls = new Array();
  imgMess: string;
  imgdetect = false;

  news: RealEstateNew;
  notifies: Observable<any>[];

  uploads = [];
  downloadURLs = [];
  confirm = false;
  uploadPercent: Observable<number>;

  errorMess = false;
  successMess = false;
  priceShow;

  form: FormGroup = this.formBuilder.group(
    {
      id: [],
      approval: [],
      customer: [''],
      description: ['', Validators.maxLength(256)],
      title: ['', [Validators.required, Validators.maxLength(256)]],
      address: ['', [Validators.required, Validators.maxLength(256)]],
      area: ['', [Validators.required, Validators.min(1)]],
      price: ['', [Validators.required, Validators.min(1)]],
      kindOfNews: [1],
      direction: [null, [Validators.required]],
      status: [null, [Validators.required]],
      realEstateType: [1],
      imageList: [],
      urls: [],
    }
  );
  private initialValues: FormGroup;

  constructor(private formBuilder: FormBuilder, private realService: RealService, private router: Router,
              private db: AngularFireStorage, private notify: AngularFireDatabase, private token: TokenStorageService) {
    const items: AngularFireList<any> = notify.list('/notifies');
    this.initialValues = this.form.value;

    items.valueChanges().subscribe(
      x => {
        this.notifies = x;
      }
    );

    this.subscription = this.realService.getAllDirection().subscribe(
      data => {
        this.directions = data;
      }, error => {
        console.log(error);
      }
    );
    this.subscription = this.realService.getAllRealEstateType().subscribe(
      data => {
        this.realTypes = data;
      }, error => {
        console.log(error);
      }
    );
    const id =  this.token.getUser().idCustomer;
    // const idTest = 'KH-0005';
    this.form.controls.customer.setValue(id);
  }

  ngOnInit(): void {
    this.form.valueChanges.subscribe( form => {
      this.priceShow = form.price;
    });
  }

  send(mess: string) {
    const time = new Date().toLocaleTimeString();
    const date = new Date().toLocaleDateString();
    this.notify.list<any>('/notifies').push({mess, time, date});
  }

  detectFiles(event) {
    this.imgdetect = true;
    this.imgMess = null;
    if (this.urls.length !== 0) {
      this.urls = new Array<string>();
    }
    if (event.target.files.length > 5) {
      this.imgMess = 'Chỉ được chọn tối đa 5 ảnh';
      this.imgdetect = false;
      return;
    } else if (event.target.files.length === 0) {
      this.imgMess = 'Chọn tối thiểu 1 ảnh';
      this.imgdetect = false;
      return;
    } else {
      const files = event.target.files;
      this.selectFiles = files;
      if (files) {
        for (const file of files) {
          const reader = new FileReader();
          reader.onload = (e: any) => {
            this.urls.push(e.target.result);
          };
          reader.readAsDataURL(file);
        }
      }
    }
  }

  onSubmit() {
    if (this.form.valid) {
      this.uploads = [];
      this.downloadURLs = [];
      const fileList = this.selectFiles;
      const allPercentage: Observable<number>[] = [];
      // @ts-ignore
      for (const file of fileList) {
        const filePath = `${file.name}` + new Date().getTime();
        const fileRef = this.db.ref(filePath);
        const task = this.db.upload(filePath, file);
        const percentage = task.percentageChanges();
        allPercentage.push(percentage);

        // observe percentage changes
        this.uploadPercent = task.percentageChanges();

        // get notified when the download URL is available
        task.snapshotChanges().pipe(
          finalize(() => {
            fileRef.getDownloadURL().subscribe((url) => {
              this.downloadURLs = this.downloadURLs.concat([url]);
              this.confirm = true;
            });
          })
        ).subscribe();
        // this.downloadURLs.push(this.downloadURL);
      }
      return;
    } else {
      console.log('not ok');
      console.log(this.findInvalidControls());
    }
  }

  public findInvalidControls() {
    const invalid = [];
    const controls = this.form.controls;
    for (const name in controls) {
      if (controls[name].invalid) {
        invalid.push(name);
      }
    }
    return invalid;
  }

  closeModal() {
    this.downloadURLs.forEach(url => {
      this.onDeleteAttachment(url);
    });
    this.downloadURLs = [];
  }

  onDeleteAttachment(downloadURL: string) {
    this.db.storage.refFromURL(downloadURL).delete();
  }

  post() {
    this.send(this.form.value.title);
    const urls = this.downloadURLs.toString();
    this.news = this.form.value;
    this.news.urls = urls;
    this.realService.save(this.news).subscribe(data => {
      this.successMess = true;
      this.router.navigateByUrl('/home');
    }, error => {
      console.log(error);
      this.errorMess = true;
    });
  }

  clearAll() {
    this.form.reset(this.initialValues);
  }

  confirmLast() {
    this.router.navigateByUrl('/real-estate-new/');
  }
}

import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {CustomerService} from '../../../service/customer.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Customer} from '../../../model/customer/customer';
import {TokenStorageService} from '../../../service/token-storage.service';
// import {MatSnackBar} from '@angular/material/snack-bar';
// import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-customer-edit',
  templateUrl: './customer-edit.component.html',
  styleUrls: ['./customer-edit.component.scss']
})
export class CustomerEditComponent implements OnInit {
  // imageForm: FormGroup;
  customerForm: FormGroup;
  id: string;
  customer: Customer;
  message = '';

  constructor(private customerService: CustomerService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit(): void {
    this.initFormEdit();
    this.id = this.tokenStorageService.getUser().idCustomer;
    console.log(this.id);
    this.customerService.getCustomerById(this.id).subscribe(data => {
      this.customer = data;
      this.customerForm.setValue(this.customer);
    });
  }

  updateCustomer(): void {
    this.customerService.update(this.id, this.customerForm.value).subscribe(value => {
      },
      error => {
        this.message = 'Xảy ra lỗi, không thể thay đổi thông tin';
      });
  }
  onClick() {
    this.customerForm.reset();
  }

  initFormEdit() {
    this.customerForm = new FormGroup({
      // id: new FormControl(''),
      id: new FormControl(this.id, [Validators.required, Validators.pattern('^KH-\\d{4}$')]),
      name: new FormControl('', [Validators.required,
        Validators.pattern('^[a-zA-ZàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼ" +\n' +
          // tslint:disable-next-line:max-line-length
          '            "ÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]+(\\\\s[a-zA-Zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợở" +\n' +
          '            "ỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]+)*$')]),
      dateOfBirth: new FormControl('', [Validators.required]),
      gender: new FormControl('', [Validators.required]),
      idCard: new FormControl('', [Validators.required, Validators.pattern(/^\d{9}|\d{12}$/)]),
      phoneNumber: new FormControl('', [Validators.required, Validators.pattern(/^(0|(\\(84\\)\\+))+([9][0-1][0-9]{7})$/)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      address: new FormControl('', [Validators.required]),
    });
  }
}

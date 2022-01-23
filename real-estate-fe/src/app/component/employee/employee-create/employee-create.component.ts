import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {EmployeeService} from '../../../service/employee.service';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs';
import {Positions} from '../../../model/employee/positions';
import {Degree} from '../../../model/employee/degree';
import {Employee} from '../../../model/employee/employee';
import {AppUser} from '../../../model/user/app-user';
import {PositionService} from '../../../service/position.service';
import {DegreeService} from '../../../service/degree.service';
import {EmployeeDTO} from '../../../model/employee/employee-dto';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-employee-create',
  templateUrl: './employee-create.component.html',
  styleUrls: ['./employee-create.component.scss']
})
export class EmployeeCreateComponent implements OnInit {

  employeeForm = new FormGroup({
    name: new FormControl('', [Validators.required,
      Validators.maxLength(40), Validators.minLength(6),
      Validators.pattern('^[a-zA-ZàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]+(\\s[a-zA-ZàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]+)*$')]),
    email: new FormControl('',
      [Validators.required, Validators.pattern('^[a-zA-Z0-9_!#$%&\'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-z]{2,6}$')]),
    phoneNumber: new FormControl('',
      [Validators.required, Validators.pattern('^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$')]),
    address: new FormControl('',
      [Validators.required, Validators.pattern('')]),
    dateOfBirth: new FormControl('', [Validators.required]),
    idCard: new FormControl('', [Validators.required,
      Validators.pattern('^\\d{9}$|\\d{12}$')]),
    gender: new FormControl(0),
    degreeDTO: new FormControl(null, [Validators.required]),
    positionDTO: new FormControl(null, [Validators.required]),
    roleDTO: new FormControl(2)
  });
  subscription: Subscription;
  positions: Positions[];
  degrees: Degree[];
  employee: EmployeeDTO;
  validateErrorEmail: string;

  constructor(private employeeService: EmployeeService,
              private router: Router,
              private positionService: PositionService,
              private degreeService: DegreeService,
              private dialog: MatDialog
  ) {
  }

  ngOnInit(): void {
    this.subscription = this.positionService.getAllPosition().subscribe(data => {
      this.positions = data;
      console.log(this.positions);
    }, error => {
      console.log('abc');
    });
    this.subscription = this.degreeService.getAllDegree().subscribe(data => {
      this.degrees = data;
      console.log(this.degrees);
    }, error => {
      console.log('bcd');
    });
    if (this.employeeForm.valid) {
      this.subscription = this.employeeService.save(this.employeeForm.value).subscribe(data => {
        this.employee = data;
      }, error => {
        console.log('cuộc sống khó khắn');
      });
    }
  }

  get name() {
    return this.employeeForm.get('name');
  }

  get email() {
    return this.employeeForm.get('email');
  }

  get phoneNumber() {
    return this.employeeForm.get('phoneNumber');
  }

  get address() {
    return this.employeeForm.get('address');
  }

  get dateOfBirth() {
    return this.employeeForm.get('dateOfBirth');
  }

  get idCard() {
    return this.employeeForm.get('idCard');
  }

  get gender() {
    return this.employeeForm.get('gender');
  }

  get position_form() {
    return this.employeeForm.get('positionDTO');
  }

  get degree_form() {
    return this.employeeForm.get('degreeDTO');
  }

  get role() {
    return this.employeeForm.get('roleDTO');
  }

  onSubmit() {
    console.log(this.employeeForm.value);
    if (this.employeeForm.valid) {
      this.positions.forEach(value => {
        console.log(value);
        if (this.employeeForm.value.position_form === value.id) {
          this.employeeForm.value.position_form = value;
        }
      });
      this.degrees.forEach(value => {
        if (this.employeeForm.value.degree_form === value.id) {
          this.employeeForm.value.degree_form = value;
        }
      });

      console.log(this.employeeForm.value);
      this.subscription = this.employeeService.save(this.employeeForm.value).subscribe(data => {
          console.log(this.employee);
          this.router.navigate(['/employee/list']);

        }
        , error => {
          this.validateErrorEmail = error.error.errorEmail;
          console.log('Not found');
          this.validateErrorEmail = 'Email bạn nhập đã được sử dụng';
        });
    }
  }

  onClear() {
    this.employeeForm.reset();
  }

  //
  // openAlertDialog() {
  //   const dialogRef = this.dialog.open(SuccessComponent, {
  //     data: {
  //       message: 'Thêm mới nhân viên thành công',
  //       buttonText: {
  //         cancel: 'Xác nhận'
  //       }
  //     },
  //   });
  //   this.router.navigate(['/employee/list']);
  // }
}

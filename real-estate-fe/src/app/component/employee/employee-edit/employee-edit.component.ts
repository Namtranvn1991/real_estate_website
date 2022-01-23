import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {Positions} from '../../../model/employee/positions';
import {Degree} from '../../../model/employee/degree';
import {EmployeeService} from '../../../service/employee.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {PositionService} from '../../../service/position.service';
import {DegreeService} from '../../../service/degree.service';
import {EmployeeDTO} from '../../../model/employee/employee-dto';

@Component({
  selector: 'app-employee-edit',
  templateUrl: './employee-edit.component.html',
  styleUrls: ['./employee-edit.component.scss']
})
export class EmployeeEditComponent implements OnInit {
  employeeForm: FormGroup;
  id: string;
  subscription: Subscription;
  positions: Positions[];
  degrees: Degree[];
  employee: EmployeeDTO;


  constructor(private employeeService: EmployeeService,
              private router: Router,
              private fb: FormBuilder,
              private positionService: PositionService,
              private degreeService: DegreeService,
              private activatedRoute: ActivatedRoute) {
  }


  ngOnInit(): void {
    this.employeeForm = this.fb.group({
      id: new FormControl(''),
      name: this.fb.control('', [Validators.required,
        Validators.pattern('^[a-zA-ZàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]+(\\s[a-zA-ZàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ]+)*$')]),
      email: this.fb.control('', [Validators.required,
        Validators.pattern('^[a-zA-Z0-9_!#$%&\'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-z]{2,6}$')]),
      phoneNumber: this.fb.control('', [Validators.required,
        Validators.pattern('^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$')]),
      address: this.fb.control('', [Validators.required,
        Validators.pattern('')]),
      dateOfBirth: this.fb.control('', [Validators.required]),
      idCard: this.fb.control('',
        [Validators.required, Validators.pattern('^\\d{9}$|\\d{12}$')]),
      gender: this.fb.control('', [Validators.required]),
      degreeDTO: this.fb.control('', [Validators.required]),
      positionDTO: this.fb.control('', [Validators.required]),
      roleDTO: this.fb.control('', [Validators.required])
    });
    this.subscription = this.positionService.getAllPosition().subscribe(data => {
      this.positions = data;
      // console.log(this.positions);
    }, error => {
      console.log('abc');
    });
    this.subscription = this.degreeService.getAllDegree().subscribe(data => {
      this.degrees = data;
      // console.log(this.degrees);
    }, error => {
      console.log('bcd');
    });
    this.activatedRoute.paramMap.subscribe((paramMap: ParamMap) => {

      this.id = paramMap.get('id');
    });

    this.employeeService.findEmployeeById(this.id).subscribe(data => {
      this.employee = data;
      console.log(this.employee);
      this.employeeForm.setValue(this.employee);
      console.log('Tạ ơn mọi người');
    }, error => {
      console.log('cuộc sống khó khắn');
    });

  }

  compareFn(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
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
    // if (this.employeeForm.valid) {
    this.subscription = this.employeeService.updateEmployee(this.id, this.employeeForm.value).subscribe(data => {
      // alert('Chỉnh sửa thông tin thành công');
      //this.router.navigate(['/employee/list']);
      console.log(this.employeeForm);
    }, error => {
      console.log('có lỗi đại ca ơi');
    });
    // }
  }

  onClick() {
    this.employeeForm.reset();
  }

  submitModal() {
    this.router.navigate(['/employee/list']);
  }
}

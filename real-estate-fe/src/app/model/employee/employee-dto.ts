import {Degree} from './degree';
import {Positions} from './positions';
import {AppUser} from '../user/app-user';
import {Image} from '../image/image';

export interface EmployeeDTO {
  id: string;
  name: string;
  email: string;
  phoneNumber: string;
  address: string;
  dateOfBirth: string;
  idCard: string;
  gender: number;
  degreeDTO: Degree;
  positionDTO: Positions;
  roleDTO: number;
  appUser: AppUser;
  image: Image;
}

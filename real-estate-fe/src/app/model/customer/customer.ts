import {AppUser} from '../user/app-user';
import {Image} from '../image/image';

export interface Customer {
  id?: string;
  address?: string;
  dateOfBirth?: string;
  email?: string;
  gender?: number;
  name?: string;
  idCard?: string;
  phoneNumber?: string;
  appUser?: AppUser;
  image?: Image;
  userName?: string;
  password?: string;
}

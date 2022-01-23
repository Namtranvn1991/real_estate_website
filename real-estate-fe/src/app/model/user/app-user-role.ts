import {AppUser} from './app-user';
import {Role} from './role';

export interface AppUserRole {
  appUser: AppUser;
  role: Role;
}

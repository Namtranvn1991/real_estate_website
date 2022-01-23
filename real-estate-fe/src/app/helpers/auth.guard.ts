import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {TokenStorageService} from '../service/token-storage.service';
import {HeaderComponent} from '../component/share/header/header.component';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router,
              private tokenStorageService: TokenStorageService) {
  }

  canActivate(
    route: ActivatedRouteSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let user = this.tokenStorageService.getUser();
    if (user !== null) {
      let role = user.roles[0];
      if (!this.tokenStorageService.isAuthenticated() || route.data.expectedRole.indexOf(role) === -1) {
        this.router.navigate(['/security/access-denied']);
        return false;
      }
      return true;
    }
    this.router.navigate(['/security/access-denied']);
    return false;

  }

}

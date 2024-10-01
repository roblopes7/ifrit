import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  let token = localStorage.getItem('token');
  console.log('authInterceptor');
  if(token) {
    const teste = `Bearer ${token}`;
    console.log(teste);
    const cloneReq = req.clone({headers: req.headers.set('Authorization', teste)});
    return next(cloneReq);
  }
  console.log('else');
  return next(req);
};

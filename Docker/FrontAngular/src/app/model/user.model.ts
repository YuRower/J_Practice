export class User {

  id: number;
  login: string;
  password: string;
  firstName: string;
  lastName: string;
  email: string;
  birthday : Date;
  roleId: {
       id: number,
       name: string
  }

}

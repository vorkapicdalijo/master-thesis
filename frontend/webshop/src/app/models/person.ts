export class Person {
    userId: string;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    address: string;
    city: string;
    zipCode: number;
    country: string;
  
    constructor(
        userId: string,
        firstName: string,
        lastName: string,
        email: string,
        phoneNumber: string,
        address: string,
        city: string,
        zipCode: number,
        country: string,
    ) {
      this.userId = userId;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.phoneNumber = phoneNumber;
      this.address = address;
      this.city = city;
      this.zipCode = zipCode;
      this.country = country;
    }
  }
  
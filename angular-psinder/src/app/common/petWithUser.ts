import { UserData } from './userData';

export class PetWithUser {
    id: string;
    user: UserData;
    name: string;
    race: string;
    size: string;
    description: string;
    address: {
        city: string;
        street: string;
    }
}

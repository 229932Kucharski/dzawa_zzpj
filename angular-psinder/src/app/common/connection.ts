import { Pet } from "./pet";
import { UserData } from "./userData";

export class Connection {
    id: number;
    pet: Pet;
    owner: UserData;
    walker: UserData;
    status: string;
}
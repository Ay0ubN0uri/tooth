import { IUser } from 'app/shared/model/user.model';
import { IGroupe } from 'app/shared/model/groupe.model';

export interface IProfessor {
  id?: number;
  grade?: string | null;
  user?: IUser;
  groupes?: IGroupe[] | null;
}

export const defaultValue: Readonly<IProfessor> = {};

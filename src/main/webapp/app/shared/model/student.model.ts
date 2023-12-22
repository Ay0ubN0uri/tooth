import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IGroupe } from 'app/shared/model/groupe.model';

export interface IStudent {
  id?: number;
  number?: string;
  cne?: string;
  cin?: string;
  birthDay?: dayjs.Dayjs;
  user?: IUser;
  groupes?: IGroupe[] | null;
}

export const defaultValue: Readonly<IStudent> = {};

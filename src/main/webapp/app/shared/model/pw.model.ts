import dayjs from 'dayjs';
import { ITooth } from 'app/shared/model/tooth.model';
import { IAcademicYear } from 'app/shared/model/academic-year.model';
import { IGroupe } from 'app/shared/model/groupe.model';

export interface IPW {
  id?: number;
  title?: string | null;
  objectif?: string | null;
  docs?: string | null;
  deadline?: dayjs.Dayjs | null;
  tooth?: ITooth;
  academicYear?: IAcademicYear;
  groupes?: IGroupe[] | null;
}

export const defaultValue: Readonly<IPW> = {};

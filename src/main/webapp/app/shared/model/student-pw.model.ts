import dayjs from 'dayjs';
import { IStudent } from 'app/shared/model/student.model';
import { IPW } from 'app/shared/model/pw.model';
import { IAcademicYear } from 'app/shared/model/academic-year.model';

export interface IStudentPW {
  id?: number;
  time?: string | null;
  imageFrontContentType?: string | null;
  imageFront?: string | null;
  imageSideContentType?: string | null;
  imageSide?: string | null;
  date?: dayjs.Dayjs | null;
  student?: IStudent;
  pw?: IPW;
  academicYear?: IAcademicYear;
}

export const defaultValue: Readonly<IStudentPW> = {};

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
  note?: number | null;
  a1ImageSide?: number | null;
  a2ImageSide?: number | null;
  a3ImageSide?: number | null;
  p1ImageSide?: number | null;
  p2ImageSide?: number | null;
  p3ImageSide?: number | null;
  a1ImageFront?: number | null;
  a2ImageFront?: number | null;
  a3ImageFront?: number | null;
  p1ImageFront?: number | null;
  p2ImageFront?: number | null;
  p3ImageFront?: number | null;
  student?: IStudent;
  pw?: IPW;
  academicYear?: IAcademicYear;
}

export const defaultValue: Readonly<IStudentPW> = {};

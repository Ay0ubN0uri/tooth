import dayjs from 'dayjs';

export interface IAcademicYear {
  id?: number;
  year?: string | null;
  startingDate?: dayjs.Dayjs | null;
  endingDate?: dayjs.Dayjs | null;
}

export const defaultValue: Readonly<IAcademicYear> = {};

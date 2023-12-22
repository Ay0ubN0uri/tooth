import { IPW } from 'app/shared/model/pw.model';

export interface ITooth {
  id?: number;
  name?: string | null;
  pws?: IPW[] | null;
}

export const defaultValue: Readonly<ITooth> = {};

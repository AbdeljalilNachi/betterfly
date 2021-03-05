import { Moment } from 'moment';

export interface IProcessus {
  id?: number;
  processus?: string;
  date?: Moment;
  version?: number;
  finalite?: string;
  ficheContentType?: string;
  fiche?: any;
  type?: string;
}

export class Processus implements IProcessus {
  constructor(
    public id?: number,
    public processus?: string,
    public date?: Moment,
    public version?: number,
    public finalite?: string,
    public ficheContentType?: string,
    public fiche?: any,
    public type?: string
  ) {}
}

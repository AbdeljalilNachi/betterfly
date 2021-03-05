import { Moment } from 'moment';

export interface IReclamation {
  id?: number;
  processus?: string;
  date?: Moment;
  description?: string;
  justifiee?: boolean;
  client?: string;
  piecejointeContentType?: string;
  piecejointe?: any;
  origine?: string;
}

export class Reclamation implements IReclamation {
  constructor(
    public id?: number,
    public processus?: string,
    public date?: Moment,
    public description?: string,
    public justifiee?: boolean,
    public client?: string,
    public piecejointeContentType?: string,
    public piecejointe?: any,
    public origine?: string
  ) {
    this.justifiee = this.justifiee || false;
  }
}

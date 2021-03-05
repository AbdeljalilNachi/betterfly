import { Moment } from 'moment';
import { Rubrique } from 'app/shared/model/enumerations/rubrique.model';

export interface IObligationConformite {
  id?: number;
  date?: Moment;
  rubrique?: Rubrique;
  reference?: string;
  num?: number;
  exigence?: string;
  applicable?: boolean;
  conforme?: boolean;
  statut?: number;
  observation?: string;
  processus?: string;
  oRIGINE?: string;
}

export class ObligationConformite implements IObligationConformite {
  constructor(
    public id?: number,
    public date?: Moment,
    public rubrique?: Rubrique,
    public reference?: string,
    public num?: number,
    public exigence?: string,
    public applicable?: boolean,
    public conforme?: boolean,
    public statut?: number,
    public observation?: string,
    public processus?: string,
    public oRIGINE?: string
  ) {
    this.applicable = this.applicable || false;
    this.conforme = this.conforme || false;
  }
}

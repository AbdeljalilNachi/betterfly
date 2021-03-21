import { IResultIndicateurs } from 'app/shared/model/result-indicateurs.model';
import { Mois } from 'app/shared/model/enumerations/mois.model';

export interface IResultatIndicateur {
  id?: number;
  mois?: Mois;
  cible?: number;
  resultat?: number;
  resultIndicateurs?: IResultIndicateurs;
}

export class ResultatIndicateur implements IResultatIndicateur {
  constructor(
    public id?: number,
    public mois?: Mois,
    public cible?: number,
    public resultat?: number,
    public resultIndicateurs?: IResultIndicateurs
  ) {}
}

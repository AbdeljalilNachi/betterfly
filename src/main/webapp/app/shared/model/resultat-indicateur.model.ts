import { Mois } from 'app/shared/model/enumerations/mois.model';

export interface IResultatIndicateur {
  id?: number;
  mois?: Mois;
  cible?: number;
  resultat?: number;
  indicateur?: string;
}

export class ResultatIndicateur implements IResultatIndicateur {
  constructor(public id?: number, public mois?: Mois, public cible?: number, public resultat?: number, public indicateur?: string) {}
}

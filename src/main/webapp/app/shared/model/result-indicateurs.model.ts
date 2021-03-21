import { IResultatIndicateur } from 'app/shared/model/resultat-indicateur.model';
import { IIndicateurSMI } from 'app/shared/model/indicateur-smi.model';

export interface IResultIndicateurs {
  id?: number;
  annee?: number;
  observation?: string;
  resultats?: IResultatIndicateur[];
  indicateur?: IIndicateurSMI;
}

export class ResultIndicateurs implements IResultIndicateurs {
  constructor(
    public id?: number,
    public annee?: number,
    public observation?: string,
    public resultats?: IResultatIndicateur[],
    public indicateur?: IIndicateurSMI
  ) {}
}

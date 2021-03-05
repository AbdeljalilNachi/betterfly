export interface IObjectif {
  id?: number;
  processus?: string;
  axedelapolitiqueqse?: string;
  objectifqse?: string;
  indicateur?: string;
  origine?: string;
}

export class Objectif implements IObjectif {
  constructor(
    public id?: number,
    public processus?: string,
    public axedelapolitiqueqse?: string,
    public objectifqse?: string,
    public indicateur?: string,
    public origine?: string
  ) {}
}

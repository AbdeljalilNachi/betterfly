export interface IProcessus {
  id?: number;
}

export class Processus implements IProcessus {
  constructor(public id?: number) {}
}

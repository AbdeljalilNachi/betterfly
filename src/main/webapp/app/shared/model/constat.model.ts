export interface IConstat {
  id?: number;
  processus?: string;
  audit?: string;
  constat?: string;
  type?: string;
  origine?: string;
}

export class Constat implements IConstat {
  constructor(
    public id?: number,
    public processus?: string,
    public audit?: string,
    public constat?: string,
    public type?: string,
    public origine?: string
  ) {}
}

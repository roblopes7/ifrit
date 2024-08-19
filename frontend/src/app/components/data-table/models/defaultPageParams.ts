import { PageParams } from '../models/pageParams';

export const defaultPageParams: PageParams = {
  direction: 'ASC',
  linesPerPage: 25,
  orderBy: 'id',
  page: 0,
};

export function getDefaultPageParams(overrides: Partial<PageParams> = {}): PageParams {
  return { ...defaultPageParams, ...overrides };
}

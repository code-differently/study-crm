import { RESTDataSource } from '@apollo/datasource-rest';
import { ServiceConfig } from './service-config';

export class LayoutsAPI extends RESTDataSource {
  private readonly accessToken: string;
  private readonly orgId: string;

  constructor(private readonly config: ServiceConfig) {
    super();
    this.accessToken = config.accessToken;
    this.orgId = config.user.organizationIds?.[0] || '';
    this.baseURL = `${process.env.API_SERVER_URL}/`;
  }

  async getLayouts(entityType: string, types: string[] = []) {
    let url = `organizations/${this.orgId}/layouts?entityType=${entityType}`;
    if (types?.length) {
      url += `&types=${types.join(',')}`;
    }
    const result = await this.get(url, {
      headers: {
        authorization: `Bearer ${this.accessToken}`,
      },
    });
    return result;
  }
}

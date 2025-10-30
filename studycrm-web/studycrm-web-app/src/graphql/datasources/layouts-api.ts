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

    try {
      const result = await this.get(url, {
        headers: {
          authorization: `Bearer ${this.accessToken}`,
        },
      });
      return result;
    } catch (error: any) {
      // Log token-related errors for debugging
      if (error.status === 401) {
        console.error('Authentication failed - token may be expired:', error);
        throw new Error(
          'Authentication failed. Please refresh the page and sign in again.'
        );
      }
      throw error;
    }
  }
}
